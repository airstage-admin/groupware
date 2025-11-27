package com.groupware.employee.form;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.groupware.common.constant.CommonConstants;
import com.groupware.employee.validation.LoginIdCheck;

@LoginIdCheck
public class EmployeeForm {
	private int id;

	@Size(min = 4, max = 50, message = "ログインIDは4文字以上50文字以下で入力してください。")
	@Pattern(regexp = CommonConstants.ID_PASS_REGEX, message = "ログインIDは半角英数字と記号（._-!@）のみで入力してください。")
	private String loginid;

	@Size(min = 8, max = 100, message = "パスワードは8文字以上100文字以下で入力してください。")
	@Pattern(regexp = CommonConstants.ID_PASS_REGEX, message = "パスワードは半角英数字と記号（._-!@）のみで入力してください。")
	private String password;

	@Size(max = 128, message = "氏名は128文字以内で入力してください。")
	@Pattern(regexp = CommonConstants.NAME_REGEX, message = "氏名は全角で入力してください。")
	private String username;

	@Pattern(regexp = CommonConstants.EMPLOYEE_NUMBER, message = "社員番号は数字3桁で入力してください。")
	private String employeeNo;

	@Pattern(regexp = CommonConstants.MAIL_REGEX, message = "メールアドレスの形式が正しくありません。（例：example@airstage.co.jp)")
	@Size(max = 254, message = "メールアドレスは254文字以内で入力してください。")
	private String mail;

	@Pattern(regexp = CommonConstants.DATE_REGEX, message = "入社日は半角数字YYYY/MM/DD形式で入力してください。")
	private String hireDate;

	private String employeeType;
	private String departmentCategory;

	// ==========================================================
	//                        Getter/Setter
	// ==========================================================
	// id の GetterSetter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// loginid の GetterSetter
	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	// password の GetterSetter
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// username の GetterSetter
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	// employee_no の GetterSetter
	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	// mail の GetterSetter
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	// employeeType の GetterSetter
	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	// departmentCategory の GetterSetter
	public String getDepartmentCategory() {
		return departmentCategory;
	}

	public void setDepartmentCategory(String departmentCategory) {
		this.departmentCategory = departmentCategory;
	}

	// hireDate の GetterSetter
	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
}
