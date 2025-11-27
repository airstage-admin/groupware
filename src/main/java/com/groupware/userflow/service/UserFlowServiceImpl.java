package com.groupware.userflow.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.dao.DepartmentTypeDao;
import com.groupware.dao.EmployeeCategoryDao;
import com.groupware.dao.PlaceCategoryDao;
import com.groupware.dao.UserDao;
import com.groupware.dao.VacationCategoryDao;
import com.groupware.dto.DepartmentTypeDto;
import com.groupware.dto.EmployeeCategoryDto;
import com.groupware.dto.PlaceCategoryDto;
import com.groupware.dto.UserDto;
import com.groupware.dto.VacationCategoryDto;
import com.groupware.userflow.controller.UserFlowController;

/**
* UserServiceImpl
* Userサービス
* @author　N.Hirai
* @version　1.0.0
*/
@Service
public class UserFlowServiceImpl implements UserFlowService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private DepartmentTypeDao departmentTypeDao;

	@Autowired
	private VacationCategoryDao vacationCategoryDao;

	@Autowired
	private EmployeeCategoryDao employeeCategoryDao;
	
	@Autowired
	private PlaceCategoryDao placeCategoryDao;
	
	private static final Logger logger = LoggerFactory.getLogger(UserFlowController.class);

	/**
	* 入力されたloginid、Passwordでログイン処理を行う
	* 
	* @param　loginid ログインID
	* @param　password パスワード
	* @return　
	*/
	@Override
	public UserDto login(String loginid, String password) {
		return userDao.login(loginid, password);
	}

	/**
	* 部署データ一覧を取得する
	* 
	* @return　List<DepartmentTypeDto>
	*/
	@Override
	public List<DepartmentTypeDto> findByDepartmentList() {
		return departmentTypeDao.findByDepartmentList();
	}

	/**
	* 勤怠休暇区分データ一覧を取得する
	* 
	* @return　List<VacationCategoryDto>
	*/
	@Override
	public List<VacationCategoryDto> findByVacationCategoryList() {
		return vacationCategoryDao.findByVacationCategoryList();
	}

	/**
	* 社員区分データ一覧を取得する
	* 
	* @return　List<EmployeeCategoryDto>
	*/
	@Override
	public List<EmployeeCategoryDto> findByEmployeeCategoryList() {
		return employeeCategoryDao.findByEmployeeCategoryList();
	}

	/**
	* 勤務先区分データ一覧を取得する
	* 
	* @return　List<PlaceCategoryDto>
	*/
	@Override
	public List<PlaceCategoryDto> findByPlaceCategoryList() {
		return placeCategoryDao.findByPlaceCategoryList();
	}
	
	/**
	* ログインユーザーの管理者区分チェック
	* 
	* @param　id ログインID
	* @return　false：非管理者、true：管理者
	*/
	@Override
	public boolean is_admin(int id) {
		return departmentTypeDao.is_admin(id);
	}
}
