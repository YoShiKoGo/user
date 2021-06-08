/*
package usermanage.example.myfeng.common.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

*/
/**
 * 过滤器
 *//*

@Slf4j
@Component
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //设置请求编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //转换为HttpServletRequest，为获取请求类型
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //限制请求类型
        httpServletResponse.addHeader("allow","GET,POST");
        //限制系统http访问方法，目前仅支持get、post
        if (!"GET".equals(httpServletRequest.getMethod())&&!"POST".equals(httpServletRequest.getMethod())){
            //返回403状态码
            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }
        log.info("初始化");
        chain.doFilter(new XssHttpServletRequestWrapper(httpServletRequest), httpServletResponse);

    }
}
*/
