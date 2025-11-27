package com.groupware.dto;

/**
* NumberPaidDaysDto
* number_paid_daysテーブル用DTO
* @author　N.Hirai
* @version　1.0.0
*/
public class NumberPaidDaysDto {
	private int id;
	private int employee_code;
	private int months_passed;
	private int paid_leave_granted;
	
	// ID
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// 雇用区分コード
	public int getEmployeeCode() {
		return employee_code;
	}

	public void setEmployeeCode(int employee_code) {
		this.employee_code = employee_code;
	}

	// 経過月数
	public int getMonthsPassed() {
		return months_passed;
	}

	public void setMonthsPassed(int months_passed) {
		this.months_passed = months_passed;
	}

	// 有給付与日数
	public int getPaidLeaveGranted() {
		return paid_leave_granted;
	}

	public void setPaidLeaveGranted(int paid_leave_granted) {
		this.paid_leave_granted = paid_leave_granted;
	}
}
