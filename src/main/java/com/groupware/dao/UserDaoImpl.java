package com.groupware.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.groupware.common.constant.CommonConstants;
import com.groupware.dto.UserApprovalDto;
import com.groupware.dto.UserDto;

/**
* UserDaoImpl
* usersテーブル用DAO
* @author　N.Hirai
* @version　1.0.0
*/
@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static final int UNSELECTED = 0; // 部署未選択
	public static final String APPROVED = "承認済"; 
	public static final String NOT_APPROVED = "未承認";
	
	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	/**
	* 入力されたLoginid、Passwordでログイン処理を行う
	* 
	* @param　login_id ログインID
	* @param　password パスワード
	* @return　
	*/
	@Override
	public UserDto login(String login_id, String password) {
		String sql = "SELECT * FROM users WHERE login_id = ? AND password = ? and delflg = false";
		try {
			List<UserDto> users = jdbcTemplate.query(
					sql,
					ps -> {
						ps.setString(1, login_id);
						ps.setString(2, password);
					},
					(ResultSet rs, int rowNum) -> {
						UserDto user = new UserDto();
						user.setId(rs.getInt("id"));
						user.setLoginid(rs.getString("login_id"));
						user.setPassword(rs.getString("password"));
						user.setUsername(rs.getString("user_name"));
						user.setEmployeeNo(rs.getString("employee_no"));
						user.setMail(rs.getString("mail"));
						user.setEmployeeType(rs.getInt("employee_type"));
						user.setDepartment(rs.getInt("department"));
						user.setHireDate(rs.getString("hire_date"));
						user.setPaidGrantDate(rs.getString("paid_grant_date"));
						user.setPaidLeaveGranted(rs.getInt("paid_leave_granted"));
						user.setPaidLeaveRemaining(rs.getFloat("paid_leave_remaining"));
						return user;
					});

			return users.isEmpty() ? null : users.get(0);
		} catch (EmptyResultDataAccessException e) {
			// 該当ユーザーなし（null返す）
			logger.warn("ログイン失敗：該当ユーザーなし（login_id={}）", login_id);
			return null;
		} catch (Exception e) {
			logger.error("DBアクセスエラー", e);
			return null;
		}
	}

	/**
	* 有効な社員アカウントデータ一覧を取得する
	* 
	* @param　categoryCode 部署コード
	* @return　List<UserDto>
	*/
	@Override
	public List<UserDto> findByUsersList(int categoryCode) {
		String judge = categoryCode == UNSELECTED ? CommonConstants.JUDGE_GT
				: CommonConstants.JUDGE_EQUAL;

		String sql = "SELECT * FROM users WHERE delflg = false AND department " + judge + " ? ORDER BY employee_no ASC";
		try {
			return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs), categoryCode);
		} catch (Exception e) {
			logger.error("有効な社員アカウントデータ取得時、DBアクセスエラー", e);
			return null;
		}
	}

	/**
	* 社員アカウントデータを取得する
	* 
	* @param　id 社員アカウントID
	* @param　categoryCode 部署コード
	* @return　UserDto
	*/
	@Override
	public UserDto findByUser(int id) {
		String sql = "SELECT * FROM users WHERE id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapRow(rs), id);
		} catch (EmptyResultDataAccessException e) {
			logger.error("社員アカウントデータ取得時、DBアクセスエラー", e);
			return null;
		}
	}

	/**
	* 既存データに登録するログインIDがあるかチェックする（自分自身は除く）
	* 
	* @param　id 社員アカウントID
	* @param　login_id ログインID
	* @return　false：無し、true：有り
	*/
	@Override
	public boolean existsByLoginid(int id, String login_id) {
		String sql = "SELECT COUNT(*) FROM users WHERE id <> ? AND login_id = ? AND delflg = false";

		try {
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id, login_id);
			return count != null && count > 0;
		} catch (Exception e) {
			logger.error("既存データに登録するログインIDがあるかチェック時、DBアクセスエラー", e);
			return false;
		}
	}

	/**
	* 社員アカウントデータを登録更新する
	* 
	* @param　userDto 社員アカウントデータ
	* @return
	*/
	@Override
	public void update(UserDto userDto) {
		// 新規登録
		if (userDto.getId() == 0) {
			String sql = "INSERT INTO users "
					+ "(department, hire_date, paid_grant_date, employee_no, user_name, mail, login_id, password, employee_type, created_by, updated_by) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				jdbcTemplate.update(sql,
						userDto.getDepartment(),
						userDto.getHireDate(),
						userDto.getPaidGrantDate(),
						userDto.getEmployeeNo(),
						userDto.getUsername(),
						userDto.getMail(),
						userDto.getLoginid(),
						userDto.getPassword(),
						userDto.getEmployeeType(),
						userDto.getCreatedBy(),
						userDto.getCreatedBy());
			} catch (Exception e) {
				logger.error("usersデータ新規登録時、DBアクセスエラー", e);
			}

			// 更新
		} else {
			String sql = "UPDATE users "
					+ "SET department = ?, employee_no = ?, user_name = ?, mail = ?, login_id = ?, password = ?, employee_type = ?, updated_by = ?, updated_at = NOW() "
					+ "WHERE id = ?";
			try {
				jdbcTemplate.update(sql,
						userDto.getDepartment(),
						userDto.getEmployeeNo(),
						userDto.getUsername(),
						userDto.getMail(),
						userDto.getLoginid(),
						userDto.getPassword(),
						userDto.getEmployeeType(),
						userDto.getCreatedBy(),
						userDto.getId());
			} catch (Exception e) {
				logger.error("usersデータ更新時、DBアクセスエラー", e);
			}
		}
	}
	
	/**
	* ログインIDのみを更新する
	* @param　userDto ユーザーIDと新しいログインID、および更新者IDを含むデータ
	* @return
	*/
	@Override
	public void updateLoginId(UserDto userDto) {
		String sql = "UPDATE users "
				+ "SET login_id = ?, updated_by = ?, updated_at = NOW() "
				+ "WHERE id = ?";
		try {
			// userDtoから新しいログインID、更新者ID、更新対象のIDを取得してSQLを実行
			jdbcTemplate.update(sql,
					userDto.getLoginid(),
					userDto.getUpdatedBy(),
					userDto.getId());
		} catch (Exception e) {
			logger.error("usersデータ (ログインID) 更新時、DBアクセスエラー", e);
		}
	}

	/**
	* 社員アカウントデータを停止する
	* 
	* @param　id 社員アカウントID
	* @param　updateId 更新者ID
	* @return　UserDto
	*/
	@Override
	public void accountStop(int id, int updateId) {
		String sql = "UPDATE users "
				+ "SET delflg = true, updated_by = ?, updated_at = NOW() "
				+ "WHERE id = ?";
		try {
			jdbcTemplate.update(sql, updateId, id);
		} catch (Exception e) {
			logger.error("usersデータ更新時、DBアクセスエラー", e);
		}
	}
	
	/**
	* attendance_approvalとJoinした社員データ一覧を取得する
	* 
	* @param　ym 対象年月
	* @return　List<UserApprovalDto>
	*/
	@Override
	public List<UserApprovalDto> findByUsersApprovalList(YearMonth ym) {
	String sql = "SELECT u.id AS id, u.user_name AS user_name, u.employee_no AS employee_no, u.department AS department, a.approval AS approval, approver_user.user_name AS approval_user "
			+ "FROM users u "
			+ "INNER JOIN department_type dt ON u.department = dt.code "
			+ "LEFT OUTER JOIN attendance_approval a ON u.id = a.user_id and a.approval = true AND a.delflg = FALSE AND YEAR(a.working_day) = ? AND MONTH(a.working_day) = ? "
			+ "LEFT OUTER JOIN users approver_user ON a.approval_by = approver_user.id "
			+ "WHERE u.delflg = FALSE AND dt.is_admin = FALSE";
		try {
			return jdbcTemplate.query(sql, (rs, rowNum) -> mapApprovalRow(rs),  ym.getYear(), ym.getMonthValue());
		} catch (Exception e) {
			logger.error("有効な社員アカウントデータ取得時、DBアクセスエラー", e);
			return null;
		}
	}

	/**
	* 有給データを更新する
	* 
	* @param　id ユーザーID
	* @param　paidGrantDate 有給付与日
	* @param　paidLeaveGranted 有給付与日数
	* @param　paidLeaveRemaining 有給残日数
	* @return
	*/
	@Override
	public void paidLeaveUpdate(int id, String paidGrantDate, int paidLeaveGranted, float paidLeaveRemaining) {
		String sql = "UPDATE users "
				+ "SET paid_grant_date = ?, paid_leave_granted = ?, paid_leave_remaining = ?, updated_by = 0, updated_at = NOW() "
				+ "WHERE id = ?";
		try {
			jdbcTemplate.update(sql, paidGrantDate, paidLeaveGranted, paidLeaveRemaining, id);
		} catch (Exception e) {
			logger.error("有給データ更新時、DBアクセスエラー", e);
		}
	}

	/**
	* 有給残日数を更新する
	* 
	* @param　id ユーザーID
	* @param　paidLeaveRemaining 有給残日数
	* @return
	*/
	@Override
	public void paidLeaveRemainingUpdate(int id, float paidLeaveRemaining) {
		String sql = "UPDATE users SET paid_leave_remaining = ?, updated_by = 0, updated_at = NOW() WHERE id = ?";
		try {
			jdbcTemplate.update(sql, paidLeaveRemaining, id);
		} catch (Exception e) {
			logger.error("有給データ更新時、DBアクセスエラー", e);
		}
	}
	
	// RowMapper
	private UserDto mapRow(ResultSet rs) throws SQLException {
		UserDto dto = new UserDto();
		dto.setId(rs.getInt("id"));
		dto.setLoginid(rs.getString("login_id"));
		dto.setPassword(rs.getString("password"));
		dto.setUsername(rs.getString("user_name"));
		dto.setEmployeeNo(rs.getString("employee_no"));
		dto.setMail(rs.getString("mail"));
		dto.setEmployeeType(rs.getInt("employee_type"));
		dto.setDepartment(rs.getInt("department"));
		dto.setHireDate(rs.getString("hire_date").replace(CommonConstants.HYPHEN_FORMAT,CommonConstants.SLASH_FORMAT));
		dto.setPaidGrantDate(rs.getString("paid_grant_date").replace(CommonConstants.HYPHEN_FORMAT,CommonConstants.SLASH_FORMAT));
		dto.setPaidLeaveGranted(rs.getInt("paid_leave_granted"));
		dto.setPaidLeaveRemaining(rs.getFloat("paid_leave_remaining"));
		return dto;
	}
	
	// mapApprovalRow
	private UserApprovalDto mapApprovalRow(ResultSet rs) throws SQLException {
		UserApprovalDto dto = new UserApprovalDto();
		dto.setId(rs.getInt("id"));
		dto.setUsername(rs.getString("user_name"));
		dto.setEmployeeNo(rs.getString("employee_no"));
		dto.setDepartment(rs.getInt("department"));
		dto.setApproval(NOT_APPROVED);
		dto.setApprovalName("");
		if (rs.getBoolean("approval")) {
			dto.setApproval(APPROVED);
			dto.setApprovalName(rs.getString("approval_user"));
		}

		return dto;
	}
	
	/**
	* パスワードを更新する
	* @param　id ユーザーID
	* @param　newPassword 新しいパスワード
	*/
	@Override
	public void updatePassword(int id, String newPassword) {
		// パスワード、更新者(自分=0またはid)、更新日時を更新
		String sql = "UPDATE users "
				+ "SET password = ?, updated_by = ?, updated_at = NOW() "
				+ "WHERE id = ?";
		try {
			jdbcTemplate.update(sql, newPassword, id, id);
		} catch (Exception e) {
			logger.error("パスワード更新時、DBアクセスエラー", e);
		}
	}
	
}