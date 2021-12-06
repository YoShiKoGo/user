package usermanage.example.myfeng.Controller;

import com.alibaba.fastjson.JSON;
import com.travelsky.component.encrypt.common.exeption.EncryptComponentException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import usermanage.example.myfeng.common.CommonResObject;
import usermanage.example.myfeng.common.annotation.Authorization;
import usermanage.example.myfeng.common.exception.ManageException;
import usermanage.example.myfeng.common.security.UserRole;
import usermanage.example.myfeng.form.OperatorForm;
import usermanage.example.myfeng.po.UserPo;
import usermanage.example.myfeng.service.IUserService;
import usermanage.example.myfeng.util.BaseUtil;
import usermanage.example.myfeng.vo.SystemStats;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 管理控制层
 */
@RestController()
@RequestMapping("api/admin")
public class AdminController {

    //注入用户
    @Autowired
    private IUserService userService;

    Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     *  修改用户信息
     */
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    @ResponseBody
    @Authorization({UserRole.ROLE_ADMIN,UserRole.ROLE_COMPANY})
    public Object modifyUser(@RequestBody OperatorForm operatorForm, HttpServletRequest request) throws ManageException {
        log.info("更新用户");
        //获取当前登录管理用户
        String user = (String) request.getSession().getAttribute("user");
        UserPo operateUserPo = BaseUtil.toJAVA(user, UserPo.class) ;
        if(!BaseUtil.stringNotNull(operatorForm.getRole())){
            throw  new ManageException("请选择修改的用户等级");
        }
        if(!BaseUtil.stringNotNull(operatorForm.getAccount())){
            throw new ManageException("账号不能设置为空");
        }
        if(!BaseUtil.stringNotNull(operatorForm.getNick_name())){
            throw new ManageException("昵称不能设置为空");
        }
        if(!BaseUtil.stringNotNull(operatorForm.getEmail())){
            throw new ManageException("邮箱不能设置为空");
        }
        if(!BaseUtil.stringNotNull(operatorForm.getMobilePhone())){
            throw new ManageException("手机号码不能设置为空");
        }
        if(!BaseUtil.stringNotNull(operatorForm.getEmployee_no())){
            throw new ManageException("工号不能设置为空");
        }
        if (operateUserPo.getLevel().equals("2")&&!operateUserPo.getPlatformId()
                .equals(operatorForm.getPlatformId())){
            throw new ManageException("无权限修改该用户信息");
        }
        //更新个人资料
        log.info("更新用户：----"+JSON.toJSONString(operateUserPo));
        CommonResObject commonResObject = userService.updateUser(operateUserPo, operatorForm);
        //更新session中User
        return commonResObject;
    }


    /**
     *  查找用户
     */
    @Authorization(UserRole.ROLE_ADMIN)
    @RequestMapping("/userAll")
    public CommonResObject getAllByLevel1And2(HttpServletRequest request) throws ManageException {
        //获取当前登录用户
        UserPo userPo = (UserPo) request.getSession().getAttribute("user");
        CommonResObject commonResObject = new CommonResObject();
        if (!userPo.getLevel().equals("1")){
            throw new ManageException("越权访问");
        }
        //获取所有用户
        log.info("获取所有用户");
        commonResObject = userService.getAllByLevel(userPo,"2","3");
        return commonResObject;
    }

    /**
     *  添加用户
     */
    @Authorization({UserRole.ROLE_ADMIN,UserRole.ROLE_COMPANY})
    @RequestMapping("/addUser")
    public CommonResObject addUser(@RequestBody OperatorForm operatorForm, HttpServletRequest request) throws Exception {
        if(isAccountExits(operatorForm.getAccount())){
            throw new ManageException("账户名已存在！");
        }
        if(isEmployeeNoExits(operatorForm.getEmployee_no())){
            throw new ManageException("工号已存在！");
        }
        if(isEmployeeNoExits(operatorForm.getNick_name())){
            throw new ManageException("昵称不能为空！");
        }
        if(isEmployeeNoExits(operatorForm.getEmail())){
            throw new ManageException("邮箱不能为空！");
        }
        if(isEmployeeNoExits(operatorForm.getMobilePhone())){
            throw new ManageException("手机号码不能为空！");
        }
        if(isEmployeeNoExits(operatorForm.getPassword())){
            throw new ManageException("密码不能为空！");
        }
        if(isEmployeeNoExits(operatorForm.getRole())){
            throw new ManageException("未选择用户权限！");
        }
        CommonResObject commonResObject = new CommonResObject();
        //获取当前用户
        UserPo onlineUserPo = (UserPo) request.getSession().getAttribute("user");
        commonResObject = userService.creatUser(onlineUserPo,operatorForm);
        return commonResObject;
    }

    /**
     *  删除用户
     */
    @RequestMapping("delUser")
    @ResponseBody
    @Authorization({UserRole.ROLE_COMPANY,UserRole.ROLE_ADMIN})
    public CommonResObject delUser(String _id, HttpServletRequest request) throws ManageException {
        //获取当前登录用户
        UserPo userPo = (UserPo) request.getSession().getAttribute("user");
        CommonResObject commonResObject = userService.delUserById(userPo,_id);
        return commonResObject;
    }



    /**
     *  查询当日 当月的登录次数
     */
    @RequestMapping("/getUserStats")
    @Authorization({UserRole.ROLE_ADMIN})
    public CommonResObject getUserStats(HttpServletRequest request){
        CommonResObject commonResObject =  userService.getUserStats();
        //设置当前系统在线人数
        Integer onlineCount = (Integer) request.getServletContext().getAttribute("onlineCount");
        SystemStats systemStats = (SystemStats) commonResObject.getResObj();
        systemStats.setOnlineCount(onlineCount);
        return commonResObject;
    }
    /**
     *  查询操作日志
     */
    @RequestMapping("/getUserLog")
    @ResponseBody
    @Authorization({UserRole.ROLE_ADMIN})
    public CommonResObject getUserLog(Integer pageSize,Integer currentPage,HttpServletRequest request){
        CommonResObject commonResObject =  userService.getUserLog(pageSize,currentPage);
        return commonResObject;
    }

    /**
     *  查询操作日志条数
     */
    @RequestMapping("/getTotalUserLog")
    @ResponseBody
    @Authorization({UserRole.ROLE_ADMIN})
    public CommonResObject getTotalUserLog(HttpServletRequest request){
        CommonResObject commonResObject =  userService.getTotalUserLog();
        return commonResObject;
    }







    /**
     *  检查当前添加用户工号是否已存在
     */
    private boolean isEmployeeNoExits(String employee_no) {
        boolean flag = false;
        List<UserPo> userPos = userService.getAll();
        //遍历
        for (final UserPo userPo :
                userPos
        ) {
            if (!BaseUtil.stringNotNull(userPo.getEmployee_no())){
                continue;
            }
            if(userPo.getEmployee_no().equals(employee_no)){
                flag=true;
                break;
            }
        }
        return flag;
    }

    /**
     *  检查当前添加用户账户名是否已存在
     */
    private boolean isAccountExits(String account){
        boolean flag = false;
        List<UserPo> userPos = userService.getAll();
        //遍历
        for (final UserPo userPo :
                userPos
        ) {
            if(userPo.getAccount().equals(account)){
                flag=true;
                break;
            }
        }
        return flag;
    }
}
