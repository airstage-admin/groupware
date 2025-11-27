package com.groupware.dto;

/**
* UserApprovalDto
* users、attendance_approvalテーブルJOIN用DTO
* @author　N.Hirai
* @version　1.0.0
*/

public class UserApprovalDto {
	private int id;
	private String user_name;
	private String employee_no;
	private int department;
	private String approval;
	private String approval_name;

	// ID
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// ユーザー名称
	public String getUsername() {
		return user_name;
	}

	public void setUsername(String user_name) {
		this.user_name = user_name;
	}

	// 社員番号
	public String getEmployeeNo() {
		return employee_no;
	}

	public void setEmployeeNo(String employee_no) {
		this.employee_no = employee_no;
	}

	// 部門区分
	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	// 承認区分
	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
	}

	// 承認者名
	public String getApprovalName() {
		return approval_name;
	}

	public void setApprovalName(String approval_name) {
		this.approval_name = approval_name;
	}
}
