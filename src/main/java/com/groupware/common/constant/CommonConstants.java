package com.groupware.common.constant;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
* CommonConstants
* 共通定数クラス
* @author　N.Hirai
* @version　1.0.0
*/
public final class CommonConstants {
	// インスタンス化を禁止
	private CommonConstants() {
		throw new AssertionError("Constant class should not be instantiated.");
	}

	// 公休日定義用
	public static Set<MonthDay> TARGET_DAYS = Set.of();

	// 有給付与日算用
	public static final int ONE_YEAR = 1;
	public static final int SIX_MONTH = 6;
	public static final String CYCLE_YEAR = "2000";
	
	// 正規表現
	public static final String CLOCK_REGEX = "^([0-2]?[0-9]|3[0-6]):[0-5][0-9]$"; // 勤怠時間正規表現（0:00～36:00)
	public static final String BREAK_REGEX = "^(([0-1]?[0-9]):(00|15|30|45))|10:00$"; // 休憩時間正規表現（0:00～9:45, 15分刻み)
	public static final String EMPLOYEE_NUMBER = "\\d{3}"; // 社員番号（半角数字3桁）
	public static final String MAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // メールアドレス正規表現
	public static final String ID_PASS_REGEX = "^[a-zA-Z0-9._\\-!@]+$"; // ログインID、パスワード（半角英数字と記号（._-!@））
	public static final String NAME_REGEX = "^[ぁ-んァ-ヶ一-龥Ａ-Ｚａ-ｚ]+$"; // 名前（全角・スペース禁止）
	public static final String DATE_REGEX = "^[0-9]{4}/(0?[1-9]|1[0-2])/(0?[1-9]|[12][0-9]|3[01])$"; // 入社日（YYYY/MM/DD）月日のゼロ埋め省略可（4/1等もOK）

	
	//文字数
	public static final int LOGIN_ID_MIN_LENGTH = 4; //ログインID最小文字数
    public static final int LOGIN_ID_MAX_LENGTH = 50; //ログインID最大文字数
    public static final int PASSWORD_MIN_LENGTH = 8; //パスワード最小文字数
    public static final int PASSWORD_MAX_LENGTH = 100; //パスワード最大文字数
    public static final int NAME_MAX_LENGTH = 128; //氏名最大文字数
    
	
	// SQL演算子
	public static final String JUDGE_EQUAL = "=";
	public static final String JUDGE_GT = ">";
	public static final String JUDGE_GE = "=>";
	public static final String JUDGE_LT = "<";
	public static final String JUDGE_LE = "<=";

	// 共通
	public static final String WEEK_NAME = "E"; // 略語 の曜日名
	public static final String KORON = "："; // 項目値後尾
	public static final String ST_HYPHEN = "-"; // 区切り文字
	public static final String PASSWORD = "PassW@rd999"; // アカウント登録パスワード初期値
	public static final String INIT_TIME = "0:00"; // 初期時間
	public static final String TIME_FORMAT = "H:mm"; // 時刻フォーマット
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // 日付フォーマット
	public static final DateTimeFormatter STRDATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 日付フォーマット
	public static final String SLASH_FORMAT = "/"; // 日付フォーマット変換用
	public static final String HYPHEN_FORMAT = "-"; // 日付フォーマット変換用
	
	public static final int UNSELECTED_CODE = 0; // 部署未選択コード
}
