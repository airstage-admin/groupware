package com.groupware.attendance.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.groupware.attendance.form.AttendanceForm;
import com.groupware.common.constant.CommonConstants;
import com.groupware.common.util.CommonUtils;

/**
 * NightBreakValidator
 * 深夜休憩時間入力チェック実装クラス
 * 
 * @author　A.Watanabe
 * @version　1.0.0
 */

public class NightBreakValidator implements ConstraintValidator<NightBreakCheck, AttendanceForm> {
	
	/**
	 * バリデーション実行処理
	 * 
	 * @param form チェック対象のフォーム（出勤・退勤・深夜休憩時間を含む）
	 * @param context バリデーションコンテキスト
	 * @return true:検証OK（または対象外）、false:検証NG
	 */
	@Override
	public boolean isValid(AttendanceForm form, ConstraintValidatorContext context) {
		String clockIn = form.getClockIn();	//出勤時刻
		String clockOut = form.getClockOut(); // 退勤時刻
		String nightBreak = form.getNightBreakTime(); // 深夜休憩

		//退勤時刻が空ならチェックしない
		if (CommonUtils.isEmpty(clockOut)) {
			return true;
		}

		//--- 時間計算ロジック 分に変換 ---
		int clockInMin = CommonUtils.toMinutes(clockIn); //出勤
		int clockOutMin = CommonUtils.toMinutes(clockOut); //退勤 
		int nightStartMin = CommonConstants.NIGHT_START_MIN;  // 22:00 = 1320分
		int nightEndMin = CommonConstants.NIGHT_END_MIN;   // 29:00 = 1740分

		// 「退勤が22:00より後」 かつ 「出勤が29:00より前」 の場合のみ深夜勤務とする
		boolean isLateNightWork = (clockOutMin > nightStartMin) && (clockInMin < nightEndMin);

		//深夜休憩が入力されているか判定 (0:00や空文字以外か)
		boolean hasNightBreakInput = CommonUtils.isNotEmpty(nightBreak) && !nightBreak.equals("0:00");

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

	/**
	 * エラーメッセージを設定する
	 *
	 * @param context バリデーションコンテキスト
	 * @param message 表示するエラーメッセージ
	 */
	private void setErrorMessage(ConstraintValidatorContext context, String message) {
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(message)
		.addPropertyNode("nightBreakTime") 
		.addConstraintViolation();
	}

}
