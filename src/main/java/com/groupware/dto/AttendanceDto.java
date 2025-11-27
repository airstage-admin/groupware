package com.groupware.dto;

import java.time.LocalDate;
import java.time.LocalTime;

/**
* AttendanceDto
* attendanceテーブル用DTO
* @author　N.Hirai
* @version　1.0.0
*/
public class AttendanceDto {
	private long id;
	private long user_id;
	private int place_work;
	private String place_work_name;
	private LocalDate working_day;
	private int week;
	private int holiday;
	private String clock_in;
	private String clock_out;
	private LocalTime break_time;
	private LocalTime night_break_time;
	private int vacation_category;
	private String vacation_note;
	private boolean delflg;
	private int created_by;
	private String created_at;
	private int updated_by;
	private String updated_at;

	// 勤怠ID
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	// ユーザーID
	public long getUserId() {
		return user_id;
	}

	public void setUserId(long user_id) {
		this.user_id = user_id;
	}

	// 勤務先種別
	public int getPlaceWork() {
		return place_work;
	}

	public void setPlaceWork(int place_work) {
		this.place_work = place_work;
	}

	// 勤務先名
	public String getPlaceWorkName() {
		return place_work_name;
	}

	public void setPlaceWorkName(String place_work_name) {
		this.place_work_name = place_work_name;
	}

	// 勤怠年月日
	public LocalDate getWorkingDay() {
		return working_day;
	}

	public void setWorkingDay(LocalDate working_day) {
		this.working_day = working_day;
	}

	// 曜日
	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	// 祝日
	public int getHoliday() {
		return holiday;
	}

	public void setHoliday(int holiday) {
		this.holiday = holiday;
	}

	// 出勤時刻
	public String getClockIn() {
		return clock_in;
	}

	public void setClockIn(String clock_in) {
		this.clock_in = clock_in;
	}

	// 退勤時刻
	public String getClockOut() {
		return clock_out;
	}

	public void setClockOut(String clock_out) {
		this.clock_out = clock_out;
	}

	// 休息時間
	public LocalTime getBreakTime() {
		return break_time;
	}

	public void setBreakTime(LocalTime break_time) {
		this.break_time = break_time;
	}

	// 深夜休息時間
	public LocalTime getNightBreakTime() {
		return night_break_time;
	}

	public void setNightBreakTime(LocalTime night_break_time) {
		this.night_break_time = night_break_time;
	}

	// 休暇区分
	public int getVacationCategory() {
		return vacation_category;
	}

	public void setVacationCategory(int code) {
		this.vacation_category = code;
	}

	// 休暇備考
	public String getVacationNote() {
		return vacation_note;
	}

	public void setVacationNote(String vacation_note) {
		this.vacation_note = vacation_note;
	}

	// 削除フラグ
	public boolean getDelflg() {
		return delflg;
	}

	public void setDelflg(boolean delflg) {
		this.delflg = delflg;
	}

	// 作成者ID
	public int getCreatedBy() {
		return created_by;
	}

	public void setCreatedBy(int created_by) {
		this.created_by = created_by;
	}

	// 作成日時
	public String getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}

	// 更新者ID
	public int getUpdatedBy() {
		return updated_by;
	}

	public void setUpdatedBy(int updated_by) {
		this.updated_by = updated_by;
	}

	// 更新日時
	public String getUpdatedAt() {
		return updated_at;
	}

	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
}
