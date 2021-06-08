package usermanage.example.myfeng.vo;


import usermanage.example.myfeng.po.PlatformPo;

/**
 *平台信息Vo
 */
public class PlatformPoVo extends PlatformPo {

    //平台管理员
    private String Manage;

    //平台下的操作人员人数
    private Long operatorCount;

    public String getManage() {
        return Manage;
    }

    public void setManage(String manage) {
        Manage = manage;
    }

    public Long getOperatorCount() {
        return operatorCount;
    }

    public void setOperatorCount(Long operatorCount) {
        this.operatorCount = operatorCount;
    }

    @Override
    public String toString() {
        return "PlatforVo{" +
                "Manage='" + Manage + '\'' +
                ", operatorCount=" + operatorCount +
                '}';
    }
}
