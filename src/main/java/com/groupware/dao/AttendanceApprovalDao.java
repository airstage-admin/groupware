package com.groupware.dao;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.groupware.dto.AttendanceApprovalDto;
import com.groupware.dto.AttendanceDto;

/**
* AttendanceApprovalDao
* attendance_approvalテーブル用DAOインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
public interface AttendanceApprovalDao {
	/**
	* 勤怠確認承認テーブルInsert
	* 
	* @param　userId ユーザーID
	* @param　date 対象年月日
	* @param　approvalId 承認者ID
	* @return　
	*/
	void insert(long userId, LocalDate date, long approvalId);

	/**
	* 当月データを取得する
	* 
	* @param　ym 当月
	* @return
	*/
	List<AttendanceApprovalDto> findByMonth(YearMonth ym);

	/**
	* 勤怠データを更新する
	* 
	* @param　dto saveAttendanceDTO
	* @param　id 更新者id
	* @return　
	*/
	void update(AttendanceDto dto, int id);

	/**
	* 入力された勤怠データを論理削除する
	* 
	* @param　id AttendanceDtoのID
	* @param　updatedBy 削除者のID
	* @return　
	*/
	void delete(long id, int updatedBy);

	/**
	* 勤怠承認データがあるかチェック 
	* 
	* @param　userId ユーザーID
	* @param　ym 対象年月
	* @return　boolean
	*/
	boolean existsByApprovalDate(long userId, YearMonth ym);
}
