package usermanage.example.myfeng.form;

import lombok.Data;

@Data
public class UserForm {

    //用户名
    private String username;

    //密码
    private String pwd;

    //验证码
    private String vCode;
}
