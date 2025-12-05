package com.groupware.common.util;


/**
* CommonUtiles
* 共通ユーティリティ
* アプリケーション全体で使う汎用的なメソッドをまとめる
* 
* @author　A.Watanabe
* @version　1.0.0
*/
public class CommonUtils {
	
    private CommonUtils() {}

    /**
     * 文字列が「空」であるかを判定する
     * 
     * @param str 判定対象の文字列
     * @return true: nullまたは空 / false: 値がある
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * 文字列に「有効な文字」が入っているかを判定する
     * 
     * @param str 判定対象
     * @return true: 値がある / false: nullまたは空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Boolean型が true かどうかを判定する（null安全）
     * 
     * @param bool 判定対象のBoolean
     * @return true: 値がtrue / false: 値がfalseまたはnull
     */
    public static boolean isTrue(Boolean bool) {
        return bool != null && bool;
    }
    
    /**
	 * "HH:mm" 形式の時間を "分" に変換する
	 * 
	 * @param time 時間文字列
	 * @return 分換算した数値 (変換できない場合は 0)
	 */
	public static int toMinutes(String time) {
		
		if (isEmpty(time)) return 0;
		try {
			String[] parts = time.split(":");
			int h = Integer.parseInt(parts[0]);
			int m = Integer.parseInt(parts[1]);
			return h * 60 + m;
		} catch (Exception e) {
			return 0;
		}
	}
}


