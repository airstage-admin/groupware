package com.groupware.batch.service;

import java.time.YearMonth;

/**
* BatchService
* 出力サービスインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
public interface BatchService {
	/**
	* 有給処理を行う
	* 
	* @param　ym 作業対象年月
	* @return
	*/
	void paidLeaveDate(YearMonth ym);

}
