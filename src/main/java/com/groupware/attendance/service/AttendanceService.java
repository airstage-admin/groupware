package com.groupware.attendance.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.groupware.dto.AttendanceApprovalDto;
import com.groupware.dto.AttendanceDto;
import com.groupware.dto.PublicHolidayDto;
import com.groupware.dto.UserApprovalDto;
import com.groupware.dto.UserDto;

/**
* AttendanceService
* Attendanceサービスインターフェース
* @author　N.Hirai
* @version　1.0.0
*/
public interface AttendanceService {
	/**
	* 入力された勤怠データをユーザーの当月データを取得する
	* 
	* @param　userId ユーザーID
	* @param　ym 当月
	* @param　placeCode 勤務先コード
	* @return　List<AttendanceDto>
	*/
	List<AttendanceDto> findByUserAndMonth(long userId, YearMonth ym, int placeEnum);

	/**
	* 入力された勤怠データをIDで取得する
	* 
	* @param　id AttendanceDtoのID
	* @return　AttendanceDto
	*/
	AttendanceDto findById(long id);

	/**
	* 勤怠データを更新する
	* 
	* @param　dto saveAttendanceDTO
	* @param　id 更新者id
	* @return　
	*/
	void update(AttendanceDto dto, int id);

	/**
	* 今月の勤怠初期データがあるかチェック 
	* 
	* @param　userId ユーザーID
	* @param　ym 対象年月
	* @return　boolean
	*/
	boolean existsByInitialAttendanceDate(long userId, YearMonth ym);

	/**
	* 指定年月の空の勤怠データを作成する
	* 
	* @param　id ユーザーID
	* @param　year 年
	* @param　month 月
	* @return　true：成功、false：エラー
	*/
	boolean makeMonthAttendance(long userId, int year, int month);
	
	/**
	* 有効な社員アカウントデータ一覧を取得する
	* 
	* @param　categoryCode 部署コード
	* @return　List<UserDto>
	*/
	List<UserDto> findByUsersList(int categoryCode);
	
	/**
	* 公休日データ一覧を取得する
	* 
	* @return　List<PublicHolidayDto>
	*/
	List<PublicHolidayDto> findByHolidayList();
	
	/**
	* ログインユーザーの管理者区分チェック
	* 
	* @param　id ログインID
	* @return　false：非管理者、true：管理者
	*/
	boolean is_admin(int id);
	
	/**
	* 勤怠確認承認テーブルInsert
	* 
	* @param　userId ユーザーID
	* @param　date 対象年月日
	* @param　approvalId 承認者ID
	* @return　
	*/
	void insertApproval(long userId, LocalDate date, long approvalId);

	/**
	* 当月データを取得する
	* 
	* @param　ym 当月
	* @return
	*/
	List<AttendanceApprovalDto> findByMonth(YearMonth ym);
	
	/**
	* attendance_approvalとJoinした社員データ一覧を取得する
	* 
	* @param　ym 対象年月
	* @return　List<UserApprovalDto>
	*/
	List<UserApprovalDto> findByUsersApprovalList(YearMonth ym);

	/**
	* 勤怠承認データがあるかチェック 
	* 
	* @param　userId ユーザーID
	* @param　ym 対象年月
	* @return　boolean
	*/
	boolean existsByApprovalDate(long userId, YearMonth ym);
	
	/**
	* 勤怠確認承認テーブルInsert
	* 
	* @param　userId ユーザーID
	* @param　date 対象年月日
	* @param　approvalId 承認者ID
	* @return　
	*/
	void approvalInsert(long userId, LocalDate date, long approvalId);
}
