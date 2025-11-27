package com.groupware.common.registry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.groupware.common.model.PlaceCategory;
import com.groupware.dto.PlaceCategoryDto;

/**
* PlaceCategoryRegistry
* 勤務先カテゴリレジストリ
* @author　N.Hirai
* @version　1.0.0
*/
public class PlaceCategoryRegistry {
	private static final Map<Integer, PlaceCategory> CODE_MAP = new HashMap<>();
	private static final Map<String, PlaceCategory> NAME_MAP = new HashMap<>();
	private static final Map<Boolean, PlaceCategory> INPUT_MAP = new HashMap<>();
	
	// インスタンス化を禁止
	private PlaceCategoryRegistry() {
	}

	/**
	 * データベースからの値を使ってレジストリを初期化する
	 * アプリケーション起動時に一度だけ実行する
	 */
	public static void initialize(List<PlaceCategoryDto> dbRecords) {
		for (PlaceCategoryDto record : dbRecords) {
			PlaceCategory dept = new PlaceCategory(
					record.getCode(),
					record.getDisplayName(),
					record.getIsName());

			CODE_MAP.put(dept.getCode(), dept);
			NAME_MAP.put(dept.getDisplayName(), dept);
			INPUT_MAP.put(dept.getIsName(), dept);
		}
	}

	/**
	 * DBから取得したコード値から PlaceCategory を取得
	 */
	public static PlaceCategory fromCode(int code) {
		return CODE_MAP.get(code);
	}
	
	/**
	 * 勤務先区分データMAP作成
	 * 
	 * @return Map<Integer, String>
	 */
	public static Map<Integer, String> placeCategorySelectSet() {
		Map<Integer, String> placeCategoryMap = new LinkedHashMap<>(); 
		
		for (PlaceCategory dept : CODE_MAP.values()) {
			placeCategoryMap.put(dept.getCode(), dept.getDisplayName());
        }
		return placeCategoryMap;
	}
}
