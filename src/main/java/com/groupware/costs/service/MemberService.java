package com.groupware.costs.service;

import java.util.List;

import com.groupware.dto.UserDto;

/**
* MemberService
* メンバー管理サービスインターフェース
* @author
* @version　1.0.0
*/
public interface MemberService {
	/**
	* メンバー（ユーザー）データ一覧を取得する
	* 
	* @return　List<UserDto>
	*/
	List<UserDto> findAll();

	/**
	* 指定IDのメンバーデータを取得する
	* 
	* @param　id ユーザーID
	* @return　UserDto
	*/
	UserDto findById(int id);
}

