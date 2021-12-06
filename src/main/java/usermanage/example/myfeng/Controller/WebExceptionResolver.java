package usermanage.example.myfeng.Controller;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import usermanage.example.myfeng.common.CommonResObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Project : user-manage
 */
@Slf4j
@Controller
public class WebExceptionResolver implements HandlerExceptionResolver {


    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error("请求{}发生异常！",httpServletRequest.getRequestURI());
        ModelAndView mv = new ModelAndView();
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        if("GET".equalsIgnoreCase(httpServletRequest.getMethod())){
            return new ModelAndView("redirect:/error/404");
        }else{
            //不需要跳转页面,返回字符串，一般是json格式
            try{
                //response.getWriter().write("error");
                CommonResObject resObj = new CommonResObject();
                resObj.setResCode("0");
                //resObj.setResMsg(errorMsg);
                resObj.setResMsg(e.getMessage());
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                String jsonStr = JSONArray.toJSONString(resObj);
                httpServletResponse.getWriter().write(jsonStr);
                log.error("GenericExceptionHandler异常处理响应写入response时出错，本次异常异常码：headCode=" + 0 + ",headCodeCN=" + e.getMessage() + ";msgCode=" + 0 + ",msgCodeCN=" + e.getMessage() + " ，本次写入错误信息：" + e.getMessage());
                e.printStackTrace();
            } catch (Exception e1){
                e1.printStackTrace();
                log.error("GenericExceptionHandler异常处理响应写入response时出错，本次异常异常码：headCode=" + 0 + ",headCodeCN=" + e.getMessage() + ";msgCode=" + 0 + ",msgCodeCN=" + e.getMessage() + " ，本次写入错误信息：" + e.getMessage());
            }
            return mv;
        }
    }
}
