package com.groupware.dto;

/**
* DepartmentTypeDto
* department_typeテーブル用DTO
* @author　N.Hirai
* @version　1.0.0
*/

public class DepartmentTypeDto {
	private int id;
	private int code;
	private String display_name;
	private boolean is_admin;

	// ID
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// 部署コード
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	// 部署名
	public String getDisplayName() {
		return display_name;
	}

	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}
	
	// 管理者権限
	public boolean getIsAdmin() {
		return is_admin;
	}

	public void setIsAdmin(boolean is_admin) {
		this.is_admin = is_admin;
	}
}
