package usermanage.example.myfeng.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import usermanage.example.myfeng.common.CommonResObject;
import usermanage.example.myfeng.common.annotation.Authorization;
import usermanage.example.myfeng.common.security.UserRole;
import usermanage.example.myfeng.util.CreateSecrteKeyUtil;
import usermanage.example.myfeng.util.VertifyCodeUtils;
import usermanage.example.myfeng.vo.CodeAndKeyVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Date;


/**
 * 验证码生成
 */
@Slf4j
@RestController
@RequestMapping("/api/verify")
public class IndentifyCodeController {

    @Authorization(UserRole.ROLE_ANY)
    @RequestMapping(value="/idcode", method = RequestMethod.GET)
    public Object identifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        CommonResObject commonResObject = new CommonResObject();
        // 1. 使用工具类生成验证码
        String code = VertifyCodeUtils.generateVerifyCode(4);
        // 2. 将验证码放入ServletContext作用域
        Date date = new Date();
        Long idcodeTime = date.getTime() + 60*1000;
        // 3. 将图片转换成base64格式
        // 字节数组输出流在内存中创建一个字节数组缓冲区，所有发送到输出流的数据保存在该字节数组缓冲区中
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //将得到的验证码，使用工具类生成验证码图片，并放入到字节数组缓存区
        VertifyCodeUtils.outputImage(220,60,byteArrayOutputStream,code);
        code = code+"_"+idcodeTime.toString();
        request.getSession().setAttribute("code", code);
        CodeAndKeyVo codeAndKeyVo = new CodeAndKeyVo();
        codeAndKeyVo.setCode_url("data:image/png;base64," + Base64Utils.encodeToString(byteArrayOutputStream.toByteArray()));
        // 前端所需要的公钥E
        //Map<String, Object> map = CreateSecrteKeyUtil.initKey();
        //String publicKey = CreateSecrteKeyUtil.getPublicKey(map);

        //codeAndKeyVo.setExponent(RsaEncryptUtil.getPublicScriptKey().getPublicExponent());
        //codeAndKeyVo.setModule(RsaEncryptUtil.getPublicScriptKey().getModuleKey());

        // 前端所需要的公钥N
        codeAndKeyVo.setPublicScriptKey(CreateSecrteKeyUtil.getPublicKey());

        //codeAndKeyVo.setPublicScriptKey(RsaEncryptUtil.getPublicScriptKey());
        //使用spring提供的工具类，将字节缓存数组中的验证码图片流转换成Base64的形式
        //并返回给浏览器
        commonResObject.setResObj(codeAndKeyVo);
        return commonResObject;
    }

}
