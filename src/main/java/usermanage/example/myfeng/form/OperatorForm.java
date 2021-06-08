package usermanage.example.myfeng.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class OperatorForm implements Serializable {

    private static final long serialVersionUID = -8642699688603852390L;

    //账号
    private String account;

    //昵称
    private String nick_name;

    //所属平台,平台标识，超级管理员时，为空，超级管理员不属于任何平台
    private String platformName;


    //用户权限
    private String role;

    //手机号码
    private String  mobilePhone;

    //邮箱
    private String email;

    //用户级别
    private String level;

    //工号
    private String employee_no;

    //密码
    private String password;

    //平台id
    private String platformId;

}
