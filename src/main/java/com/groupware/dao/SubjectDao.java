package com.groupware.dao;

import java.util.List;

import com.groupware.dto.SubjectDto;

/**
* SubjectDao
* subjectsテーブル用DAOインターフェース（勘定科目マスタ）
* @author
* @version　1.0.0
*/
public interface SubjectDao {
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

