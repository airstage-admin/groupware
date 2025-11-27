package com.groupware.attendance.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = ClockOrderValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ClockOrderCheck {
	String message() default "退勤時刻は出勤時刻より後に設定してください。";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
