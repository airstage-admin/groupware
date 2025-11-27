package com.groupware.common.model;

/**
* EmployeeCategory
* 社員区分データモデル
* @author　N.Hirai
* @version　1.0.0
*/
public class EmployeeCategory {
	private final int code;
	private final String displayName;
	
	public EmployeeCategory(int code, String displayName) {
		this.code = code;
		this.displayName = displayName;
	}

	// ゲッターメソッド
	public int getCode() {
		return code;
	}

	public String getDisplayName() {
		return displayName;
	}
}
