package com.groupware.attendance.form;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.groupware.attendance.validation.ClockOrderCheck;
import com.groupware.common.constant.CommonConstants;

@ClockOrderCheck
public class AttendanceForm {
	private Long id;
	private int userId;
	private String workingDay;
	private String placeWork;

	@Size(max = 256, message = "勤務場所名は256文字以内で入力してください。")
	private String placeWorkName;

	@Pattern(regexp = CommonConstants.CLOCK_REGEX, message = "出勤時刻を正しく入力してください。")
	private String clockIn;

	@Pattern(regexp = CommonConstants.CLOCK_REGEX, message = "退勤時刻を正しく入力してください。")
	private String clockOut;

	@Pattern(regexp = CommonConstants.BREAK_REGEX, message = "休憩時刻を正しく入力してください。")
	private String breakTime;

	@Pattern(regexp = CommonConstants.BREAK_REGEX, message = "深夜休憩時刻を正しく入力してください。")
	private String nightBreakTime;

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
