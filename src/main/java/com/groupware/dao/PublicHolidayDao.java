package com.groupware.dao;

import java.util.List;

import com.groupware.dto.PublicHolidayDto;

/**
* PublicHolidayDao
* public_holidayテーブル用DAOインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
public interface PublicHolidayDao {
	/**
	* 公休日データ一覧を取得する
	* 
	* @return　List<PublicHolidayDto>
	*/
	List<PublicHolidayDto> findByHolidayList();
}
