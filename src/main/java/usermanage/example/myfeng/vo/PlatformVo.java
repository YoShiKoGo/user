package usermanage.example.myfeng.vo;

import usermanage.example.myfeng.po.PlatformPo;
import usermanage.example.myfeng.po.UserPo;
import usermanage.example.myfeng.util.BaseUtil;

import java.io.Serializable;
public class PlatformVo implements Serializable {
    private static final long serialVersionUID = -4465199312670056223L;

    //平台名
    private PlatformPo platformPo;

    public PlatformVo(PlatformPo platformPo){
        if (BaseUtil.objectNotNull(platformPo)){
            this.platformPo=platformPo;
        }else {
            this.platformPo= new PlatformPo();
        }
    }

    public String getCreate_time() {
        return platformPo.getCreate_time();
    }

    public void setCreate_time(String create_time) {
        this.platformPo.setCreate_time(create_time);
    }

    public String getLastUpdate_time() {
        return platformPo.getLastUpdate_time();
    }

    public void setLastUpdate_time(String lastUpdate_time) {
        this.platformPo.setLastUpdate_time(lastUpdate_time);
    }

    public int getStatus() {
        return platformPo.getStatus();
    }

    public void setStatus(int status) {
        this.platformPo.setStatus(status);
    }


    public String get_id() {
        return platformPo.get_id();
    }

    public void set_id(String _id) {
        this.platformPo.set_id(_id);
    }

    public String getPlatformName() {
        return platformPo.getPlatformName();
    }

    public void setPlatformName(String platformName) {
        this.platformPo.setPlatformName(platformName);
    }

    public String getPlatformCode() {
        return platformPo.getPlatformCode();
    }

    public void setPlatformCode(String platformCode) {
        this.platformPo.setPlatformCode(platformCode);
    }



}
