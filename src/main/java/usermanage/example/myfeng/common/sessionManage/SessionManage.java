package usermanage.example.myfeng.common.sessionManage;


import usermanage.example.myfeng.po.UserPo;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * session管理器 用于管理session
 */
public class SessionManage {
    //用于存入当前的session
    public static Map<String, HttpSession> sessionMap = new HashMap<>();

    /**
     *  添加一个session
     */
    public static synchronized   void  addSession(HttpSession session){
        sessionMap.put(session.getId(),session);
    }

    /**
     *  通过账号名 查找用户
     */
    public static synchronized HttpSession getSessionByAccount(String account){
        Set<String> keys = sessionMap.keySet();
        for (String key:keys
             ) {
            HttpSession session = sessionMap.get(key);
            UserPo userPo = (UserPo) session.getAttribute("user");
            if(userPo !=null && userPo.getAccount().equals(account)){
                return session;
            }
        }
        return null;
    }
    
    /**
     *  删除一个session
     */
    public static synchronized void delSession(HttpSession session){
        if(session!=null) {
            HttpSession session1 = sessionMap.remove(session.getId());
            if(session1!=null){
                session1.invalidate();
            }
        }
    }
}
