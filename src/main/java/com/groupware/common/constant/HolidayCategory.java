package com.groupware.common.constant;

/**
* HolidayCategory
* 休日区分カテゴリ
* @author　N.Hirai
* @version　1.0.0
*/
public enum HolidayCategory {
	NONE(0, ""), HOLIDAY(1, "祝"), PUBLIC(2, "公");

	private final int code;
	private final String label;

	HolidayCategory(int code, String label) {
		this.code = code;
		this.label = label;
	}

	public int getHoliday() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public static String ofCode(int code) {
		for (HolidayCategory category : values()) {
			if (category.code == code) {
				return category.label;
			}
		}
		return "";
	}
}
