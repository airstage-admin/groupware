package com.groupware.attendance.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.groupware.attendance.form.AttendanceForm;

/**
* NightBreakValidator
* 深夜休憩時間入力チェック実装クラス
* 
* @author　A.Watanabe
* @version　1.0.0
*/

public class NightBreakValidator implements ConstraintValidator<NightBreakCheck, AttendanceForm> {
	@Override
	public boolean isValid(AttendanceForm form, ConstraintValidatorContext context) {
		String clockIn = form.getClockIn();	//出勤時刻
		String clockOut = form.getClockOut(); // 退勤時刻
		String nightBreak = form.getNightBreakTime(); // 深夜休憩

		//退勤時刻が空ならチェックしない
		if (clockOut == null || clockOut.isEmpty()) {
			return true;
		}

		//--- 時間計算ロジック 分に変換 ---
		int clockInMin = toMinutes(clockIn); //出勤
		int clockOutMin = toMinutes(clockOut); //退勤 
		int nightStartMin = 22 * 60; // 22:00 = 1320分
		int nightEndMin = 29 * 60;   // 29:00 = 1740分

		// 「退勤が22:00より後」 かつ 「出勤が29:00より前」 の場合のみ深夜勤務とする
		boolean isLateNightWork = (clockOutMin > nightStartMin) && (clockInMin < nightEndMin);

		//深夜休憩が入力されているか判定 (0:00や空文字以外か)
		boolean hasNightBreakInput = (nightBreak != null && !nightBreak.isEmpty() && !nightBreak.equals("0:00"));

		// --- チェック処理 ---

		//深夜勤務なのに、休憩が入っていない
		if (isLateNightWork && !hasNightBreakInput) {
			setErrorMessage(context, "このフィールドを入力してください。");
			return false;
		}

		//深夜勤務じゃないのに、休憩が入っている
		if (!isLateNightWork && hasNightBreakInput) {
			setErrorMessage(context, "深夜休憩時間は、深夜（22:00～29:00）に勤務した場合のみ記入してください。");
			return false;
		}

		return true;
	}

	// エラーメッセージを特定のフィールドに出すための処理
	private void setErrorMessage(ConstraintValidatorContext context, String message) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(message)
		.addPropertyNode("nightBreakTime") 
		.addConstraintViolation();
	}

	//"HH:mm" を "分" に変換するメソッド
	private int toMinutes(String time) {
		try {
			String[] parts = time.split(":");
			int h = Integer.parseInt(parts[0]);
			int m = Integer.parseInt(parts[1]);
			return h * 60 + m;
		} catch (Exception e) {
			return 0;
		}
	}

}
