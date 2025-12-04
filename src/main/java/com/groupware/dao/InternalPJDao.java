package com.groupware.dao;

import java.util.List;

import com.groupware.dto.InternalPJDto;

public interface InternalPJDao {
    /**
     * 社内PJ対応時間を取得
     * @param targetMonth 対象年月
     * @return リスト
     */
	List<InternalPJDto> selectInternalProjectWorkTime(String targetMonth);
}