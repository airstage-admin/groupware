package com.groupware.dao;

/**
* NumberPaidDaysDao
* number_paid_daysテーブル用DAOインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
public interface NumberPaidDaysDao {
	/**
	* 有給付与日数を取得する
	* 
	* @param　code 雇用区分コード
	* @param　passed 入社からの経過月数
	* @return　int 有給付与日数
	*/
	int findByPaidLeaveRranted(int code, int passed);
}
