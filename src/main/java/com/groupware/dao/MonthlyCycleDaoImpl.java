package com.groupware.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.groupware.dto.MonthlyCycleDto;

/**
* MonthlyCycleDaoImpl
* monthly_cycleテーブル用DAO
* @author　N.Hirai
* @version　1.0.0
*/
@Repository
public class MonthlyCycleDaoImpl implements MonthlyCycleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(MonthlyCycleDaoImpl.class);

	/**
	* 付与月日を取得する
	* 
	* @param　date 対象年月日
	* @return　MonthlyCycleDto
	*/
	@Override
	public MonthlyCycleDto findByTargetDate(LocalDate date) {
		String sql = "SELECT * FROM monthly_cycle WHERE ? BETWEEN start_date AND end_date";
		try {
			return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapRow(rs), date);
		} catch (Exception e) {
			logger.error(" 付与月日を取得時、DBアクセスエラー", e);
			return null;
		}
	}
	
	// RowMapper
	private MonthlyCycleDto mapRow(ResultSet rs) throws SQLException {
		MonthlyCycleDto dto = new MonthlyCycleDto();
		dto.setId(rs.getInt("id"));
		dto.setStartDate(rs.getString("start_date"));
		dto.setEndDate(rs.getString("end_date"));
		dto.setTargetDate(rs.getString("target_date"));
		return dto;
	}
}
