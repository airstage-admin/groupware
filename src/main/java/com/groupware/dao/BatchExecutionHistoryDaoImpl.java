package com.groupware.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
* BatchExecutionHistoryDaoImpl
* batch_execution_historyテーブル用DAO
* @author　N.Hirai
* @version　1.0.0
*/
@Repository
public class BatchExecutionHistoryDaoImpl implements BatchExecutionHistoryDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(BatchExecutionHistoryDaoImpl.class);

	/**
	* バッチ実行履歴を作成する
	* @param　batch_name バッチ名称
	* @param　result 結果
	* @return　
	*/
	@Override
	public void insert(String batch_name, boolean result) {
		String sql = "INSERT INTO batch_execution_history "
				+ "(batch_name, execution_date, result) "
				+ "VALUES (?, CURDATE(), ?)";
		try {
			jdbcTemplate.update(sql, batch_name, result);
		} catch (Exception e) {
			logger.error("バッチ実行履歴書込み時、DBアクセスエラー", e);
		}
	}

	/**
	* 対象バッチが実行月に起動成功したかチェック 
	* 
	* @param　batch_name バッチ名称
	* @return　boolean
	*/
	@Override
	public boolean isBatchExecution(String batch_name) {
		String sql = "SELECT COUNT(*) FROM batch_execution_history WHERE batch_name = ? AND DATE_FORMAT(execution_date, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m') AND result = true";
		try {
			Integer count = jdbcTemplate.queryForObject(sql, Integer.class, batch_name);
			return count != null && count > 0;
		} catch (Exception e) {
			logger.error("対象バッチの実行月に起動成功したかチェック時、DBアクセスエラー", e);
			return false;
		}
	}
}