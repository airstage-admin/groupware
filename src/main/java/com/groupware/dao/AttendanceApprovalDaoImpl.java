package com.groupware.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.groupware.dto.AttendanceApprovalDto;
import com.groupware.dto.AttendanceDto;

/**
* AttendanceApprovalDao
* attendance_approvalテーブル用DAO
* @author　N.Hirai
* @version　1.0.0
*/
@Repository
public class AttendanceApprovalDaoImpl implements AttendanceApprovalDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(AttendanceApprovalDaoImpl.class);

	/**
	* 勤怠確認承認テーブルInsert
	* 
	* @param　userId ユーザーID
	* @param　date 対象年月日
	* @param　approvalId 承認者ID
	* @return　
	*/
	@Override
	public void insert(long userId, LocalDate date, long approvalId) {

		String sql = "INSERT INTO attendance_approval "
				+ "(user_id, working_day, approval_by, created_by, updated_by) "
				+ "VALUES (?, ?, ?, ?, ?)";
		try {
			// attendanceテーブルに書き込む
			jdbcTemplate.update(sql,
					userId,
					date,
					approvalId,
					approvalId,
					approvalId);
		} catch (Exception e) {
			logger.error("勤怠確認承認テーブル新規書込み時、DBアクセスエラー", e);
		}
	}

	/**
	* 当月データを取得する
	* 
	* @param　ym 当月
	* @return
	*/
	@Override
	public List<AttendanceApprovalDto> findByMonth(YearMonth ym) {
		String sql = "SELECT * FROM attendance_approval WHERE YEAR(working_day) = ? AND MONTH(working_day) = ? AND delflg = false";
		try {
			return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs),ym.getYear(), ym.getMonthValue());
		} catch (Exception e) {
			logger.error("勤怠確認承認テーブル当月データを取得時、DBアクセスエラー", e);
			return null;
		}
	}

	/**
	* 勤怠データを更新する
	* 
	* @param　dto saveAttendanceDTO
	* @param　id 更新者id
	* @return　
	*/
	@Transactional(rollbackFor = SQLException.class)
	public void update(AttendanceDto dto, int id) {
		String sql = """
				UPDATE attendance
				   SET place_work_name = ?, clock_in = ?, clock_out = ?, break_time = ?, night_break_time = ?,
				       vacation_category = ?, vacation_note = ?,
				       updated_by = ?, updated_at = NOW()
				 WHERE id = ?
				""";

		try {
			jdbcTemplate.update(sql,
					dto.getPlaceWorkName(),
					dto.getClockIn(),
					dto.getClockOut(),
					dto.getBreakTime(),
					dto.getNightBreakTime(),
					dto.getVacationCategory(),
					dto.getVacationNote(),
					id,
					dto.getId());
		} catch (Exception e) {
			logger.error("勤怠データ更新時、DBアクセスエラー", e);
		}
	}

	/**
	* 入力された勤怠データを論理削除する
	* 
	* @param　id AttendanceDtoのID
	* @return　
	*/
	@Transactional(rollbackFor = SQLException.class)
	public void delete(long id, int updatedBy) {
		String sql = "UPDATE attendance_approval SET delflg = true, updated_by = ?, updated_at = NOW() WHERE id = ?";

		try {
			jdbcTemplate.update(sql, updatedBy, id);
		} catch (Exception e) {
			logger.error("勤怠データ論理削除時、DBアクセスエラー", e);
		}
	}

	/**
	* 勤怠承認データがあるかチェック 
	* 
	* @param　userId ユーザーID
	* @param　ym 対象年月
	* @return　boolean
	*/
	@Override
	public boolean existsByApprovalDate(long userId, YearMonth ym) {
		String sql = "SELECT COUNT(*)t FROM attendance_approval WHERE user_id = ? AND YEAR(working_day) = ? AND MONTH(working_day) = ? AND delflg = false";

		try {
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, ym.getYear(), ym.getMonthValue());
			return count != null && count > 0;
		} catch (Exception e) {
			logger.error("勤怠勤怠承認データか確認時、DBアクセスエラー", e);
			return false;
		}
	}

	// RowMapper
	private AttendanceApprovalDto mapRow(ResultSet rs) throws SQLException {
		AttendanceApprovalDto dto = new AttendanceApprovalDto();
		dto.setId(rs.getLong("id"));
		dto.setUserId(rs.getInt("user_id"));
		if (rs.getDate("working_day") != null)
			dto.setWorkingDay(rs.getDate("working_day").toLocalDate());
		dto.setApproval(rs.getBoolean("approval"));
		dto.setApprovalBy(rs.getInt("approval_by"));
		dto.setDelflg(rs.getBoolean("delflg"));
		dto.setCreatedBy(rs.getInt("created_by"));
		dto.setCreatedAt(rs.getString("created_at"));
		dto.setUpdatedBy(rs.getInt("updated_by"));
		dto.setUpdatedAt(rs.getString("updated_at"));
		return dto;
	}
}
