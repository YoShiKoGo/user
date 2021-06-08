package usermanage.example.myfeng.vo;

import lombok.Data;
import usermanage.example.myfeng.po.UserPo;
import usermanage.example.myfeng.util.BaseUtil;

import java.io.Serializable;


public class OperatorListVo implements Serializable {

    private static final long serialVersionUID = -8642699688603852390L;

    public  OperatorListVo(UserPo userPo) {
        if (BaseUtil.objectNotNull(userPo)){
            this.userPo=userPo;
        }else {
            this.userPo= new UserPo();
        }
    }
    //用户信息
    private final UserPo userPo;

    public String get_id() {
        return userPo.get_id();
    }

    public void set_id(String _id) {
        this.userPo.set_id(_id);
    }

    public String getEmployee_no() {
        return userPo.getEmployee_no();
    }

    public void setEmployee_no(String employee_no) {
        this.userPo.setEmployee_no(employee_no);
    }

    public String getAccount() {
        return userPo.getAccount();
    }

    public void setAccount(String account) {
        this.userPo.setAccount(account);
    }

    public String getNick_name() {
        return userPo.getNick_name();
    }

    public void setNick_name(String nick_name) {
        this.userPo.setNick_name( nick_name);
    }

    public String getPlatformName() {
        return userPo.getPlatformName();
    }

    public void setPlatformName(String platformName) {
        this.userPo.setPlatformName(platformName);
    }

    public String getRole() {
        return userPo.getRole();
    }

    public void setRole(String role) {
        this.userPo.setRole(role);
    }

    public String getMobilePhone() {
        return userPo.getMobilePhone();
    }

    public void setMobilePhone(String mobilePhone) {
        this.userPo.setMobilePhone(mobilePhone);
    }

    public String getEmail() {
        return userPo.getEmail();
    }

    public void setEmail(String email) {
        this.userPo.setEmail(email);
    }


}
