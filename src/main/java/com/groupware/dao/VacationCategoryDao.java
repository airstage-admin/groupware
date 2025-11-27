package com.groupware.dao;

import java.util.List;

import com.groupware.dto.VacationCategoryDto;

/**
* VacationCategoryDao
* vacation_categoryテーブル用DAOインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
public interface VacationCategoryDao {
	/**
	* 勤怠休暇区分データ一覧を取得する
	* 
	* @return　List<VacationCategoryDto>
	*/
	List<VacationCategoryDto> findByVacationCategoryList();
}
