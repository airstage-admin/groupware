package com.groupware.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.groupware.dto.InternalPJDto;

@Mapper
public interface InternalPJDao {
    /**
     * 社内PJ対応時間を取得
     * @param targetMonth 対象年月
     * @return リスト
     */
    List<InternalPJDto> selectInternalProjectWorkTime(@Param("targetMonth") String targetMonth);
}