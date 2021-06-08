package usermanage.example.myfeng.util;


import usermanage.example.myfeng.po.OperatLogPo;
import usermanage.example.myfeng.po.UserPo;

import java.util.Date;

/**
 * 日志操作工具类
 */
public class LogUtil {
    
    /**
     *  生成日志类
     */

    public static OperatLogPo getOperatLog(UserPo userPo, String operatContent){
        //设置操作记录
        OperatLogPo operatLogPo = new OperatLogPo();
        //操作人
        operatLogPo.setAccount(userPo.getAccount());
        //操作人
        operatLogPo.setRole(userPo.getRole());
        //操作时间
        operatLogPo.setDateStr(DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //操作内容
        operatLogPo.setLogContent(operatContent);
        return operatLogPo;
    }
}
