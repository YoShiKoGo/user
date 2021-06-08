package usermanage.example.myfeng.util;


import usermanage.example.myfeng.common.security.UserRole;
import usermanage.example.myfeng.common.security.UserRoleCn;

/**
 * 将role的信息 转化成中文 用于现实中在前端
 */
public class RoleChangeUtil {


    /**
     *  Role转化成中文 用于现实中在前端
     */
    public static String change(String level){
        if(level.equals("1")){
           return UserRoleCn.超级管理员.toString();
        } else if (level.equals("2")) {
           return UserRoleCn.公司管理员.toString();
        }else {
           return UserRoleCn.普通操作员.toString();
        }
    }
    /**
     *  Role转化成中文 用于现实中在前端
     */
    public static String changeByRole(String role){
        if(role.equals("ROLE_ADMIN")){
            return UserRoleCn.超级管理员.toString();
        } else if (role.equals("ROLE_COMPANY")) {
            return UserRoleCn.公司管理员.toString();
        }else {
            return UserRoleCn.普通操作员.toString();
        }
    }
    /**
     *  Role转化回来 用于存储
     */
    public static String changeReturn(String role){
        if(role.equals("超级管理员")||role.equals("ROLE_ADMIN")){
            return UserRole.ROLE_ADMIN.toString();
        } else if (role.equals("公司管理员")||role.equals("ROLE_COMPANY")) {
            return UserRole.ROLE_COMPANY.toString();
        }else {
            return UserRole.ROLE_OPERATOR.toString();
        }
    }
}
