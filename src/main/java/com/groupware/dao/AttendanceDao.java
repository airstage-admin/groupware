package com.groupware.dao;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.groupware.common.constant.HolidayCategory;
import com.groupware.common.constant.WeekCategory;
import com.groupware.dto.AttendanceDto;

/**
* AttendanceDao
* attendanceテーブル用DAOインターフェース
* @author N.Hirai
* @version 1.0.0
*/
public interface AttendanceDao {
	/**
	* 対象月の勤怠データをattendanceテーブルに作成する
	* 
	* @param userId ユーザーID
	* @param date 対象年月日
	* @param weekEnum WeekCategoryのENUM値
	* @param holiday HolidayCategoryのENUM値
	* @param placeCode 勤務先コード
	* @return 
	*/
	void insert(long userId, LocalDate date, WeekCategory weekEnum, HolidayCategory holidayEnum, int placeCode);

	/**
	* 入力された勤怠データをユーザーの当月データを取得する
	* 
	* @param userId ユーザーID
	* @param ym 当月
	* @param placeCode 勤務先コード
	* @return List<AttendanceDto>
	*/
	List<AttendanceDto> findByUserAndMonth(long userId, YearMonth ym, int placeCode);

	/**
	* ユーザーの当月データを取得する
	* 
	* @param userId ユーザーID
	* @param ym 当月
	* @return List<AttendanceDto>
	*/
	List<AttendanceDto> findByUserAndMonthPaidDate(long userId, YearMonth ym);
	
	/**
	* 入力された勤怠データをIDで取得する
	* 
	* @param id AttendanceDtoのID
	* @return AttendanceDto
	*/
	AttendanceDto findById(long id);

	/**
	* 勤怠データを更新する
	* 
	* @param dto saveAttendanceDTO
	* @param id 更新者id
	* @return 
	*/
	void update(AttendanceDto dto, int id);

	/**
	* 入力された勤怠データを論理削除する
	* 
	* @param id AttendanceDtoのID
	* @param updatedBy 削除者のID
	* @return 
	*/
	void delete(long id, int updatedBy);

	/**
	* 今月の勤怠初期データがあるかチェック 
	* 
	* @param userId ユーザーID
	* @param ym 対象年月
	* @return boolean
	*/
	boolean existsByInitialAttendanceDate(long userId, YearMonth ym);

    /**
     * 指定月の全社員の勤怠データを取得（全勤務先含む）
     * @param ym 対象年月
     * @return List<AttendanceDto>
     */
    List<AttendanceDto> findAllByMonth(YearMonth ym);

}