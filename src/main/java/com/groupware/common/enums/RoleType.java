package com.groupware.common.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * RoleType
 * 権限区分enum
 * 
 * 1: 一般
 * 2: 管理者
 * 3: 決裁者
 */
public enum RoleType {
    GENERAL(1, "一般"),
    ADMIN(2, "管理者"),
    APPROVER(3, "決裁者");

    private final int code;
    private final String displayName;

    RoleType(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public int getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * コードから権限区分を取得
     * @param code 権限コード
     * @return RoleType（該当なしの場合はGENERAL）
     */
    public static RoleType fromCode(int code) {
        for (RoleType role : values()) {
            if (role.code == code) {
                return role;
            }
        }
        return GENERAL; // デフォルトは一般
    }

    /**
     * 権限区分のマップを取得
     * @return Map<Integer, String> コードと表示名のマップ
     */
    public static Map<Integer, String> getRoleMap() {
        Map<Integer, String> roleMap = new LinkedHashMap<>();
        for (RoleType role : values()) {
            roleMap.put(role.code, role.displayName);
        }
        return roleMap;
    }
}

