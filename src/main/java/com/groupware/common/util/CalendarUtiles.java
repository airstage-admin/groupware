package com.groupware.common.util;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.groupware.common.constant.CommonConstants;
import com.groupware.common.constant.HolidayCategory;
import com.groupware.common.constant.WeekCategory;
import com.samuraism.holidays.日本の祝休日;

/**
* CalendarUtiles
* カレンダーユーティリティ
* @author　N.Hirai
* @version　1.0.0
*/
public final class CalendarUtiles {

	// インスタンス化を禁止するためにプライベートコンストラクタを定義
	private CalendarUtiles() {
		throw new AssertionError("Constant class should not be instantiated.");
	}

	/**
	* 指定された年月日の曜日を取得する
	* 
	* @param　date 指定年月日
	* @return　曜日
	*/
	public static String getWeekendHoliday(LocalDate specifiedDate) {
		try {
			return specifiedDate.format(DateTimeFormatter.ofPattern(CommonConstants.WEEK_NAME, Locale.JAPAN));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	* 指定された年月日の祝日判定
	* 
	* @param　specifiedDate 指定年月日
	* @return　HolidayCategory
	*/
	public static HolidayCategory isHoliday(LocalDate specifiedDate) {
		日本の祝休日 holidays = new 日本の祝休日();

		if (holidays.is祝休日(specifiedDate)) {
			return HolidayCategory.HOLIDAY;
		} else {
			return CommonConstants.TARGET_DAYS.contains(MonthDay.from(specifiedDate))
					? HolidayCategory.PUBLIC
					: HolidayCategory.NONE;
		}
	}

	/**
	* 指定年月の歴数を取得する
	* 
	* @param　year 年
	* @param　month 月
	* @return　指定年月の歴数
	*/
	public int getNumberYearsCount(int year, int month) {
		return (int) getDaysStreamInMonth(year, month).count();
	}

	/**
	* 指定年月の休日を取得する
	* 
	* @param　year 年
	* @param　month 月
	* @return　指定年月の休日
	*/
	public int getWeekHolidayCount(int year, int month) {
		long weekendHolidayCount = getDaysStreamInMonth(year, month)
				.filter(date -> {
					// 処理対象日付の曜日取得
					String weekDay = CalendarUtiles.getWeekendHoliday(date);

					// 曜日のENUM値を取得
					Optional<WeekCategory> week = WeekCategory.ofJapaneseName(weekDay);
					WeekCategory weekEnum = week.orElse(WeekCategory.SUNDAY);

					// 処理対象日付の祝日のENUM値を取得
					HolidayCategory holidayEnum = CalendarUtiles.isHoliday(date);

					// フィルタリング条件
					boolean isWeekend = weekEnum == WeekCategory.SATURDAY || weekEnum == WeekCategory.SUNDAY;
					boolean isHoliday = holidayEnum == HolidayCategory.HOLIDAY || holidayEnum == HolidayCategory.PUBLIC;

					// 土日、または祝日/公休日の場合にtrueを返す
					return isWeekend || isHoliday;
				})
				.count();
		return (int) weekendHolidayCount;
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
}
