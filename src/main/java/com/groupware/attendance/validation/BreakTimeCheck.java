package com.groupware.attendance.validation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
* BreakTimeCheck
* 休憩時間超過チェック用アノテーション
* 
* @author　A.Watanabe
* @version　1.0.0
*/

@Constraint(validatedBy = BreakTimeValidator.class) 
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)

public @interface BreakTimeCheck  {
	
    String message() default "休憩時刻合計が勤務時間をオーバーしています。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
