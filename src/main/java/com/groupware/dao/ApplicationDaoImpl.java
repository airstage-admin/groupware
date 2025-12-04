package com.groupware.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * ApplicationDaoImpl
 * applicationsテーブル用DAO実装
 */
@Repository
public class ApplicationDaoImpl implements ApplicationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ApplicationDaoImpl.class);

    @Override
    public int countCurrentMonthApplications(int userId) {
        String sql = """
            SELECT COUNT(*) FROM applications 
            WHERE applicant_user_id = ? 
            AND YEAR(apply_date) = YEAR(CURRENT_DATE) 
            AND MONTH(apply_date) = MONTH(CURRENT_DATE)
            """;
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
            return count != null ? count : 0;
        } catch (Exception e) {
            logger.error("今月の申請件数取得時、DBアクセスエラー (userId={})", userId, e);
            return 0;
        }
    }
}

