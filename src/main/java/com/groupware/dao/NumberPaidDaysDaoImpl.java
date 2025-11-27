package com.groupware.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
* NumberPaidDaysDao
* number_paid_daysテーブル用DAO
* @author　N.Hirai
* @version　1.0.0
*/
@Repository
public class NumberPaidDaysDaoImpl implements NumberPaidDaysDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(NumberPaidDaysDaoImpl.class);

	/**
	* 有給付与日数を取得する
	* 
	* @param　code 雇用区分コード
	* @param　passed 入社からの経過月数
	* @return　int 有給付与日数
	*/
	@Override
	public int findByPaidLeaveRranted(int code, int passed) {
		String sql = "SELECT paid_leave_granted FROM number_paid_days WHERE employee_code = ? AND months_passed <= ? ORDER BY months_passed DESC LIMIT 1";

		try {
			return jdbcTemplate.queryForObject(sql, Integer.class, code, passed);
		} catch (EmptyResultDataAccessException e) {
			// クエリ結果が0件の場合（例: passedが5以下の時）
			// ログに出力し、規定値である0を返す
			logger.warn("有給付与日数を取得できませんでした。employee_code={}、months_passed={}", code, passed);
			return 0;
		} catch (Exception e) {
			logger.error("有給付与日数を取得時、DBアクセスエラー", e);
			return 0;
		}
	}
}
