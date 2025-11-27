package com.groupware.dto;

/**
* MonthlyCycleDto
* monthly_cycleテーブル用DTO
* @author　N.Hirai
* @version　1.0.0
*/

public class MonthlyCycleDto {
	private int id;
	private String start_date;
	private String end_date;
	private String target_date;

	// ID
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// 開始月日
	public String getStartDate() {
		return start_date;
	}

	public void setStartDate(String start_date) {
		this.start_date = start_date;
	}

	// 終了月日
	public String getEndDate() {
		return end_date;
	}

	public void setEndDate(String end_date) {
		this.end_date = end_date;
	}

	// 付与月日
	public String getTargetDate() {
		return target_date;
	}

	public void setTargetDate(String target_date) {
		this.target_date = target_date;
	}
}
