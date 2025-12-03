package com.groupware.employee.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.groupware.common.constant.CommonConstants;
import com.groupware.employee.validation.LoginIdCheck;

@LoginIdCheck
public class EmployeeForm {
	private int id;
	@NotBlank(message = "新しいログインIDを入力してください。")
	@Size(min = CommonConstants.LOGIN_ID_MIN_LENGTH, 
	       max = CommonConstants.LOGIN_ID_MAX_LENGTH, 
	       message = "ログインIDは{min}文字以上{max}文字以下で入力してください。")
	@Pattern(regexp = CommonConstants.ID_PASS_REGEX, message = "ログインIDは半角英数字と記号（._-!@）のみで入力してください。")
	private String loginid;

	@NotBlank(message = "新しいパスワードを入力してください。")
    @Size(min = CommonConstants.PASSWORD_MIN_LENGTH, 
          max = CommonConstants.PASSWORD_MAX_LENGTH, 
          message = "パスワードは{min}文字以上{max}文字以下で入力してください。")
	@Pattern(regexp = CommonConstants.ID_PASS_REGEX, message = "パスワードは半角英数字と記号（._-!@）のみで入力してください。")
	private String password;

	@Size(max = CommonConstants.NAME_MAX_LENGTH, 
    	  message = "氏名は{max}文字以下で入力してください。")
	
	@Pattern(regexp = CommonConstants.NAME_REGEX, message = "氏名は全角かな・全角英字で入力してください。")
	private String username;

	@Pattern(regexp = CommonConstants.EMPLOYEE_NUMBER, message = "社員番号は半角数字3桁で入力してください。")
	private String employeeNo;

	@Pattern(regexp = CommonConstants.MAIL_REGEX, message = "メールアドレスの形式が正しくありません。メールアドレス形式で、半角英数字、および記号（._%+-）のみを使用して入力してください。")
	@Size(max = 254, message = "メールアドレスは254文字以下で入力してください。")
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
