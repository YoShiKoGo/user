package usermanage.example.myfeng.common.security;

/**
 * 用户角色
 */

public enum UserRole {

    /*
        any 无需任何权限
        admin 超级管理员
        company 公司管理员
        operator 普通操作员
    */

    ROLE_ANY(), ROLE_ADMIN(), ROLE_COMPANY(), ROLE_OPERATOR();
}
