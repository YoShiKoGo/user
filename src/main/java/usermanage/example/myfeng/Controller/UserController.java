package usermanage.example.myfeng.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usermanage.example.myfeng.common.CommonResObject;
import usermanage.example.myfeng.common.annotation.Authorization;
import usermanage.example.myfeng.common.exception.ManageException;
import usermanage.example.myfeng.common.security.UserRole;
import usermanage.example.myfeng.common.sessionManage.SessionManage;
import usermanage.example.myfeng.dao.IUserDao;
import usermanage.example.myfeng.form.OperatorForm;
import usermanage.example.myfeng.form.UserForm;
import usermanage.example.myfeng.po.UserPo;
import usermanage.example.myfeng.service.IRedisService;
import usermanage.example.myfeng.service.IUserService;
import usermanage.example.myfeng.util.*;
import usermanage.example.myfeng.vo.OperatorListVo;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制层
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {
    //注入redisDao
    @Autowired
    private IRedisService redisService;

    //注入用户持久层
    @Autowired
    private IUserDao userDao;
    //注入用户
    @Autowired
    private IUserService userService;

    @RequestMapping("/")
    @ResponseBody
    public Object browseCountIncr(HttpServletRequest httpRequest) {
        log.info("用户访问成功");
        Map<String, Object> map = new HashMap<>();
        Long browseCount = redisService.incr("browseCount");
        log.info("用户访问成功：当前访问量" + browseCount);
        map.put("访问量", browseCount);
        return map;
    }


    /**
     * 登录用户
     */
    @Authorization(UserRole.ROLE_ANY)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login( UserForm userForm, HttpServletRequest request) throws Exception {
        //用户名不能为空
        if (userForm.getUsername() == null || userForm.getUsername().trim().equals("")) {
            log.info("ss" + userForm.getUsername());
            throw new ManageException("用户名不能为空");
        }
        //密码不能为空
        if (userForm.getPwd() == null || userForm.getPwd().trim().equals("")) {
            throw new ManageException("密码不能为空");
        }
        //验证码不能为空
        if (userForm.getVCode() == null || userForm.getVCode().trim().equals("")) {
            throw new ManageException("验证码不能为空");
        }
        String vCodeSession = (String) request.getSession().getAttribute("code");

        // 传输的密码解密
        //String decryptPassword = AesUtils.decrypt(userForm.getPwd(), userForm.getKey());
        //通过私钥对前端加密的密码解密
        String dePwd = CreateSecrteKeyUtil.decrypt(userForm.getPwd(),CreateSecrteKeyUtil.getPrivateKey());
        // 传输的密码解密 add by jwei
        //String dePwd = RsaEncryptUtil.decryptByPrivateKey(userForm.getPwd());
        //验证码校验时的当前时间戳
        Date date = new Date();
        Long idCodeUseTime = date.getTime();
        CommonResObject commonResObject = new CommonResObject();
        //验证验证码的有效性，登陆完让验证码失效
        if (vCodeSession != null) {
            String[] vCodeSessions = vCodeSession.split("_");
            Long vCodeTime;
            try {
                vCodeTime = BaseUtil.stringToLong(vCodeSessions[1]);
            } catch (ManageException manageException) {
                log.info("验证码转换失败");
                throw new ManageException("登陆错误");
            }
            if (vCodeTime > idCodeUseTime) {
                if (userForm.getVCode() == null || userForm.getVCode().trim().equals("") || vCodeSessions[0] == null
                        || !vCodeSessions[0].equalsIgnoreCase(userForm.getVCode())) {
                    throw new ManageException("验证码错误");
                }
            } else {
                commonResObject.setResMsg("验证码失效，请重新获取");
                return commonResObject;
            }
        } else {
            //请重新获取验证码
            throw new ManageException("验证码失效，请重新获取");
        }
        //获取登陆IP
        String ip = request.getRemoteAddr();
        //登录
        commonResObject = userService.login(userForm.getUsername(), dePwd, ip);
        //判断返回code 是否验证通过
        if ("1000".equals(commonResObject.getResCode())) {
            UserPo userPo = (UserPo) commonResObject.getResObj();
            if ("1".equals(userPo.getLevel())) {
                //超级管理员
                userPo.setRole(UserRole.ROLE_ADMIN.toString());
            } else if ("2".equals(userPo.getLevel())) {
                //公司管理员
                userPo.setRole(UserRole.ROLE_COMPANY.toString());
            } else {
                //普通操作员
                userPo.setRole(UserRole.ROLE_OPERATOR.toString());
            }
            //判断当前用户是否在线
            HttpSession sessionC = SessionManage.getSessionByAccount(userForm.getUsername());
            if (sessionC != null) {
                //下线操作
                SessionManage.delSession(sessionC);
            }
            //将当前登录的用户信息存入session
            request.getSession().setAttribute("user", userPo);
            //统计登录次数
            String dayStr = DateUtil.parseDateToStr(new Date(),
                    DateUtil.DATE_FORMAT_YYYYMMDD);
            String mouStr = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMM);
            //当日登录次数+1
            redisService.incr(userPo.getAccount() + "_" + dayStr);
            //当月次数+1
            redisService.incr(userPo.getAccount() + "_" + mouStr);
            //系统总的登录次数+1
            redisService.incr("sysCount_" + dayStr);
            redisService.incr("sysCount_" + mouStr);
            //获取APP
            ServletContext servletContext = request.getSession().getServletContext();
            //获取当前在线人数
            Integer onlineCount = (Integer) servletContext.getAttribute("onlineCount");
            //当有个session创建时 ，在线用户人数就+1
            if (onlineCount == null) {
                onlineCount = 1;
                servletContext.setAttribute("onlineCount", onlineCount);
            } else {
                onlineCount++;
                servletContext.setAttribute("onlineCount", onlineCount);
            }
            //将登陆的用户信息 返回给前端
            OperatorListVo operatorListVo = new OperatorListVo(userPo);
            commonResObject.setResObj(operatorListVo);
        }
        //返回
        return commonResObject;
    }


    /**
     * 用过平台id查找用户
     */
    @Authorization({UserRole.ROLE_ADMIN, UserRole.ROLE_COMPANY})
    @RequestMapping("/getUsersByPlatForm")
    @ResponseBody
    public CommonResObject getUsersByPlatForm(String platformId, HttpServletRequest request) throws ManageException {
        CommonResObject resObject = new CommonResObject();
        log.info("请求平台名查找用户");
        if (!BaseUtil.stringNotNull(platformId)) {
            throw new ManageException("平台名为空");
        }
        //获取当前登录用户
        UserPo userPo = (UserPo) request.getSession().getAttribute("user");
        //判断当前用户是否有权限(超级管理员可以访问任何平台)
        if (userPo.getLevel().equals("1")|| userPo.getPlatformId().equals(platformId)){
            //用过平台id查找用户
            resObject = userService.getUserByPlatFormName(platformId);
        }
        return resObject;
    }

    /**
     * 用户界面查找一个用户
     */
    @Authorization({UserRole.ROLE_ADMIN, UserRole.ROLE_COMPANY, UserRole.ROLE_OPERATOR})
    @RequestMapping("getUser")
    @ResponseBody
    public Object getUser(String _id,HttpServletRequest request) throws ManageException {
        log.info("查找一个用户");
        //获取当前登录用户
        UserPo userPo = (UserPo) request.getSession().getAttribute("user");
        //查找一个用户
        CommonResObject commonResObject = userService.getUserById(_id, userPo);
        return commonResObject;
    }

    /**
     * 用户修改一些信息
     */
    @Authorization({UserRole.ROLE_ADMIN, UserRole.ROLE_COMPANY, UserRole.ROLE_OPERATOR})
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public Object updateUser(@RequestBody OperatorForm operatorForm, HttpServletRequest request) throws ManageException {
        if (operatorForm.getEmail() == null || operatorForm.getEmail().trim().equals("")) {
            throw new ManageException("邮箱不能为空");
        }
        if (operatorForm.getMobilePhone() == null || operatorForm.getMobilePhone().trim().equals("")) {
            throw new ManageException("手机号不能为空");
        }
        if (operatorForm.getNick_name() == null || operatorForm.getNick_name().trim().equals("")) {
            throw new ManageException("昵称不能为空");
        }
        if (operatorForm.getEmployee_no() == null || operatorForm.getEmployee_no().trim().equals("")) {
            throw new ManageException("工号不能为空");
        }
        log.info("更新用户");
        //获取当前登录用户
        UserPo userPo = (UserPo) request.getSession().getAttribute("user");
        CommonResObject commonResObject = userService.updateSomeInfo(operatorForm, userPo);
        request.getSession().setAttribute("user", operatorForm);
        return commonResObject;
        //更新个人资料
    }


    /**
     * 用户退出
     */
    @Authorization({UserRole.ROLE_ADMIN, UserRole.ROLE_COMPANY, UserRole.ROLE_OPERATOR})
    @RequestMapping("/logout")
    @ResponseBody
    public CommonResObject logout(HttpServletRequest request, HttpServletResponse response) {
        CommonResObject commonResObject = new CommonResObject();
        //清空session 内容
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        //清空cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                cookie.setValue("-");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        //获取APP
        ServletContext servletContext = request.getSession().getServletContext();
        //获取当前在线人数
        Integer onlineCount = (Integer) servletContext.getAttribute("onlineCount");
        //当有个session被销毁时 ，在线用户人数就-1
        if (onlineCount != null) {
            onlineCount--;
        }
        servletContext.setAttribute("onlineCount", onlineCount);
        commonResObject.setResCode("1");
        commonResObject.setResMsg("用户已退出");
        return commonResObject;
    }
}
