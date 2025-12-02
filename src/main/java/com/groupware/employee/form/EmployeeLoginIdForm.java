package com.groupware.employee.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.groupware.common.constant.CommonConstants;


/**
* EmployeeLoginIdForm
* ログインID変更専用のフォームクラス
* 
* @author　A.Watanabe
* @version　1.0.0
*/
public class EmployeeLoginIdForm {
	
	 @NotBlank(message = "新しいログインIDを入力してください")
	 @Size(min = CommonConstants.LOGIN_ID_MIN_LENGTH, 
	       max = CommonConstants.LOGIN_ID_MAX_LENGTH, 
	       message = "ログインIDは{min}文字以上{max}文字以内で入力してください")
	 
	 @Pattern(regexp = CommonConstants.ID_PASS_REGEX, 
	          message = "ログインIDは半角英数字と記号（._-!@）のみで入力してください")
	 private String newLoginId;

    // ==========================================================
 	//                        Getter/Setter
 	// ==========================================================
    // newloginid の GetterSetter
    public String getNewLoginId() {
        return newLoginId;
    }

    public void setNewLoginId(String newLoginId) {
        this.newLoginId = newLoginId;
    }
}