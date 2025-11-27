package com.groupware.dto;

/**
* PublicHolidayDto
* public_holidayテーブル用DTO
* @author　N.Hirai
* @version　1.0.0
*/

public class PublicHolidayDto {
	private int id;
	private int month;
	private int day;

	// ID
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// 月
	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	// 日
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
}
