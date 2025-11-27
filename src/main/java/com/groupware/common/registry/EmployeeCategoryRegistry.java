package com.groupware.common.registry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.groupware.common.model.EmployeeCategory;
import com.groupware.dto.EmployeeCategoryDto;

/**
* EmployeeCategoryRegistry
* 社員区分レジストリ
* @author　N.Hirai
* @version　1.0.0
*/
public class EmployeeCategoryRegistry {
	private static final Map<Integer, EmployeeCategory> CODE_MAP = new HashMap<>();
	private static final Map<String, EmployeeCategory> NAME_MAP = new HashMap<>();
	
	// インスタンス化を禁止
	private EmployeeCategoryRegistry() {
	}

	/**
	 * データベースからの値を使ってレジストリを初期化する
	 * アプリケーション起動時に一度だけ実行する
	 */
	public static void initialize(List<EmployeeCategoryDto> dbRecords) {
		for (EmployeeCategoryDto record : dbRecords) {
			EmployeeCategory dept = new EmployeeCategory(
					record.getCode(),
					record.getDisplayName());

			CODE_MAP.put(dept.getCode(), dept);
			NAME_MAP.put(dept.getDisplayName(), dept);
		}
	}

	/**
	 * DBから取得したコード値から EmployeeCategory を取得
	 */
	public static EmployeeCategory fromCode(int code) {
		return CODE_MAP.get(code);
	}
	
	/**
	 * 社員区分データMAP作成
	 * 
	 * @return Map<Integer, String>
	 */
	public static Map<Integer, String> employeeCategorySelectSet() {
		Map<Integer, String> employeeCategoryMap = new LinkedHashMap<>(); 
		for (EmployeeCategory dept : CODE_MAP.values()) {
			employeeCategoryMap.put(dept.getCode(), dept.getDisplayName());
        }
		return employeeCategoryMap;
	}
}
