package com.groupware.common.constant;

/**
 * OutputCategory
 * 出力項目カテゴリ
 * @author　S.dasgo
 * @version　1.0.0
 */
public enum OutputCategory {
	ATTENDANCE("attendance", "勤怠管理"),
	PAID_LEAVE("paid_leave", "有給管理簿"),
	INTERNAL_PJ("internal_pj", "社内PJ"),
	STUDY("study", "勉強会");

	private final String code;
	private final String label;

	OutputCategory(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

	public static String ofCode(String code) {
		for (OutputCategory category : values()) {
			if (category.code.equals(code)) {
				return category.label;
			}
		}
		return "";
	}
}