package com.groupware.costs.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.sql.results.LoadingLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.dao.ApplicationDao;
import com.groupware.dao.ApplicationDaoImpl;



/**
 * RecognizedIdServiceImpl
 * 識別用番号生成サービス実装
 *
 * フォーマット: FY + 西暦下2桁 + 月(2桁) + 社員番号 + アルファベット + 番号(2桁)
 * 例: FY2512999A01
 *
 * アルファベットのルール:
 * - 1-99件目: A01-A99
 * - 100-198件目: B01-B99
 * - 199-297件目: C01-C99
 * ...以降同様（99件ごとに繰り上がる）
 */
@Service
public class RecognizedIdServiceImpl implements RecognizedIdService {

    @Autowired
    private ApplicationDao applicationDao;

    private static final Logger logger = LoggerFactory.getLogger(RecognizedIdServiceImpl.class);

    @Override
    public String generate(String employeeNo) {
        // 現在の年（下2桁）と月（2桁）を取得
        LocalDate now = LocalDate.now();
        String year = String.format("%02d", now.getYear() % 100);
        String month = String.format("%02d", now.getMonthValue());

        // 社員番号を3桁でフォーマット（数値の場合は0パディング）
        String formattedEmployeeNo = formatEmployeeNo(employeeNo);

        // 今月の申請件数を取得（月またぎでリセット、社員番号で検索）
        int currentCount = applicationDao.countCurrentMonthApplications(employeeNo);

        // 次の申請番号（1から始まる）
        int nextNumber = currentCount + 1;

        // アルファベットと2桁番号を計算
        // 1-99 → A01-A99
        // 100-198 → B01-B99
        // 199-297 → C01-C99
        char letter = (char) ('A' + (nextNumber - 1) / 99);
        int twoDigit = ((nextNumber - 1) % 99) + 1;

        logger.info("ID: nextNumber={}, twoDigit={}", nextNumber, twoDigit);

        // 識別用番号を生成
        return String.format("FY%s%s%s%c%02d", year, month, formattedEmployeeNo, letter, twoDigit);
    }

    /**
     * 社員番号を3桁でフォーマット
     * @param employeeNo 社員番号
     * @return 3桁でフォーマットされた社員番号
     */
    private String formatEmployeeNo(String employeeNo) {
        if (employeeNo == null || employeeNo.isEmpty()) {
            return "000";
        }
        try {
            // 数値に変換できる場合は3桁でゼロパディング
            int num = Integer.parseInt(employeeNo);
            return String.format("%03d", num);
        } catch (NumberFormatException e) {
            // 数値でない場合はそのまま返す
            return employeeNo;
        }
    }
}

