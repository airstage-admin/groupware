package com.groupware.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.groupware.dto.EmployeeCategoryDto;

/**
* EmployeeCategoryDao
* employee_categoryテーブル用DAOインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
@Repository
public class EmployeeCategoryDaoImpl implements EmployeeCategoryDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(EmployeeCategoryDaoImpl.class);

	/**
	* 勤怠休暇区分データ一覧を取得する
	* 
	* @return　List<EmployeeCategoryDto>
	*/
	@Override
	public List<EmployeeCategoryDto> findByEmployeeCategoryList() {
		String sql = "SELECT * FROM employee_category ORDER BY code";
		try {
			return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
		} catch (Exception e) {
			logger.error("社員区分データ取得時、DBアクセスエラー", e);
			return null;
		}
	}
	
	// RowMapper
	private EmployeeCategoryDto mapRow(ResultSet rs) throws SQLException {
		EmployeeCategoryDto dto = new EmployeeCategoryDto();
		dto.setId(rs.getInt("id"));
		dto.setCode(rs.getInt("code"));
		dto.setDisplayName(rs.getString("display_name"));
		return dto;
	}

}