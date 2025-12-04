package com.groupware.costs.service;

import java.util.List;

import com.groupware.dto.SubjectDto;

/**
* SubjectService
* 勘定科目サービスインターフェース
* @author
* @version　1.0.0
*/
public interface SubjectService {
	/**
	* 勘定科目データ一覧を取得する
	* 
	* @return　List<SubjectDto>
	*/
	List<SubjectDto> findAll();

	/**
	* 指定IDの勘定科目データを取得する
	* 
	* @param　id 勘定科目ID
	* @return　SubjectDto
	*/
	SubjectDto findById(long id);
}

