package com.groupware.attendance.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.groupware.attendance.form.AttendanceForm;
import com.groupware.common.model.PlaceCategory;
import com.groupware.common.registry.PlaceCategoryRegistry;
import com.groupware.common.util.CommonUtils;

/**
* PlaceWorkNameValidator
* 勤務場所名入力チェック実装クラス
* 
* @author　A.Watanabe
* @version　1.0.0
*/

public class PlaceWorkNameValidator implements ConstraintValidator<PlaceWorkNameCheck, AttendanceForm> {

	/**
	 * バリデーション実行処理
	 * 
	 * @param form チェック対象のフォーム
	 * @param context バリデーションコンテキスト
	 * @return true:検証OK（または対象外）、false:検証NG
	 */
	@Override
	public boolean isValid(AttendanceForm form, ConstraintValidatorContext context) {
		// フォームから値を取得
		String placeWorkCode = form.getPlaceWork(); // 勤務先区分
		String placeName = form.getPlaceWorkName(); // 勤務場所名

		// 勤務先区分が空ならチェックしない
		if (CommonUtils.isEmpty(placeWorkCode)) {
			return true;
		}

		try {
			//Stringのコードを int に変換
			int code = Integer.parseInt(placeWorkCode);

			//Registryから勤務先情報を取得
			PlaceCategory category = PlaceCategoryRegistry.fromCode(code);

			//場所名の入力が必要な勤務先か
		    boolean isNameRequired = (category != null && CommonUtils.isTrue(category.getIsName()));

		    //場所名が空か
		    boolean isNameEmpty =  CommonUtils.isEmpty(placeName);

		    // 入力が不要な設定なら、チェック不要なので OK
		    if (!isNameRequired) {
		    	return true;
		    }

		    // 名前がちゃんと入力されていれば、チェックOK
		    if (!isNameEmpty) {
		    	return true;
		    }

		    // エラーメッセージを設定して return false
		    context.disableDefaultConstraintViolation();
		    context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
		    .addPropertyNode("placeWorkName")
		    .addConstraintViolation();

		    return false;
		    
		} catch (NumberFormatException e) {
			return true;
		}
		
	}
}
