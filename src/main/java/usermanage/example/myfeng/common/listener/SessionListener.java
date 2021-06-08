package usermanage.example.myfeng.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usermanage.example.myfeng.common.sessionManage.SessionManage;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * session 监听器
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    /**
     * Logger
     */
    private static final Logger lg = LoggerFactory.getLogger(SessionListener.class);
    //创建时执行
    @Override
    public synchronized  void  sessionCreated(HttpSessionEvent se)  {
        lg.info("创建了一个session");
        //获取APP
//        ServletContext servletContext = se.getSession().getServletContext();
//        //获取当前在线人数
//        Integer onlineCount = (Integer) servletContext.getAttribute("onlineCount");
//        //当有个session创建时 ，在线用户人数就+1
//        if(onlineCount==null){
//            onlineCount = 1;
//            servletContext.setAttribute("onlineCount",onlineCount);
//        }else {
//            onlineCount++;
//            servletContext.setAttribute("onlineCount",onlineCount);
//        }
        SessionManage.addSession(se.getSession());
    }
    //销毁时执行
    @Override
    public synchronized   void sessionDestroyed(HttpSessionEvent se){
        lg.info("销毁了一个session");
        //获取APP
//        ServletContext servletContext = se.getSession().getServletContext();
//        //获取当前在线人数
//        Integer onlineCount = (Integer) servletContext.getAttribute("onlineCount");
//        //当有个session被销毁时 ，在线用户人数就-1
//        onlineCount--;
//        servletContext.setAttribute("onlineCount",onlineCount);
        SessionManage.delSession(se.getSession());
    }
}
