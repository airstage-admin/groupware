package com.groupware.common.registry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.groupware.common.model.DepartmentType;
import com.groupware.dto.DepartmentTypeDto;

/**
* DepartmentRegistry
* 部署カテゴリレジストリ
* @author　N.Hirai
* @version　1.0.0
*/
public class DepartmentRegistry {
	private static final Map<Integer, DepartmentType> CODE_MAP = new HashMap<>();
	private static final Map<String, DepartmentType> NAME_MAP = new HashMap<>();
	private static final Map<Boolean, DepartmentType> ADMIN_MAP = new HashMap<>();
	
	// インスタンス化を禁止
	private DepartmentRegistry() {
	}

	/**
	 * データベースからの値を使ってレジストリを初期化する
	 * アプリケーション起動時に一度だけ実行する
	 */
	public static void initialize(List<DepartmentTypeDto> dbRecords) {
		for (DepartmentTypeDto record : dbRecords) {
			DepartmentType dept = new DepartmentType(
					record.getCode(),
					record.getDisplayName(),
					record.getIsAdmin());

			CODE_MAP.put(dept.getCode(), dept);
			NAME_MAP.put(dept.getDisplayName(), dept);
			ADMIN_MAP.put(dept.getAdmin(), dept);
		}
	}

	/**
	 * DBから取得したコード値から DepartmentType を取得
	 */
	public static DepartmentType fromCode(int code) {
		return CODE_MAP.get(code);
	}
	
	/**
	 * 部署区分データMAP作成
	 * 
	 * @param none false：部署選択無し、true：部署選択有り
	 * 
	 * @return Map<Integer, String>
	 */
	public static Map<Integer, String> departmentTypeSelectSet(boolean none) {
		Map<Integer, String> departmentMap = new LinkedHashMap<>(); 
		if (none) {
			departmentMap.put(0, "部署選択");
		}
		
		for (DepartmentType dept : CODE_MAP.values()) {
            departmentMap.put(dept.getCode(), dept.getDisplayName());
        }
		return departmentMap;
	}
}
