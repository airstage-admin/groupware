package com.groupware.dao;

import java.util.List;

import com.groupware.dto.PlaceCategoryDto;

/**
* PlaceCategoryDao
* place_categoryテーブル用DAOインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
public interface PlaceCategoryDao {
	/**
	* 勤務先データ一覧を取得する
	* 
	* @return　List<PlaceCategoryDto>
	*/
	List<PlaceCategoryDto> findByPlaceCategoryList();
}
