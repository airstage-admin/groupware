package com.groupware.dto;

/**
* EmployeeCategoryDto
* employee_categoryテーブル用DTO
* @author　N.Hirai
* @version　1.0.0
*/

public class EmployeeCategoryDto {
	private int id;
	private int code;
	private String display_name;

	// ID
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// 社員区分コード
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	// 社員区分名
	public String getDisplayName() {
		return display_name;
	}

	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}
}
