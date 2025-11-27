package com.groupware.dao;

import java.util.List;

import com.groupware.dto.DepartmentTypeDto;

/**
* DepartmentTypeDao
* department_typeテーブル用DAOインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
public interface DepartmentTypeDao {
	/**
	* 部署データ一覧を取得する
	* 
	* @return　List<DepartmentTypeDto>
	*/
	List<DepartmentTypeDto> findByDepartmentList();
	
	/**
	* ログインユーザーの管理者区分チェック
	* 
	* @param　id ログインID
	* @return　false：非管理者、true：管理者
	*/
	boolean is_admin(int id);
}
