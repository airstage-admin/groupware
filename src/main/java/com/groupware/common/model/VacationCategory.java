package com.groupware.common.model;

/**
* VacationCategory
* 勤怠休暇区分データモデル
* @author　N.Hirai
* @version　1.0.0
*/
public class VacationCategory {
	private final int code;
	private final String displayName;
	private boolean is_paid;
	private float paid_date;
	
	public VacationCategory(int code, String displayName, boolean is_paid, float paid_date) {
		this.code = code;
		this.displayName = displayName;
		this.is_paid = is_paid;
		this.paid_date = paid_date;
	}

	// ゲッターメソッド
	public int getCode() {
		return code;
	}

	public String getDisplayName() {
		return displayName;
	}
	
	public boolean getIsPaid() {
		return is_paid;
	}
	
	public float getPaidDate() {
		return paid_date;
	}
}
