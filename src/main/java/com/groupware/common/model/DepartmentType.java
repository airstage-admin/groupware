package com.groupware.common.model;

/**
* DepartmentType
* 部署カテゴリデータモデル
* @author　N.Hirai
* @version　1.0.0
*/
public class DepartmentType {
	private final int code;
	private final String displayName;
	private final boolean admin;
	
	public DepartmentType(int code, String displayName, boolean admin) {
		this.code = code;
		this.displayName = displayName;
		this.admin = admin;
	}

	// ゲッターメソッド
	public int getCode() {
		return code;
	}

	public String getDisplayName() {
		return displayName;
	}

	public boolean getAdmin() {
		return admin;
	}
}
