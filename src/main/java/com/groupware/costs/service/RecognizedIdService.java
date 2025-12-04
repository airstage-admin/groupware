package com.groupware.costs.service;

/**
 * RecognizedIdService
 * 識別用番号生成サービスインターフェース
 */
public interface RecognizedIdService {
    /**
     * 識別用番号を生成する
     * 
     * フォーマット: FY + 月(2桁) + ユーザーID(2桁) + アルファベット + 番号(2桁)
     * 例: FY1201A01
     * 
     * @param userId ユーザーID
     * @return 識別用番号
     */
    String generate(int userId);
}

