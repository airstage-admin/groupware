package com.groupware.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.groupware.dto.PublicHolidayDto;

/**
* PublicHolidayDaoImpl
* public_holidayテーブル用DAO
* @author　N.Hirai
* @version　1.0.0
*/
@Repository
public class PublicHolidayDaoImpl implements PublicHolidayDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(PublicHolidayDaoImpl.class);

	/**
	* 公休日データ一覧を取得する
	* 
	* @return　List<PublicHolidayDto>
	*/
	@Override
	public List<PublicHolidayDto> findByHolidayList() {
		String sql = "SELECT * FROM public_holiday";
		try {
			return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
		} catch (Exception e) {
			logger.error("公休日データ取得時、DBアクセスエラー", e);
			return null;
		}
	}

	// RowMapper
	private PublicHolidayDto mapRow(ResultSet rs) throws SQLException {
		PublicHolidayDto dto = new PublicHolidayDto();
		dto.setId(rs.getInt("id"));
		dto.setMonth(rs.getInt("month"));
		dto.setDay(rs.getInt("day"));
		return dto;
	}
}