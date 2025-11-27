package com.groupware.common.constant;

import java.util.Arrays;
import java.util.Optional;

/**
* WeekCategory
* 曜日カテゴリ
* @author　N.Hirai
* @version　1.0.0
*/
public enum WeekCategory {
	SUNDAY(1, "日"), MONDAY(2, "月"), TUESDAY(3, "火"), WEDNESDAY(4, "水"), THURSDAY(5, "木"), FRIDAY(6, "金"), SATURDAY(7,	"土");

	private final int code;
	private final String japaneseName;
	public static final String SUN = "日";
	
	WeekCategory(int code, String japaneseName) {
		this.code = code;
		this.japaneseName = japaneseName;
	}

	// --- ゲッターメソッド（必須） ---

	/**
	 * Enum定数から対応するコードを検索する。
	 * @return コード
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Enum定数から対応する日本語名を検索する。
	 * @return 日本語名
	 */
	public String getJapaneseName() {
		return japaneseName;
	}

	// --- ユーティリティメソッド（日本語名検索） ---

	/**
	 * 日本語名から対応するEnum定数を検索する。
	 * @param japaneseName 曜日の日本語名 (例: "月")
	 * @return 対応するWeekCategory (見つからない場合はOptional.empty())
	 */
	public static Optional<WeekCategory> ofJapaneseName(String japaneseName) {
		return Arrays.stream(values())
				// japaneseNameが一致するものをフィルター
				.filter(day -> day.japaneseName.equals(japaneseName))
				// 最初に見つかったものを返す
				.findFirst();
	}

	// --- ユーティリティメソッド（コード取得） ---

	/**
	 * 日本語名から対応するコードを取得する。
	 * ofJapaneseNameとgetCode()を組み合わせた便利なメソッド。
	 * @param japaneseName 曜日の日本語名 (例: "火")
	 * @return 対応するコードを保持するOptional<Integer> (見つからない場合はOptional.empty())
	 */
	public static Optional<Integer> getCodeByJapaneseName(String japaneseName) {
		return ofJapaneseName(japaneseName) // Optional<WeekCategory> を取得
				.map(WeekCategory::getCode); // 存在すれば code (int) に変換し、Optional<Integer> を返す
	}

	public static String ofCode(int code) {
		for (WeekCategory category : values()) {
			if (category.code == code) {
				return category.japaneseName;
			}
		}
		return SUN;
	}
}