package com.groupware.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.groupware.dto.DepartmentTypeDto;

/**
* DepartmentTypeDaoImpl
* department_typeテーブル用DAO
* @author　N.Hirai
* @version　1.0.0
*/
@Repository
public class DepartmentTypeDaoImpl implements DepartmentTypeDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(DepartmentTypeDaoImpl.class);

	/**
	* 部署データ一覧を取得する
	* 
	* @return　List<DepartmentTypeDto>
	*/
	@Override
	public List<DepartmentTypeDto> findByDepartmentList() {
		String sql = "SELECT * FROM department_type ORDER BY code";
		try {
			return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
		} catch (Exception e) {
			logger.error("部署データ取得時、DBアクセスエラー", e);
			return null;
		}
	}

	/**
	* ログインユーザーの管理者区分チェック
	* 
	* @param　id ログインID
	* @return　false：非管理者、true：管理者
	*/
	@Override
	public boolean is_admin(int id) {
		String sql = "SELECT dt.is_admin FROM users AS u JOIN department_type AS dt ON u.department = dt.code WHERE u.id = ?";
		try {
			Boolean isAdmin = jdbcTemplate.queryForObject(sql, Boolean.class, id);

            return isAdmin != null && isAdmin;
		} catch (Exception e) {
			logger.error("管理区分チェック時、DBアクセスエラー", e);
			return false;
		}
	}
	
	// RowMapper
	private DepartmentTypeDto mapRow(ResultSet rs) throws SQLException {
		DepartmentTypeDto dto = new DepartmentTypeDto();
		dto.setId(rs.getInt("id"));
		dto.setCode(rs.getInt("code"));
		dto.setDisplayName(rs.getString("display_name"));
		dto.setIsAdmin(rs.getBoolean("is_admin"));
		return dto;
	}

}