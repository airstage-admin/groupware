package com.groupware.attendance.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
* NightBreakCheck
* 深夜休憩時間入力チェック用アノテーション
* 退勤時刻と深夜休憩時間の整合性を検証する
* 
* @author　A.Watanabe
* @version　1.0.0
*/

@Constraint(validatedBy = NightBreakValidator.class) // 次に作るクラスを指定
@Target({ ElementType.TYPE }) // クラス全体にかけるので TYPE にする
@Retention(RetentionPolicy.RUNTIME)

public  @interface NightBreakCheck {
	String message() default "深夜勤務に関する入力に誤りがあります。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
