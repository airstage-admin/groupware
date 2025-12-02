package com.groupware.employee.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.groupware.common.constant.CommonConstants;
import com.groupware.employee.validation.PasswordCheck; 

/**
* EmployeePasswordForm
* パスワード変更専用のフォームクラス
* 
* @author　A.Watanabe
* @version　1.0.0
*/
@PasswordCheck(
	    field1 = "newPassword", 
	    field2 = "newPasswordConfirm", 
	    message = "新しいパスワードと確認用パスワードが一致しません"
)

public class EmployeePasswordForm {

	 // 現在のパスワード
    @NotBlank(message = "現在のパスワードを入力してください")
    private String currentPassword;

    // 新しいパスワード
    @NotBlank(message = "新しいパスワードを入力してください")
    @Size(min = CommonConstants.PASSWORD_MIN_LENGTH, 
          max = CommonConstants.PASSWORD_MAX_LENGTH, 
          message = "パスワードは{min}文字以上{max}文字以下で入力してください")
    @Pattern(regexp = CommonConstants.ID_PASS_REGEX, 
          message = "パスワードは半角英数字と記号（._-!@）のみで入力してください")    
    private String newPassword;

    // 新しいパスワード（確認用）
    @NotBlank(message = "確認用パスワードを入力してください")
    private String newPasswordConfirm;


    // ==========================================================
 	//                        Getter/Setter
 	// ==========================================================
    // currentpassword の GetterSetter
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    
    // getnewpassword の GetterSetter
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }
}