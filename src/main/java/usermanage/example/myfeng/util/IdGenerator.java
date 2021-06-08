package usermanage.example.myfeng.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 生成uuId
 */
public class IdGenerator {

    //时间格式化
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddhhmm");

    /**
     *  生成uuid 时间与uuid结合
     */
    public static String uuId(){
        String rs = "";
        rs = UUID.randomUUID().toString().replace("-","").substring(11);
        rs = simpleDateFormat.format(new Date())+"_"+rs;
        return rs;
    }
}
