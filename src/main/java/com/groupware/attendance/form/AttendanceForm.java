package com.groupware.attendance.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.groupware.attendance.validation.BreakTimeCheck;
import com.groupware.attendance.validation.ClockOrderCheck;
import com.groupware.attendance.validation.NightBreakCheck;
import com.groupware.common.constant.CommonConstants;

@ClockOrderCheck
@NightBreakCheck
@BreakTimeCheck 
public class AttendanceForm {
	private Long id;
	private int userId;
	private String workingDay;
	private String placeWork;

	//勤務場所
	@Size(max = CommonConstants.PLACE_WORK_NAME_MAX_LENGTH, 
	      message = "勤務場所名は{max}文字以内で入力してください。")
	private String placeWorkName;

	//出勤時刻
	@NotBlank(message = "出勤時刻を入力してください。")
	@Pattern(regexp = CommonConstants.CLOCK_REGEX, message = "出勤時刻は0:00~31:59の値で入力してください。")
	private String clockIn;
	
	//退勤時刻
	@NotBlank(message = "退勤時刻を入力してください。")
	@Pattern(regexp = CommonConstants.CLOCK_REGEX, message = "退勤時刻は0:00~31:59の値で入力してください。")
	private String clockOut;

	//休憩時間
	@NotBlank(message = "休憩時間を入力してください。")
    @Pattern(regexp = CommonConstants.BREAK_RANGE_REGEX, message = "休憩時間は0:00~10:00の値で入力してください。")
    @Pattern(regexp = CommonConstants.BREAK_INTERVAL_REGEX, message = "休憩時間は15分単位で記入してください。")
    private String breakTime;

	//深夜休憩時間
	@Pattern(regexp = CommonConstants.NIGHT_BREAK_RANGE_REGEX, message = "深夜休憩時間は0:00~7:00の値で入力してください。")
	@Pattern(regexp = CommonConstants.BREAK_INTERVAL_REGEX, message = "深夜休憩時間は15分単位で記入してください。")
	private String nightBreakTime;
	
	//その他属性
	private String vacationCategory;
	private String vacationNote;

	// ==========================================================
	//                        Getter/Setter
	// ==========================================================
	// id の GetterSetter
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// userId の GetterSetter
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	// workingDay の GetterSetter
	public String getWorkingDay() {
		return workingDay;
	}

	public void setWorkingDay(String workingDay) {
		this.workingDay = workingDay;
	}

	// placeWork の GetterSetter
	public String getPlaceWork() {
		return placeWork;
	}

	public void setPlaceWork(String placeWork) {
		this.placeWork = placeWork;
	}

	// placeWorkName の GetterSetter
	public String getPlaceWorkName() {
		return placeWorkName;
	}

	public void setPlaceWorkName(String placeWorkName) {
		this.placeWorkName = placeWorkName;
	}

	// clockIn の GetterSetter
	public String getClockIn() {
		return clockIn;
	}

	public void setClockIn(String clockIn) {
		this.clockIn = clockIn;
	}

	// clockOut の GetterSetter
	public String getClockOut() {
		return clockOut;
	}

	public void setClockOut(String clockOut) {
		this.clockOut = clockOut;
	}

	// breakTime の GetterSetter
	public String getBreakTime() {
		return breakTime;
	}

	public void setBreakTime(String breakTime) {
		this.breakTime = breakTime;
	}

	// nightBreakTime の GetterSetter
	public String getNightBreakTime() {
		return nightBreakTime;
	}

	public void setNightBreakTime(String nightBreakTime) {
		this.nightBreakTime = nightBreakTime;
	}

	// vacationCategory の GetterSetter
	public String getVacationCategory() {
		return vacationCategory;
	}

	public void setVacationCategory(String vacationCategory) {
		this.vacationCategory = vacationCategory;
	}

	// vacationNote の GetterSetter
	public String getVacationNote() {
		return vacationNote;
	}

	public void setVacationNote(String vacationNote) {
		this.vacationNote = vacationNote;
	}
}
