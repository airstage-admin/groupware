package com.groupware.attendance.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.common.constant.HolidayCategory;
import com.groupware.common.constant.WeekCategory;
import com.groupware.common.registry.PlaceCategoryRegistry;
import com.groupware.common.util.CalendarUtiles;
import com.groupware.dao.AttendanceApprovalDao;
import com.groupware.dao.AttendanceDao;
import com.groupware.dao.DepartmentTypeDao;
import com.groupware.dao.PublicHolidayDao;
import com.groupware.dao.UserDao;
import com.groupware.dto.AttendanceApprovalDto;
import com.groupware.dto.AttendanceDto;
import com.groupware.dto.PublicHolidayDto;
import com.groupware.dto.UserApprovalDto;
import com.groupware.dto.UserDto;

/**
* AttendanceServiceImpl
* Attendanceサービス
* @author　N.Hirai
* @version　1.0.0
*/
@Service
public class AttendanceServiceImpl implements AttendanceService {
	@Autowired
	private AttendanceDao attendanceDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PublicHolidayDao publicHolidayDao;
	
	@Autowired
	private DepartmentTypeDao departmentTypeDao;	
	
	@Autowired
	private AttendanceApprovalDao attendanceApprovalDao;	
	
	/**
	* 入力された勤怠データをユーザーの当月データを取得する
	* 
	* @param　userId ユーザーID
	* @param　ym 当月
	* @param　placeCode 勤務先コード
	* @return　
	*/
	@Override
	public List<AttendanceDto> findByUserAndMonth(long userId, YearMonth ym, int placeCode) {
		return attendanceDao.findByUserAndMonth(userId, ym, placeCode);
	}

	/**
	* 入力された勤怠データをIDで取得する
	* 
	* @param　id AttendanceDtoのID
	* @return　
	*/
	@Override
	public AttendanceDto findById(long id) {
		return attendanceDao.findById(id);
	}

	/**
	* 勤怠データを更新する
	* 
	* @param　dto saveAttendanceDTO
	* @param　id 更新者id
	* @return　
	*/
	@Override
	@Transactional
	public void update(AttendanceDto dto, int id) {
		attendanceDao.update(dto, id);
	}

	/**
	* 今月の勤怠初期データがあるかチェック 
	* 
	* @param　userId ユーザーID
	* @param　ym 対象年月
	* @return　boolean
	*/
	@Override
	public boolean existsByInitialAttendanceDate(long userId, YearMonth ym) {
		return attendanceDao.existsByInitialAttendanceDate(userId, ym);
	}

	/**
	* 指定年月の空の勤怠データを作成する
	* 
	* @param　id ユーザーID
	* @param　year 年
	* @param　month 月
	* @return　true：成功、false：エラー
	*/
	@Override
	@Transactional
	public boolean makeMonthAttendance(long userId, int year, int month) {
		try {
			 Map<Integer, String> placeCategoryMap = PlaceCategoryRegistry.placeCategorySelectSet();
			 
			getDaysStreamInMonth(year, month)
					.forEach(date -> {
						// 処理対象日付の曜日取得
						String weekDay = CalendarUtiles.getWeekendHoliday(date); // 日、月、火、水、木、金、土

						// 曜日のENUM値を取得
						Optional<WeekCategory> week = WeekCategory.ofJapaneseName(weekDay);
						WeekCategory weekEnum = week.orElse(WeekCategory.SUNDAY); // 見つからない場合はSUNDAY
						
						// 処理対象日付の祝日のENUM値を取得
						HolidayCategory holidayEnum = CalendarUtiles.isHoliday(date);

						// userId, date, weekEnum, holidayを引数にAttendanceテーブルに初期データを書き込む（勤務先分）
						placeCategoryMap.forEach((placeId, placeName) -> {
							attendanceDao.insert(userId, date, weekEnum, holidayEnum, placeId);
						});
					});
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 指定年月の全ての日付を含むStream<LocalDate>を取得する。
	 *
	 * @param year 年
	 * @param month 月
	 * @return 指定年月の全ての日付のストリーム
	 */
	private Stream<LocalDate> getDaysStreamInMonth(int year, int month) {
		YearMonth targetMonth = YearMonth.of(year, month);
		LocalDate startDate = targetMonth.atDay(1);
		LocalDate endDate = targetMonth.atEndOfMonth();

		// endDateを含むため、日数の差を使用
		long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

		return IntStream.rangeClosed(0, (int) daysBetween).mapToObj(startDate::plusDays);
	}
	
	/**
	* 有効な社員アカウントデータ一覧を取得する
	* 
	* @param　categoryCode 部署コード
	* @return　List<UserDto>
	*/
	@Override
	public List<UserDto> findByUsersList(int categoryCode) {
		return userDao.findByUsersList(categoryCode);
	}
	
	/**
	* 公休日データ一覧を取得する
	* 
	* @return　List<PublicHolidayDto>
	*/
	@Override
	public List<PublicHolidayDto> findByHolidayList() {
		return publicHolidayDao.findByHolidayList();
	}

	/**
	* ログインユーザーの管理者区分チェック
	* 
	* @param　id ログインID
	* @return　false：非管理者、true：管理者
	*/
	@Override
	public boolean is_admin(int id) {
		return departmentTypeDao.is_admin(id);
	}

	@Override
	public void insertApproval(long userId, LocalDate date, long approvalId) {
		attendanceApprovalDao.insert(userId, date, approvalId);
	}

	/**
	* 当月データを取得する
	* 
	* @param　ym 当月
	* @return
	*/
	@Override
	public List<AttendanceApprovalDto> findByMonth(YearMonth ym) {
		return attendanceApprovalDao.findByMonth(ym);
	}

	/**
	* attendance_approvalとJoinした社員データ一覧を取得する
	* 
	* @param　ym 対象年月
	* @return　List<UserApprovalDto>
	*/
	@Override
	public List<UserApprovalDto> findByUsersApprovalList(YearMonth ym) {
		return userDao.findByUsersApprovalList(ym);
	}

	/**
	* 勤怠承認データがあるかチェック 
	* 
	* @param　userId ユーザーID
	* @param　ym 対象年月
	* @return　boolean
	*/
	@Override
	public boolean existsByApprovalDate(long userId, YearMonth ym) {
		return  attendanceApprovalDao.existsByApprovalDate(userId, ym);
	}

	/**
	* 勤怠確認承認テーブルInsert
	* 
	* @param　userId ユーザーID
	* @param　date 対象年月日
	* @param　approvalId 承認者ID
	* @return　
	*/
	@Override
	public void approvalInsert(long userId, LocalDate date, long approvalId) {
		attendanceApprovalDao.insert(userId, date, approvalId);
	}
}
