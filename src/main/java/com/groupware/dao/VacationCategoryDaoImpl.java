package com.groupware.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.groupware.dto.VacationCategoryDto;

/**
* VacationCategoryDao
* vacation_categoryテーブル用DAOインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
@Repository
public class VacationCategoryDaoImpl implements VacationCategoryDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(VacationCategoryDaoImpl.class);

	/**
	* 勤怠休暇区分データ一覧を取得する
	* 
	* @return　List<VacationCategoryDto>
	*/
	@Override
	public List<VacationCategoryDto> findByVacationCategoryList() {
		String sql = "SELECT * FROM vacation_category ORDER BY code";
		try {
			return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
		} catch (Exception e) {
			logger.error("勤怠休暇区分データ取得時、DBアクセスエラー", e);
			return null;
		}
	}
	
	// RowMapper
	private VacationCategoryDto mapRow(ResultSet rs) throws SQLException {
		VacationCategoryDto dto = new VacationCategoryDto();
		dto.setId(rs.getInt("id"));
		dto.setCode(rs.getInt("code"));
		dto.setDisplayName(rs.getString("display_name"));
		dto.setIsPaid(rs.getBoolean("is_paid"));
		dto.setPaidDate(rs.getFloat("paid_date"));
		return dto;
	}

}