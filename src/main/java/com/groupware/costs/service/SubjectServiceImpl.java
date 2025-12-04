package com.groupware.costs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.dao.SubjectDao;
import com.groupware.dto.SubjectDto;

/**
* SubjectServiceImpl
* 勘定科目サービス
* @author
* @version　1.0.0
*/
@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	private SubjectDao subjectDao;

	/**
	* 勘定科目データ一覧を取得する
	* 
	* @return　List<SubjectDto>
	*/
	@Override
	public List<SubjectDto> findAll() {
		return subjectDao.findAll();
	}

	/**
	* 指定IDの勘定科目データを取得する
	* 
	* @param　id 勘定科目ID
	* @return　SubjectDto
	*/
	@Override
	public SubjectDto findById(long id) {
		return subjectDao.findById(id);
	}
}

