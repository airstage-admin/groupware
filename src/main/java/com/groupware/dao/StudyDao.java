package com.groupware.dao;

import java.util.List;

import com.groupware.dto.StudyDto;

public interface StudyDao {
    /**
     * 勉強会参加時間を取得
     * @param targetMonth 対象年月
     * @return リスト
     */
	List<StudyDto> selectStudyTime(String targetMonth);
}