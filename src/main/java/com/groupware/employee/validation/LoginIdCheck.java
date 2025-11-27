package com.groupware.employee.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = LoginIdValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginIdCheck {
	String message() default "そのログインIDはすでに使用されています。";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
