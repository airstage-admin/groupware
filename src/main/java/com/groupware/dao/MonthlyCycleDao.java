package com.groupware.dao;

import java.time.LocalDate;

import com.groupware.dto.MonthlyCycleDto;

/**
* MonthlyCycleDao
* monthly_cycleテーブル用DAOインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
public interface MonthlyCycleDao {
	/**
	* 付与月日を取得する
	* 
	* @param　date 対象年月日
	* @return　MonthlyCycleDto
	*/
	MonthlyCycleDto findByTargetDate(LocalDate date);
}
