package com.groupware.userflow.form;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.groupware.common.constant.CommonConstants;

/**
 * ログインフォーム
 * 画面からの入力値を受け取り、バリデーション（チェック）を行うクラス
 */
public class LoginForm {

    //ログイン文字種、文字数チェック
    @Pattern(regexp = "^[a-zA-Z0-9._!@-]+$", message = "ログインIDは半角英数字と記号（._-!@）のみで入力してください")
    @Size(min = CommonConstants.LOGIN_ID_MIN_LENGTH, max = CommonConstants.LOGIN_ID_MAX_LENGTH, 
    message = "ログインIDは" + CommonConstants.LOGIN_ID_MIN_LENGTH + "文字以上" + CommonConstants.LOGIN_ID_MAX_LENGTH + "文字以下で入力してください")
    private String loginid;

    //パスワード文字種、文字数チェック
    @Pattern(regexp = "^[a-zA-Z0-9._!@-]+$", message = "パスワードは半角英数字と記号（._-!@）のみで入力してください")
    @Size(min = CommonConstants.PASSWORD_MIN_LENGTH, max = CommonConstants.PASSWORD_MAX_LENGTH, 
    	message = "パスワードは" + CommonConstants.PASSWORD_MIN_LENGTH + "文字以上" + CommonConstants.PASSWORD_MAX_LENGTH + "文字以下で入力してください")
    private String password;

    // ゲッターとセッター
    public String getLoginid() {
        return loginid;
    }
    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}