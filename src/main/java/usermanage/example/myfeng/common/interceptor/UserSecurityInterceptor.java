package usermanage.example.myfeng.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import usermanage.example.myfeng.common.annotation.Authorization;
import usermanage.example.myfeng.common.exception.ManageException;
import usermanage.example.myfeng.common.security.AuthChecker;
import usermanage.example.myfeng.po.UserPo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户权限拦截器
 */
@Slf4j
@Component
public class UserSecurityInterceptor implements HandlerInterceptor {



    //需要拦截的路径集合
    private static final List<String> interceptPaths;

    //加入需要拦截的路径
    static {
        interceptPaths = new ArrayList<>();
        //管理路径
        String adminPath = "/api/admin";
        interceptPaths.add(adminPath);
        //平台路径
        String platformPath = "api/company";
        interceptPaths.add(platformPath);
        //用户个人信息
        String user = "/api/user";
        interceptPaths.add(user);
    }


    /**
     *  判断访问路径是否需要拦截
     */
    private boolean isInterceptPath(String servletPath){
        boolean flag = false;
        for (final String path:interceptPaths
             ) {
            //访问路径以需要拦截路径开头
            if (servletPath.startsWith(path)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     *  拦截功能
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //用户请求路径
        String servletPath = request.getServletPath();
        //用户请求IP
        String requestIp = request.getRemoteAddr();
        //当前登录用户，
        UserPo userPo = (UserPo) request.getSession().getAttribute("user");
        log.info("拦截器：" + requestIp + "进入系统，请求链接为：" + request.getRequestURI() + "	User-Agent:" + request.getHeader("User-Agent"));
        //请求路径是否需要拦截
        if (isInterceptPath(servletPath)){
            //登录验证
            if(userPo ==null){
                //用户未登录不能访问
                log.debug("未登录! " + requestIp + "进入系统，请求链接为：" + request.getRequestURI());
                throw  new ManageException("用户未登录或者会话已过期！");
            }
        }

        //权限验证
        if (handler.getClass().isAssignableFrom(HandlerMethod.class))
        {
            // 方法上是否有授权注解
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Authorization authorization = handlerMethod.getMethodAnnotation(Authorization.class);
            if (authorization == null)
            {
                // 方法所属类上是否有授权注解
                authorization = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Authorization.class);
                if (authorization == null)
                {
                    // 无授权注解，禁止访问
                    //throw new SiatException("EC_AUTH_FAILED");
                    throw new ManageException("无授权注解 禁止访问");
                }
            }
            ArrayList<String> roles = new ArrayList<String>();
            for (int i = 0; i < authorization.value().length; i++)
            {
                roles.add(authorization.value()[i].toString());
            }

            log.info(requestIp + "进入系统，请求链接为：" + request.getRequestURI() + " 当前链接权限：" + roles);
            // 权限检查时，若权限不匹配，需抛出异常
            AuthChecker.check(roles, userPo);

        }else {
            throw new ManageException("授权注解错误");
        }
        return true;
    }




}
