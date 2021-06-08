package usermanage.example.myfeng.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import usermanage.example.myfeng.common.CommonResObject;
import usermanage.example.myfeng.common.annotation.Authorization;
import usermanage.example.myfeng.common.exception.ManageException;
import usermanage.example.myfeng.common.security.UserRole;
import usermanage.example.myfeng.form.PlatformForm;
import usermanage.example.myfeng.po.PlatformPo;
import usermanage.example.myfeng.po.UserPo;
import usermanage.example.myfeng.service.IPlatformService;
import usermanage.example.myfeng.util.BaseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 平台信息管理
 */
@Slf4j
@RestController
@RequestMapping("api/company")
public class PlatformController {

    //注入平台服务
    @Autowired
    private IPlatformService platformService;

    /**
     *  查找所有平台
     */
    @RequestMapping("/findAll")
    @Authorization(UserRole.ROLE_ADMIN)
    public Object platformAll(HttpServletRequest request) throws ManageException {
        UserPo userPo = (UserPo) request.getSession().getAttribute("user");
        log.info("查询平台信息"+userPo.getAccount());
        CommonResObject commonResObject = platformService.findAllByUserRole(userPo);
        return commonResObject;
    }


    /**
     *  添加平台
     */
    @RequestMapping("/addPlatform")
    @ResponseBody
    @Authorization(UserRole.ROLE_ADMIN)
    public CommonResObject addPlatform(HttpServletRequest request,@RequestBody PlatformForm platformForm) throws ManageException {
        if (isPlatform(platformForm)){
            log.info("校验平台信息完成");
        }
        //获得当前登录用户
        UserPo userPo = (UserPo) request.getSession().getAttribute("user");
        if (!userPo.getLevel().equals("1")){
            throw new ManageException("权限错误");
        }
        CommonResObject resObject = platformService.addPlatform(userPo, platformForm);
        return resObject;
    }

    /**
     *  修改平台信息
     */
    @RequestMapping("/modifyPlatform")
    @ResponseBody
    @Authorization(UserRole.ROLE_ADMIN)
    public CommonResObject modifyPlatform(@RequestBody PlatformForm platformForm, HttpServletRequest request) throws ManageException {
        //获得当前登录用户
        UserPo userPo = (UserPo) request.getSession().getAttribute("user");
        if (!userPo.getLevel().equals("1")){
            throw new ManageException("权限错误");
        }
        if (isPlatform(platformForm)){
            log.info("校验平台信息完成");
        }
        return platformService.modifyPlatform(userPo, platformForm);
    }
    /**
     *  查询平台通过平台名称
     */
    @RequestMapping("/findByPlatformName")
    @ResponseBody
    @Authorization({UserRole.ROLE_ADMIN, UserRole.ROLE_COMPANY})
    public CommonResObject getPlatformByPlatformName(String platformName,HttpServletRequest request){
        //获得当前登录用户
        UserPo userPo = (UserPo) request.getSession().getAttribute("user");
        CommonResObject commonResObject = new CommonResObject();
        if (userPo.getLevel().equals("1")||userPo.getPlatformName().equals(platformName)){
            commonResObject = platformService.getPlatFormByPlatformName(platformName);
        }
        return commonResObject;
    }

    /**
     *  根据用户得到平台信息
     */
    @RequestMapping("/getPlatformByUser")
    @ResponseBody
    @Authorization({UserRole.ROLE_COMPANY,UserRole.ROLE_OPERATOR,})
    public CommonResObject getPlatformByUser(HttpServletRequest request){
        //获得当前在线用户
        UserPo userPo = (UserPo) request.getSession().getAttribute("user");
        return platformService.getPlatFormById(userPo.getPlatformId());
    }


    /**
     *  删除平台
     */
    @Authorization(UserRole.ROLE_ADMIN)
    @RequestMapping("delPlatform")
    @ResponseBody
    public CommonResObject delPlatform(HttpServletRequest request, String _id) throws ManageException {
        //获得当前登录用户
        UserPo operateUserPo = (UserPo) request.getSession().getAttribute("user");
        if (!operateUserPo.getLevel().equals("1")){
            log.info("越权删除");
            throw new ManageException("越权删除");
        }
        log.info("删除平台");
        return  platformService.delPlatform(operateUserPo,_id);
    }

    /**
     *  判断平台名 是否已存在
     */
    private boolean isPlatformNameExits(String platformName){
        boolean flag = false;
        //查找所有平台
        List<PlatformPo> platformPos = (List<PlatformPo>) platformService.findAll().getResObj();
        //遍历查询平台名
        for (final PlatformPo p: platformPos
        ) {
            if(platformName.equals(p.getPlatformName())){
                flag = true;
            }
        }
        return flag;
    }
    /**
     * 校验平台信息正确
     */
    private boolean isPlatform(PlatformForm platformForm) throws ManageException {
        if(!BaseUtil.stringNotNull(platformForm.getPlatformName())){
            throw new ManageException("平台名不能为空");
        }
        if(!BaseUtil.stringNotNull(platformForm.getPlatformCode())){
            throw new ManageException("平台代码不能为空");
        }
        if(isPlatformNameExits(platformForm.getPlatformName())){
            throw new ManageException("平台名已存在");
        }
        if(isPlatformCodeExits(platformForm.getPlatformCode())){
            throw new ManageException("平台代码已存在");
        }
        if(!checkPlatformCode(platformForm.getPlatformCode())){
            throw new ManageException("平台代码格式错误（例：ABC");
        }
        return true;
    }

    /**
     *  是否平台代码是否已经存在
     */
    private boolean isPlatformCodeExits(String platformCode){
        boolean flag = false;
        //查找所有平台
        List<PlatformPo> platformPos = (List<PlatformPo>) platformService.findAll().getResObj();
        //遍历查询平台名
        for (final PlatformPo p: platformPos
             ) {
            if(platformCode.equals(p.getPlatformCode())){
                flag = true;
            }
        }
        return flag;
    }

    /**
     *  检查平台代码格式是否正确
     */
    private boolean checkPlatformCode(String platformCode){
        //正则匹配字符串
        String pattern = "[A-Z][A-Z][A-Z]";
        return  Pattern.matches(pattern, platformCode);
    }

}
