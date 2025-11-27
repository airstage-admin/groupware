package com.groupware.dto;

/**
* PlaceCategoryDto
* place_categoryテーブル用DTO
* @author　N.Hirai
* @version　1.0.0
*/

public class PlaceCategoryDto {
	private int id;
	private int code;
	private String display_name;
	private boolean is_name;

	// ID
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// 勤務先コード
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	// 勤務先名
	public String getDisplayName() {
		return display_name;
	}

	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}
	
	// 勤務先名入力区分
	public boolean getIsName() {
		return is_name;
	}

	public void setIsName(boolean is_name) {
		this.is_name = is_name;
	}
}
