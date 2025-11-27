package com.groupware.common.model;

/**
* PlaceCategory
* 勤務先データモデル
* @author　N.Hirai
* @version　1.0.0
*/
public class PlaceCategory {
	private final int code;
	private final String displayName;
	private final boolean is_name;
	
	public PlaceCategory(int code, String displayName, boolean is_name) {
		this.code = code;
		this.displayName = displayName;
		this.is_name = is_name;
	}

	// ゲッターメソッド
	public int getCode() {
		return code;
	}

	public String getDisplayName() {
		return displayName;
	}

	public boolean getIsName() {
		return is_name;
	}
}
