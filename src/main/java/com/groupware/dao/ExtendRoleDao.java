package com.groupware.dao;

import java.util.Map;

/**
 * ExtendRoleDao
 * extend_roleテーブル用DAOインターフェース
 */
public interface ExtendRoleDao {
    /**
     * 全ユーザーの権限マップを取得
     * @return Map<Integer, Integer> ユーザーIDと権限コードのマップ
     */
    Map<Integer, Integer> findAllRoles();

    /**
     * 指定ユーザーの権限を取得
     * @param userId ユーザーID
     * @return 権限コード（該当なしの場合は1=一般）
     */
    int findRoleByUserId(int userId);

    /**
     * 全ユーザーの有効な権限マップを取得
     * extend_roleの値を優先し、なければdepartment_typeのis_adminを確認
     * @return Map<Integer, Integer> ユーザーIDと権限コードのマップ
     */
    Map<Integer, Integer> findAllEffectiveRoles();
}

