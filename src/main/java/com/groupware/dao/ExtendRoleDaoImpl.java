package com.groupware.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * ExtendRoleDaoImpl
 * extend_roleテーブル用DAO実装
 */
@Repository
public class ExtendRoleDaoImpl implements ExtendRoleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ExtendRoleDaoImpl.class);

    @Override
    public Map<Integer, Integer> findAllRoles() {
        String sql = "SELECT target_user_id, role FROM extend_role";
        Map<Integer, Integer> roleMap = new HashMap<>();
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            for (Map<String, Object> row : rows) {
                int userId = (Integer) row.get("target_user_id");
                int role = (Integer) row.get("role");
                roleMap.put(userId, role);
            }
            logger.info("権限データ取得: {}件", roleMap.size());
        } catch (Exception e) {
            logger.error("権限データ取得時、DBアクセスエラー", e);
        }
        return roleMap;
    }

    @Override
    public int findRoleByUserId(int userId) {
        String sql = "SELECT role FROM extend_role WHERE target_user_id = ?";
        try {
            Integer role = jdbcTemplate.queryForObject(sql, Integer.class, userId);
            return role != null ? role : 1;
        } catch (Exception e) {
            // 該当なしの場合は一般（1）を返す
            return 1;
        }
    }

    /**
     * 全ユーザーの有効な権限マップを取得
     * extend_roleの値を優先し、なければdepartment_typeのis_adminを確認
     * - extend_roleにデータあり → その権限を使用
     * - extend_roleにデータなし かつ department_type.is_admin=true → 管理者(2)
     * - それ以外 → 一般(1)
     */
    @Override
    public Map<Integer, Integer> findAllEffectiveRoles() {
        String sql = """
            SELECT
                u.id AS user_id,
                COALESCE(er.role,
                    CASE WHEN dt.is_admin = TRUE THEN 2 ELSE 1 END
                ) AS effective_role
            FROM users u
            LEFT JOIN extend_role er ON u.id = er.target_user_id
            LEFT JOIN department_type dt ON u.department = dt.code
            WHERE u.delflg = FALSE
            """;
        Map<Integer, Integer> roleMap = new HashMap<>();
        try {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            for (Map<String, Object> row : rows) {
                // DBの型に依存せず、安全に Number として受け取る
                Number userIdNum = (Number) row.get("user_id");
                Number roleNum = (Number) row.get("effective_role");

                // intValue() を使って安全に int へ変換
                int userId = userIdNum.intValue();
                int role = roleNum.intValue();

                roleMap.put(userId, role);
            }
            logger.info("有効権限データ取得: {}件", roleMap.size());
        } catch (Exception e) {
            logger.error("有効権限データ取得時、DBアクセスエラー", e);
        }
        return roleMap;
    }
}

