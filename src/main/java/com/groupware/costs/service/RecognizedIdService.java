package com.groupware.costs.service;

/**
 * RecognizedIdService
 * 識別用番号生成サービスインターフェース
 */
public interface RecognizedIdService {
    /**
     * 識別用番号を生成する
     * 
     * フォーマット: FY + 西暦下2桁 + 月(2桁) + 社員番号 + アルファベット + 番号(2桁)
     * 例: FY2512999A01
     * 
     * @param employeeNo 社員番号
     * @return 識別用番号
     */
    String generate(String employeeNo);
}

