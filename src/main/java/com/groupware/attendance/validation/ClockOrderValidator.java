package com.groupware.attendance.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.groupware.attendance.form.AttendanceForm;

public class ClockOrderValidator implements ConstraintValidator<ClockOrderCheck, AttendanceForm> {

	// 時刻文字列を分単位の数値に変換するユーティリティ関数
	private long timeToMinutes(String timeStr) {
		if (timeStr == null || !timeStr.matches("^([0-2]?[0-9]|3[0-1]):[0-5][0-9]$")) {
			return -1; // 不正な時刻は比較をスキップ
		}
		String[] parts = timeStr.split(":");
		int hours = Integer.parseInt(parts[0]);
		int minutes = Integer.parseInt(parts[1]);
		return (long) hours * 60 + minutes;
	}

	@Override
	public boolean isValid(AttendanceForm form, ConstraintValidatorContext context) {
		// フィールドレベルのバリデーション (NotBlank, Pattern) が失敗した場合、ここではチェックをスキップする
		if (form.getClockIn() == null || form.getClockOut() == null) {
			return true;
		}

		long clockInMinutes = timeToMinutes(form.getClockIn());
		long clockOutMinutes = timeToMinutes(form.getClockOut());

		if (clockInMinutes == -1 || clockOutMinutes == -1) {
			// 時刻形式が不正な場合は、Patternバリデーションでエラーになっているはずなので、ここでは true を返す
			return true;
		}

		boolean isValid = clockInMinutes < clockOutMinutes;

		if (!isValid) {
			// エラーをクラス全体ではなく、clockOut フィールドに結びつける
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("clockOut") // エラーメッセージを表示したいフィールド
					.addConstraintViolation();
		}

		return isValid;
	}
}
