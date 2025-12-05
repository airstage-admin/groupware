package com.groupware.attendance.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.groupware.attendance.form.AttendanceForm;
import com.groupware.common.util.CommonUtils;

/**
* BreakTimeValidator
* 休憩時間超過チェック実装クラス
* 
* @author　A.Watanabe
* @version　1.0.0
*/

public class BreakTimeValidator implements ConstraintValidator<BreakTimeCheck, AttendanceForm> {
	
	@Override
    public boolean isValid(AttendanceForm form, ConstraintValidatorContext context) {
        String clockIn = form.getClockIn();
        String clockOut = form.getClockOut();
        String breakTime = form.getBreakTime();
        String nightBreak = form.getNightBreakTime();

        //必要な値が空ならチェックしない
        if (CommonUtils.isEmpty(clockIn) || CommonUtils.isEmpty(clockOut)) {
            return true;
        }

        //分に変換して計算
        int inMin = CommonUtils.toMinutes(clockIn);
        int outMin = CommonUtils.toMinutes(clockOut);
        int breakMin = CommonUtils.toMinutes(breakTime);
        int nightBreakMin = CommonUtils.toMinutes(nightBreak);

        //勤務時間（退勤 - 出勤）
        int workDuration = outMin - inMin;
        
        //休憩合計
        int totalBreak = breakMin + nightBreakMin;

        if (workDuration <= 0) {
            return true;
        }

        //判定：休憩合計が勤務時間を超えていたらエラー
        if (totalBreak >= workDuration) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                   .addPropertyNode("breakTime") // 休憩時間の欄に赤文字を出す
                   .addConstraintViolation();
            return false;
        }

        return true;
    }

}
