package com.groupware.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.groupware.dto.SubjectDto;

/**
* SubjectDaoImpl
* subjectsテーブル用DAO（勘定科目マスタ）
* @author
* @version　1.0.0
*/
@Repository
public class SubjectDaoImpl implements SubjectDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(SubjectDaoImpl.class);

	/**
	* 勘定科目データ一覧を取得する
	* 
	* @return　List<SubjectDto>
	*/
	@Override
	public List<SubjectDto> findAll() {
		String sql = "SELECT * FROM subjects ORDER BY id";
		try {
			return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
		} catch (Exception e) {
			logger.error("勘定科目データ取得時、DBアクセスエラー", e);
			return null;
		}
	}

	/**
	* 指定IDの勘定科目データを取得する
	* 
	* @param　id 勘定科目ID
	* @return　SubjectDto
	*/
	@Override
	public SubjectDto findById(long id) {
		String sql = "SELECT * FROM subjects WHERE id = ?";
		try {
			List<SubjectDto> results = jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs), id);
			return results.isEmpty() ? null : results.get(0);
		} catch (Exception e) {
			logger.error("勘定科目データ取得時、DBアクセスエラー", e);
			return null;
		}
	}

	// RowMapper
	private SubjectDto mapRow(ResultSet rs) throws SQLException {
		SubjectDto dto = new SubjectDto();
		dto.setId(rs.getLong("id"));
		dto.setName(rs.getString("name"));
		dto.setDescription(rs.getString("description"));
		return dto;
	}
}

