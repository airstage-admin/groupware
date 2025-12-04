package com.groupware.costs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.dao.UserDao;
import com.groupware.dto.UserDto;

/**
* MemberServiceImpl
* メンバー管理サービス
* @author
* @version　1.0.0
*/
@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private UserDao userDao;

	private static final int ALL_DEPARTMENTS = 0; // 全部署

	/**
	* メンバー（ユーザー）データ一覧を取得する
	* 
	* @return　List<UserDto>
	*/
	@Override
	public List<UserDto> findAll() {
		return userDao.findByUsersList(ALL_DEPARTMENTS);
	}

	/**
	* 指定IDのメンバーデータを取得する
	* 
	* @param　id ユーザーID
	* @return　UserDto
	*/
	@Override
	public UserDto findById(int id) {
		return userDao.findByUser(id);
	}
}

