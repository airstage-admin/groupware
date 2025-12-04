package com.groupware.dao;

/**
 * ApplicationDao
 * applicationsテーブル用DAOインターフェース
 */
public interface ApplicationDao {
    /**
     * 指定ユーザーの今月の申請件数を取得
     * @param userId ユーザーID
     * @return 今月の申請件数
     */
    int countCurrentMonthApplications(int userId);
}

