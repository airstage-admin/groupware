package com.groupware.dto;

/**
* UserDto
* usersテーブル用DTO
* @author　N.Hirai
* @version　1.0.0
*/

public class UserDto {
	private int id;
	private String login_id;
	private String password;
	private String user_name;
	private String employee_no;
	private String mail;
	private int employee_type;
	private int department;
	private String hire_date;
	private String paid_grant_date;
	private int paid_leave_granted;
	private float paid_leave_remaining;
	private boolean delflg;
	private int created_by;
	private String created_at;
	private int updated_by;
	private String updated_at;

	// ユーザーID
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// ログインID
	public String getLoginid() {
		return login_id;
	}

	public void setLoginid(String login_id) {
		this.login_id = login_id;
	}

	// パスワード
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	// メールアドレス
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	// 社員区分
	public int getEmployeeType() {
		return employee_type;
	}

	public void setEmployeeType(int employee_type) {
		this.employee_type = employee_type;
	}

	// 部門区分
	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	// 入社日
	public String getHireDate() {
		return hire_date;
	}

	public void setHireDate(String hire_date) {
		this.hire_date = hire_date;
	}

	// 有給付与日
	public String getPaidGrantDate() {
		return paid_grant_date;
	}

	public void setPaidGrantDate(String paid_grant_date) {
		this.paid_grant_date = paid_grant_date;
	}

	// 有給付与日数
	public int getPaidLeaveGranted() {
		return paid_leave_granted;
	}

	public void setPaidLeaveGranted(int paid_leave_granted) {
		this.paid_leave_granted = paid_leave_granted;
	}

	// 有給残日数
	public float getPaidLeaveRemaining() {
		return paid_leave_remaining;
	}

	public void setPaidLeaveRemaining(float paid_leave_remaining) {
		this.paid_leave_remaining = paid_leave_remaining;
	}

	// 削除フラグ
	public boolean getDelflg() {
		return delflg;
	}

	public void setDelflg(boolean delflg) {
		this.delflg = delflg;
	}

	// 作成者ID
	public int getCreatedBy() {
		return created_by;
	}

	public void setCreatedBy(int created_by) {
		this.created_by = created_by;
	}

	// 作成日時
	public String getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(String created_at) {
		this.created_at = created_at;
	}

	// 更新者ID
	public int getUpdatedBy() {
		return updated_by;
	}

	public void setUpdatedBy(int updated_by) {
		this.updated_by = updated_by;
	}

	// 更新日時
	public String getUpdatedAt() {
		return updated_at;
	}

	public void setUpdatedAt(String updated_at) {
		this.updated_at = updated_at;
	}
}
