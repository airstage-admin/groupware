package com.groupware.employee.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.groupware.employee.form.EmployeeForm;
import com.groupware.employee.service.EmployeeService;

public class LoginIdValidator implements ConstraintValidator<LoginIdCheck, EmployeeForm> {
	@Autowired
	private EmployeeService userService;

	@Override
	public boolean isValid(EmployeeForm form, ConstraintValidatorContext context) {
		// 既存データに登録するログインIDがあるかチェックする（自分自身は除く）
		boolean isValid = userService.existsByLoginid(form.getId(), form.getLoginid());

		if (isValid) {
			// エラーをクラス全体ではなく、loginid フィールドに結びつける
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("loginid") // エラーメッセージを表示したいフィールド
					.addConstraintViolation();
		}

		return !isValid;
	}
}
