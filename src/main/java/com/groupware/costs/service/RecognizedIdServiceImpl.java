package com.groupware.costs.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.dao.ApplicationDao;

/**
 * RecognizedIdServiceImpl
 * 識別用番号生成サービス実装
 * 
 * フォーマット: FY + 月(2桁) + ユーザーID(2桁) + アルファベット + 番号(2桁)
 * 例: FY1201A01
 * 
 * アルファベットのルール:
 * - 1-99件目: A01-A99
 * - 100件目: B00
 * - 101-199件目: B01-B99
 * - 200件目: C00
 * ...以降同様
 */
@Service
public class RecognizedIdServiceImpl implements RecognizedIdService {

    @Autowired
    private ApplicationDao applicationDao;

    @Override
    public String generate(int userId) {
        // 現在の月を取得（2桁）
        LocalDate now = LocalDate.now();
        String month = String.format("%02d", now.getMonthValue());
        
        // ユーザーIDを2桁でフォーマット
        String userIdStr = String.format("%02d", userId);
        
        // 今月の申請件数を取得
        int currentCount = applicationDao.countCurrentMonthApplications(userId);
        
        // 次の申請番号（1から始まる）
        int nextNumber = currentCount + 1;
        
        // アルファベットと2桁番号を計算
        // 1-99 → A01-A99
        // 100 → B00
        // 101-199 → B01-B99
        char letter = (char) ('A' + nextNumber / 100);
        int twoDigit = nextNumber % 100;
        
        // 識別用番号を生成
        return String.format("FY%s%s%c%02d", month, userIdStr, letter, twoDigit);
    }
}

