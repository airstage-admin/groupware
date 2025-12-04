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

    /**
     * 指定社員番号の今月の申請件数を取得
     * 月またぎでリセットされる（毎月1日から0件カウント開始）
     *
     * @param employeeNo 社員番号
     * @return 今月の申請件数
     */
    @Override
    public int countCurrentMonthApplications(String employeeNo) {
        String sql = """
            SELECT COUNT(*)
            FROM applications a
            INNER JOIN users u ON a.applicant_user_id = u.id
            WHERE u.employee_no = ?
              AND YEAR(a.apply_date) = YEAR(CURRENT_DATE)
              AND MONTH(a.apply_date) = MONTH(CURRENT_DATE)
            """;
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, employeeNo);
            int result = count != null ? count : 0;
            logger.info("今月の申請件数取得: employeeNo={}, count={}", employeeNo, result);
            return result;
        } catch (Exception e) {
            logger.error("今月の申請件数取得時、DBアクセスエラー (employeeNo={})", employeeNo, e);
            return 0;
        }
    }
}

