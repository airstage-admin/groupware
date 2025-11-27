package com.groupware.common.registry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.groupware.common.model.VacationCategory;
import com.groupware.dto.VacationCategoryDto;

/**
* VacationCategoryRegistry
* 勤怠休暇区分レジストリ
* @author　N.Hirai
* @version　1.0.0
*/
public class VacationCategoryRegistry {
	private static final Map<Integer, VacationCategory> CODE_MAP = new HashMap<>();
	private static final Map<String, VacationCategory> NAME_MAP = new HashMap<>();
	private static final Map<Boolean, VacationCategory> PAID_MAP = new HashMap<>();
	private static final Map<Float, VacationCategory> PAID_DATE_MAP = new HashMap<>();
	
	// インスタンス化を禁止
	private VacationCategoryRegistry() {
	}

	/**
	 * データベースからの値を使ってレジストリを初期化する
	 * アプリケーション起動時に一度だけ実行する
	 */
	public static void initialize(List<VacationCategoryDto> dbRecords) {
		for (VacationCategoryDto record : dbRecords) {
			VacationCategory dept = new VacationCategory(
					record.getCode(),
					record.getDisplayName(),
					record.getIsPaid(),
					record.getPaidDate());

			CODE_MAP.put(dept.getCode(), dept);
			NAME_MAP.put(dept.getDisplayName(), dept);
			PAID_MAP.put(dept.getIsPaid(), dept);
			PAID_DATE_MAP.put(dept.getPaidDate(), dept);
		}
	}

	/**
	 * DBから取得したコード値から VacationCategory を取得
	 */
	public static VacationCategory fromCode(int code) {
		return CODE_MAP.get(code);
	}
	
	/**
	 * 勤怠休暇区分データMAP作成
	 * 
	 * @return Map<Integer, String>
	 */
	public static Map<Integer, String> vacationCategorySelectSet() {
		Map<Integer, String> vacationCategoryMap = new LinkedHashMap<>(); 
		for (VacationCategory dept : CODE_MAP.values()) {
			vacationCategoryMap.put(dept.getCode(), dept.getDisplayName());
        }
		return vacationCategoryMap;
	}
}
