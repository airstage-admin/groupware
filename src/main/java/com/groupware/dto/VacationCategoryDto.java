package com.groupware.dto;

/**
* VacationCategoryDto
* vacation_categoryテーブル用DTO
* @author　N.Hirai
* @version　1.0.0
*/

public class VacationCategoryDto {
	private int id;
	private int code;
	private String display_name;
	private boolean is_paid;
	private float paid_date;
	
	// ID
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// 勤怠休暇区分コード
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	// 勤怠休暇区分名
	public String getDisplayName() {
		return display_name;
	}

	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}

	// 有給区分
	public boolean getIsPaid() {
		return is_paid;
	}

	public void setIsPaid(boolean is_paid) {
		this.is_paid = is_paid;
	}

	// 有有給取得日
	public float getPaidDate() {
		return paid_date;
	}

	public void setPaidDate(float paid_date) {
		this.paid_date = paid_date;
	}
}
