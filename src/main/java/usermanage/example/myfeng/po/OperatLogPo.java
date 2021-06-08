package usermanage.example.myfeng.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 操作记录
 */
@Document("operatLog")
public class OperatLogPo {
     //主键
     @Id
    private String _id;
    //操作人
    private String account;

    //用户级别
    private String role;

    //操作时间
    private String dateStr;

    //操作内容
    private String logContent;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    @Override
    public String toString() {
        return "OperatLog{" +
                "_id='" + _id + '\'' +
                ", account='" + account + '\'' +
                ", role='" + role + '\'' +
                ", dateStr='" + dateStr + '\'' +
                ", logContent='" + logContent + '\'' +
                '}';
    }
}
