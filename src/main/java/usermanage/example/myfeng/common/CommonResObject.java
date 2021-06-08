package usermanage.example.myfeng.common;

import lombok.Data;

/**
 * 	系统正常响应前台对象
 */
@Data
public class CommonResObject {

    private String resCode = "1000";

    private String resMsg;

    private Object resObj;
}
