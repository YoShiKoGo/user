package usermanage.example.myfeng.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


/**
 * 用户实体类
 */
@Document("user")
public class UserPo implements Serializable {

    private static final long serialVersionUID = 1L;
    //主键
    @Id
    private String _id;


    //用户ID，用于增删改查 唯一标识
    private String accountId;
    //账号
    private String account;

    //密码
    private String password;

    //昵称/真实姓名
    private String nick_name;

    //所属平台,平台标识，超级管理员时，为空，超级管理员不属于任何平台
    private String platformName;
    //所属平台主键
    private String platformId;


    //用户级别
    private String level;

    //用户权限
    private String role;

    //手机号码
    private String  mobilePhone;

    //座机号码
//    private String phone;

    //邮箱
    private String email;

    //工号
    private String employee_no;

    //状态
    private Integer status = 1;

    //创建时间  yyyy-MM-dd HH:mm:ss
    private String create_time;

    //最后一次修改时间 yyyy-MM-dd HH:mm:ss
    private String lastUpdate_time;

    //最后一次登录时间 yyyy-MM-dd HH:mm:ss
    private String lastLogin_time;

    //最后一次登录IP
    private String lastLogin_ip;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployee_no() {
        return employee_no;
    }

    public void setEmployee_no(String employee_no) {
        this.employee_no = employee_no;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLastUpdate_time() {
        return lastUpdate_time;
    }

    public void setLastUpdate_time(String lastUpdate_time) {
        this.lastUpdate_time = lastUpdate_time;
    }

    public String getLastLogin_time() {
        return lastLogin_time;
    }

    public void setLastLogin_time(String lastLogin_time) {
        this.lastLogin_time = lastLogin_time;
    }

    public String getLastLogin_ip() {
        return lastLogin_ip;
    }

    public void setLastLogin_ip(String lastLogin_ip) {
        this.lastLogin_ip = lastLogin_ip;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", accountId='" + accountId + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", level='" + level + '\'' +
                ", role='" + role + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
               // ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", employee_no='" + employee_no + '\'' +
                ", create_time='" + create_time + '\'' +
                ", lastUpdate_time='" + lastUpdate_time + '\'' +
                ", lastLogin_time='" + lastLogin_time + '\'' +
                ", lastLogin_ip='" + lastLogin_ip + '\'' +
                '}';
    }
}
