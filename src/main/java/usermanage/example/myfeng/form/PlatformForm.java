package usermanage.example.myfeng.form;

import java.io.Serializable;

public class PlatformForm implements Serializable {

    private static final long serialVersionUID = -4932278990475910941L;
    //主键
    private String _id;
    //平台名
    private String platformName;
    //平台代码
    private String platformCode;
    //平台创建时间
    private String create_time;
    //最后更新时间
    private String lastUpdate_time;
    //状态
    private int status = 1;


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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    @Override
    public String toString() {
        return "Platfor{" +
                "_id='" + _id + '\'' +
                ", platforName='" + platformName + '\'' +
                ", platforCode='" + platformCode + '\'' +
                '}';
    }
}
