package com.groupware.output.service;

import java.time.YearMonth;
import java.util.Map;

public interface OutputService {
    /** プルダウン用の年月リスト取得 */
    Map<String, String> getYearMonthList();

    /**
     * 勤怠チェックを行い、エラーがあればExcelバイナリを返す。なければnull。
     * @param　ym 対象年月
     * @return　Excelファイルのbyte配列 または null
     */
    byte[] validateAttendance(YearMonth ym);

    /**
     * 指定された帳票のExcelバイナリを返す。
     * @param　item 出力項目キー
     * @param　ym 対象年月
     * @return　Excelファイルのbyte配列
     */
    byte[] generateReport(String item, YearMonth ym);
}