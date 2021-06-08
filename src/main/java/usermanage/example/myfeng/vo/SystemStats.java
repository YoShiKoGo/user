package usermanage.example.myfeng.vo;


import usermanage.example.myfeng.po.OperatLogPo;

import java.util.List;

/**
 * 系统数据 操作记录等
 */
public class SystemStats {

    //用户登录记录
    private List<UserStats> userStatsList ;
    //系统本日登录次数
    private Integer sysDayCount;
    //系统本月登录次数
    private Integer sysMouthCount;
    //查询前十条操作记录
    private List<OperatLogPo> operateLogs;
    //当前系统在线人数
    private Integer onlineCount  = 0;

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    public Integer getSysDayCount() {
        return sysDayCount;
    }

    public void setSysDayCount(Integer sysDayCount) {
        this.sysDayCount = sysDayCount;
    }

    public Integer getSysMouthCount() {
        return sysMouthCount;
    }

    public void setSysMouthCount(Integer sysMouthCount) {
        this.sysMouthCount = sysMouthCount;
    }

    public List<UserStats> getUserStatsList() {
        return userStatsList;
    }

    public void setUserStatsList(List<UserStats> userStatsList) {
        this.userStatsList = userStatsList;
    }

    public List<OperatLogPo> getOperateLogs() {
        return operateLogs;
    }

    public void setOperateLogs(List<OperatLogPo> operateLogs) {
        this.operateLogs = operateLogs;
    }

    @Override
    public String toString() {
        return "SystemStats{" +
                "userStatsList=" + userStatsList +
                ", sysDayCount=" + sysDayCount +
                ", sysMouthCount=" + sysMouthCount +
                ", OperatLogs=" + operateLogs +
                '}';
    }
}
