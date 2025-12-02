package com.groupware.employee.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
* EmployeeLoginIdForm
* ログインID変更専用のフォームクラス
* 
* @author　A.Watanabe
* @version　1.0.0
*/
public class EmployeeLoginIdForm {
	
    @NotBlank(message = "新しいログインIDを入力してください")
    @Size(min = 4, max = 20, message = "ログインIDは4文字以上20文字以内で入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "ログインIDは半角英数字と記号（._-）のみで入力してください")
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