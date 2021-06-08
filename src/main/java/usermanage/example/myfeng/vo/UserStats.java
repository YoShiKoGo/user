package usermanage.example.myfeng.vo;

/**
 * 用户登录操作统计
 */
public class UserStats {
    //用户账号
    private String account;
    //本日登录次数
    private Integer dayCount;
    //本月登录次数
    private Integer month;
    //用户角色
    private String role;



    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    private Integer isOnline;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "UserStats{" +
                "account='" + account + '\'' +
                ", dayCount=" + dayCount +
                ", month=" + month +
                '}';
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
