package com.groupware.dao;

import java.util.List;

import com.groupware.dto.EmployeeCategoryDto;

/**
* EmployeeCategoryDao
* employee_categoryテーブル用DAOインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
public interface EmployeeCategoryDao {
	/**
	* 社員区分データ一覧を取得する
	* 
	* @return　List<EmployeeCategoryDto>
	*/
	List<EmployeeCategoryDto> findByEmployeeCategoryList();
}
