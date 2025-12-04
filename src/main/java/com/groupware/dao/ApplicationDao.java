package com.groupware.dao;

/**
 * ApplicationDao
 * applicationsテーブル用DAOインターフェース
 */
public interface ApplicationDao {
    /**
     * 指定社員番号の今月の申請件数を取得
     * @param employeeNo 社員番号
     * @return 今月の申請件数
     */
    int countCurrentMonthApplications(String employeeNo);
}

