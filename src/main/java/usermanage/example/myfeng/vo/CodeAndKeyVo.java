package usermanage.example.myfeng.vo;

import com.travelsky.component.encrypt.rsa.PublicScriptKey;
import lombok.Data;

import java.io.Serializable;

@Data
public class CodeAndKeyVo implements Serializable {

    private static final long serialVersionUID = 0;

    //验证码url
    private String code_url;

    //js加密的N值
    private String module;

    //js加密的e值
    private String exponent;

    //key
    private String PublicScriptKey;
}
