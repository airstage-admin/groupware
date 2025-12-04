package com.groupware.dto;

/**
* SubjectDto
* subjectsテーブル用DTO（勘定科目マスタ）
* @author
* @version　1.0.0
*/
public class SubjectDto {
	private long id;
	private String name;
	private String description;

	// ID
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	// 勘定科目名
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 説明
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

