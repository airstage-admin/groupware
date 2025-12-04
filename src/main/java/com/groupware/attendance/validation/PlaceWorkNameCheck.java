package com.groupware.attendance.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
* PlaceWorkNameCheck
* 勤務場所名入力チェック用アノテーション
* 
* @author　A.Watanabe
* @version　1.0.0
*/

@Constraint(validatedBy = PlaceWorkNameValidator.class)
@Target({ ElementType.TYPE }) 
@Retention(RetentionPolicy.RUNTIME)
public @interface PlaceWorkNameCheck {

	String message() default "勤務場所を入力してください。";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}