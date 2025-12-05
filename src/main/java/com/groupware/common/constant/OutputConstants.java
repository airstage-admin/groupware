package com.groupware.common.constant;

import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * OutputConstants
 * Excel出力機能に関する定数定義（スタイル、ヘッダー、メッセージ等）
 */
public class OutputConstants {

    private OutputConstants() {}

    // --- ファイル拡張子 ---
    public static final String EXTENSION_XLSX = ".xlsx";

    // --- ヘッダー項目（社内PJ用） ---
    // ※これはOutputCategoryには書けない情報なのでここに置くのが正解
    public static final String[] HEADER_INTERNAL_PJ = {"氏名", "社内PJ対応時間", "残業手当", "金額", "繰り上げ"};

    // --- スタイル設定 ---
    public static final short FONT_SIZE_DEFAULT = 10;
    public static final short COLOR_TEXT_BLACK = IndexedColors.BLACK.getIndex();
    public static final short COLOR_BG_HEADER = IndexedColors.SEA_GREEN.getIndex(); 
    public static final short COLOR_BG_DATA = IndexedColors.LIGHT_YELLOW.getIndex(); 

    // --- メッセージ ---
    public static final String MSG_ERR_INVALID_ITEM = "エラー：無効な出力項目が指定されました。";
    public static final String MSG_SUCCESS_CREATE = "のExcelを正常に作成しました: ";
    public static final String MSG_FAIL_CREATE = "のExcel作成に失敗しました: ";
    
    // --- 初期値 ---
    public static final String VAL_ZERO = "0";
    public static final String VAL_EMPTY = "";
}