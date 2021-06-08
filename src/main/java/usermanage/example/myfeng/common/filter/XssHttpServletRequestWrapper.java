/*
package usermanage.example.myfeng.common.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usermanage.example.myfeng.util.BaseUtil;
import usermanage.example.myfeng.util.GetRequestJsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(XssHttpServletRequestWrapper.class);

    // 用于保存读取body中数据
    private final byte[] body;

    HttpServletRequest orgRequest = null;
    */
/**
     * Constructs a request object wrapping the given request.
     *
     *
     * @param body
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     *//*

    public XssHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        orgRequest = request;
        body = GetRequestJsonUtil.getRequestPostBytes(orgRequest);
    }

    */
/**
     * 覆盖getParameter方法，将参数名和参数值都做xss & sql过滤
     *//*

    @Override
    public String getParameter (String name){
        String value = super.getParameter(clearXss(name));
        if (BaseUtil.stringNotNull(value)){
            String pam = clearXss(value);
            if (!BaseUtil.stringNotNull(pam)){
                LOGGER.debug("请求参数中包含xss注入。请求URL:"
                        + orgRequest.getRequestURI() + " 原始参数:" + value +
                        " xss处理后的参数:" + pam);
            }
            return pam;
        }
        return value;
    }
    */
/**
     * 处理转义字符
     *//*

    private String clearXss(String value){
        String temp = value;

        if (temp == null || "".equals(temp)) {
            return temp;
        }
        temp = temp.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        temp = temp.replaceAll("\\(", "&#40;").replace("\\)", "&#41;");
        temp = temp.replaceAll("'", "&#39;");
        temp = temp.replaceAll("eval\\((.*)\\)", "");
        temp = temp.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        temp = temp.replaceAll("script", "xss");
        temp = temp.replaceAll("frame", "xss");
        temp = temp.replaceAll("object", "xss");
        temp = temp.replaceAll("applet", "xss");
        temp = temp.replaceAll("link", "xss");
        temp = temp.replaceAll("style", "xss");
        temp = temp.replaceAll("img", "xss");
        temp = temp.replaceAll("image", "xss");
        temp = temp.replaceAll("iframe", "xss");
        temp = temp.replaceAll("frameset", "xss");
        temp = temp.replaceAll("meta", "xss");
        temp = temp.replaceAll("onload", "xss");
        temp = temp.replaceAll("alert", "xss");

        return temp;
    }

}
*/
