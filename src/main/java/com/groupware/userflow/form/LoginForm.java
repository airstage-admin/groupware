package com.groupware.userflow.form;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * ログインフォーム
 * 画面からの入力値を受け取り、バリデーション（チェック）を行うクラス
 */
public class LoginForm {

    //ログイン文字種、文字数チェック
    @Pattern(regexp = "^[a-zA-Z0-9._!@-]+$", message = "ログインIDは半角英数字と記号（._-!@）のみで入力してください")
    @Size(min = 4, max = 50, message = "ログインIDは4文字以上50文字以下で入力してください")
    private String loginid;

    //パスワード文字種、文字数チェック
    @Pattern(regexp = "^[a-zA-Z0-9._!@-]+$", message = "パスワードは半角英数字と記号（._-!@）のみで入力してください")
    @Size(min = 8, max = 100, message = "パスワードは8文字以上100文字以下で入力してください")
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