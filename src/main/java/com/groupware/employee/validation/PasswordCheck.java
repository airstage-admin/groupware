package com.groupware.employee.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
* PasswordCheck
* パスワード一致チェック用アノテーション
* 新しいパスワードと確認用パスワードの値が一致しているかを検証する
* 
* @author　A.Watanabe
* @version　1.0.0
*/

@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.TYPE }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordCheck { 

    // エラーメッセージ
    String message() default "新しいパスワードと確認用パスワードが一致しません。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    // 比較するフィールド名（デフォルト設定）
    String field1() default "newPassword";
    String field2() default "newPasswordConfirm";
}