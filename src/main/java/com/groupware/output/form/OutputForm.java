package com.groupware.output.form;

/**
 * OutputController
 * @author　S.dasgo
 * @version　1.0.0
 */

public class OutputForm {
    private String outputItem;      // 出力項目
    private String targetYearMonth; // 出力対象年月 (YYYY-MM)
    private boolean checkOk;        // 勤怠確認OKフラグ (true: 確認済み)

    // Getter
    public String getOutputItem() { return outputItem; }
    public String getTargetYearMonth() { return targetYearMonth; }
    public boolean isCheckOk() { return checkOk; }

    // Setter
    public void setOutputItem(String outputItem) { this.outputItem = outputItem; }
    public void setTargetYearMonth(String targetYearMonth) { this.targetYearMonth = targetYearMonth; }
    public void setCheckOk(boolean checkOk) { this.checkOk = checkOk; }
}