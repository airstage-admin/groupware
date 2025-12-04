package com.groupware.attendance.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.groupware.attendance.form.AttendanceForm;
import com.groupware.common.constant.CommonConstants;

/**
* PlaceWorkNameValidator
* 勤務場所名入力チェック実装クラス
* 
* @author　A.Watanabe
* @version　1.0.0
*/

public class PlaceWorkNameValidator implements ConstraintValidator<PlaceWorkNameCheck, AttendanceForm> {

	@Override
	public boolean isValid(AttendanceForm form, ConstraintValidatorContext context) {
		// フォームから値を取得
		String placeWorkCode = form.getPlaceWork(); // 勤務先区分
		String placeName = form.getPlaceWorkName(); // 勤務場所名

		// 勤務先区分が空ならチェックしない
		if (placeWorkCode == null || placeWorkCode.isEmpty()) {
			return true;
		}

		//判定ロジック	
		boolean isResidentWork = CommonConstants.PLACE_CODE_RESIDENT.equals(placeWorkCode);

		boolean isNameEmpty = (placeName == null || placeName.trim().isEmpty());

		//「常駐案件」かつ「場所名が空」ならエラー
		if (isResidentWork && isNameEmpty) {
			// エラーメッセージを placeWorkName フィールドに表示させる設定
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
			.addPropertyNode("placeWorkName") 
			.addConstraintViolation();

			return false;
		}

		return true;
	}
}