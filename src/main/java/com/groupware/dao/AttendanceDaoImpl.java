package com.groupware.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.groupware.common.constant.HolidayCategory;
import com.groupware.common.constant.WeekCategory;
import com.groupware.dto.AttendanceDto;

/**
* AttendanceDaoImpl
* attendanceテーブル用DAO
* @author　N.Hirai
* @version　1.0.0
*/
@Repository
public class AttendanceDaoImpl implements AttendanceDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(AttendanceDaoImpl.class);

	/**
	* 対象月の勤怠データをattendanceテーブルに作成する
	* 
	* @param　userId ユーザーID
	* @param　date 対象年月日
	* @param　weekEnum WeekCategoryのENUM値
	* @param　holiday HolidayCategoryのENUM値
	* @param　placeCode 勤務先コード
	* @return　
	*/
	@Override
	public void insert(long userId, LocalDate date, WeekCategory weekEnum, HolidayCategory holidayEnum, int placeCode) {

		String sql = "INSERT INTO attendance "
				+ "(user_id, place_work, working_day, week, holiday, created_by, updated_by) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			// attendanceテーブルに書き込む
			jdbcTemplate.update(sql,
					userId,
					placeCode,
					date,
					weekEnum.getCode(),
					holidayEnum.getHoliday(),
					userId,
					userId);
		} catch (Exception e) {
			logger.error("新規書込み時、DBアクセスエラー", e);
		}
	}

	/**
	* 入力された勤怠データをユーザーの当月データを取得する
	* 
	* @param　userId ユーザーID
	* @param　ym 当月
	* @param　placeCode 勤務先コード
	* @return
	*/
	@Override
	public List<AttendanceDto> findByUserAndMonth(long userId, YearMonth ym, int placeCode) {
		String sql = "SELECT * FROM attendance WHERE user_id = ? AND YEAR(working_day) = ? AND MONTH(working_day) = ? AND place_work = ? AND delflg = false ORDER BY working_day";
		try {
			return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs), userId, ym.getYear(), ym.getMonthValue(), placeCode);
		} catch (Exception e) {
			logger.error("当月データを取得時、DBアクセスエラー", e);
			return null;
		}
	}

	/**
	* ユーザーの当月データを取得する
	* 
	* @param　userId ユーザーID
	* @param　ym 当月
	* @return　List<AttendanceDto>
	*/
	@Override
	public List<AttendanceDto> findByUserAndMonthPaidDate(long userId, YearMonth ym) {
		String sql = "SELECT * FROM attendance WHERE user_id = ? AND YEAR(working_day) = ? AND MONTH(working_day) = ?  AND delflg = false ORDER BY working_day";
		try {
			return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs), userId, ym.getYear(), ym.getMonthValue());
		} catch (Exception e) {
			logger.error("有給付与処理で当月データを取得時、DBアクセスエラー", e);
			return null;
		}
	}
	
	/**
	* 入力された勤怠データをIDで取得する
	* 
	* @param　id AttendanceDtoのID
	* @return　
	*/
	public AttendanceDto findById(long id) {
		String sql = "SELECT * FROM attendance WHERE id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapRow(rs), id);
		} catch (EmptyResultDataAccessException e) {
			logger.error("勤怠データID取得時、DBアクセスエラー", e);
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
		String sql = "UPDATE attendance SET delflg = true, updated_by = ?, updated_at = NOW() WHERE id = ?";

		try {
			jdbcTemplate.update(sql, updatedBy, id);
		} catch (Exception e) {
			logger.error("勤怠データ論理削除時、DBアクセスエラー", e);
		}
	}

	/**
	* 今月の勤怠初期データがあるかチェック 
	* 
	* @param　userId ユーザーID
	* @param　ym 対象年月
	* @return　boolean
	*/
	@Override
	public boolean existsByInitialAttendanceDate(long userId, YearMonth ym) {
		String sql = "SELECT COUNT(*) FROM attendance WHERE user_id = ? AND YEAR(working_day) = ? AND MONTH(working_day) = ? AND delflg = false";

		try {
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, ym.getYear(), ym.getMonthValue());
			return count != null && count > 0;
		} catch (Exception e) {
			logger.error("今月の勤怠初期データか確認時、DBアクセスエラー", e);
			return false;
		}
	}

	// RowMapper
	private AttendanceDto mapRow(ResultSet rs) throws SQLException {
		AttendanceDto dto = new AttendanceDto();
		dto.setId(rs.getLong("id"));
		dto.setUserId(rs.getInt("user_id"));
		if (rs.getDate("working_day") != null)
			dto.setWorkingDay(rs.getDate("working_day").toLocalDate());
		if (rs.getTime("break_time") != null)
			dto.setBreakTime(rs.getTime("break_time").toLocalTime());
		if (rs.getTime("night_break_time") != null)
			dto.setNightBreakTime(rs.getTime("night_break_time").toLocalTime());
		dto.setPlaceWork(rs.getInt("place_work"));
		dto.setPlaceWorkName(rs.getString("place_work_name"));
		dto.setWeek(rs.getInt("week"));
		dto.setHoliday(rs.getInt("holiday"));
		dto.setClockIn(rs.getString("clock_in"));
		dto.setClockOut(rs.getString("clock_out"));
		dto.setVacationCategory(rs.getInt("vacation_category"));
		dto.setVacationNote(rs.getString("vacation_note"));
		dto.setDelflg(rs.getBoolean("delflg"));
		dto.setCreatedBy(rs.getInt("created_by"));
		dto.setCreatedAt(rs.getString("created_at"));
		dto.setUpdatedBy(rs.getInt("updated_by"));
		dto.setUpdatedAt(rs.getString("updated_at"));
		return dto;
	}
}
