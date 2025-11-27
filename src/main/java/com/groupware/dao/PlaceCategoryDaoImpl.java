package com.groupware.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.groupware.dto.PlaceCategoryDto;

/**
* PlaceCategoryDaoImpl
* place_categoryテーブル用DAO
* @author　N.Hirai
* @version　1.0.0
*/
@Repository
public class PlaceCategoryDaoImpl implements PlaceCategoryDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(PlaceCategoryDaoImpl.class);

	/**
	* 部署データ一覧を取得する
	* 
	* @return　List<PlaceCategoryDto>
	*/
	@Override
	public List<PlaceCategoryDto> findByPlaceCategoryList() {
		String sql = "SELECT * FROM place_category ORDER BY code";
		try {
			return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
		} catch (Exception e) {
			logger.error("勤務先データ取得時、DBアクセスエラー", e);
			return null;
		}
	}
	
	// RowMapper
	private PlaceCategoryDto mapRow(ResultSet rs) throws SQLException {
		PlaceCategoryDto dto = new PlaceCategoryDto();
		dto.setId(rs.getInt("id"));
		dto.setCode(rs.getInt("code"));
		dto.setDisplayName(rs.getString("display_name"));
		dto.setIsName(rs.getBoolean("is_name"));
		return dto;
	}

}