package com.groupware.userflow.service;

import java.util.List;

import com.groupware.dto.DepartmentTypeDto;
import com.groupware.dto.EmployeeCategoryDto;
import com.groupware.dto.PlaceCategoryDto;
import com.groupware.dto.UserDto;
import com.groupware.dto.VacationCategoryDto;

/**
* UserService
* Userサービスインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
public interface UserFlowService {
	/**
	* 入力されたloginid、Passwordでログイン処理を行う
	* 
	* @param　loginid ログインID
	* @param　password パスワード
	* @return　UserDto
	*/
	UserDto login(String loginid, String password);
	
	/**
	* 部署データ一覧を取得する
	* 
	* @return　List<DepartmentTypeDto>
	*/
	List<DepartmentTypeDto> findByDepartmentList();
	
	/**
	* 勤怠休暇区分データ一覧を取得する
	* 
	* @return　List<VacationCategoryDto>
	*/
	List<VacationCategoryDto> findByVacationCategoryList();
	
	/**
	* 社員区分データ一覧を取得する
	* 
	* @return　List<EmployeeCategoryDto>
	*/
	List<EmployeeCategoryDto> findByEmployeeCategoryList();
	
	/**
	* 勤務先区分データ一覧を取得する
	* 
	* @return　List<PlaceCategoryDto>
	*/
	List<PlaceCategoryDto> findByPlaceCategoryList();
	
	/**
	* ログインユーザーの管理者区分チェック
	* 
	* @param　id ログインID
	* @return　false：非管理者、true：管理者
	*/
	boolean is_admin(int id);
}
