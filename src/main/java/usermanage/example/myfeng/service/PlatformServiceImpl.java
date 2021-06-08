package usermanage.example.myfeng.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usermanage.example.myfeng.common.CommonResObject;
import usermanage.example.myfeng.common.exception.ManageException;
import usermanage.example.myfeng.dao.IOperatLogDao;
import usermanage.example.myfeng.dao.IPlatformDao;
import usermanage.example.myfeng.dao.IUserDao;
import usermanage.example.myfeng.form.PlatformForm;
import usermanage.example.myfeng.po.OperatLogPo;
import usermanage.example.myfeng.po.PlatformPo;
import usermanage.example.myfeng.po.UserPo;
import usermanage.example.myfeng.util.BaseUtil;
import usermanage.example.myfeng.util.DateUtil;
import usermanage.example.myfeng.util.RoleChangeUtil;
import usermanage.example.myfeng.vo.PlatformPoVo;
import usermanage.example.myfeng.vo.PlatformVo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 平台信息服务的实现类
 */
@Slf4j
@Service("platformService")
public class PlatformServiceImpl implements  IPlatformService {



   //注入平台信息数据操作接口
    @Autowired
    private IPlatformDao platformDao;
    //注入用户
    @Autowired
    private IUserDao userDao;

    //注入操作记录
    @Autowired
    private IOperatLogDao operatLogDao;
    
    /**
     *  查找所有平台
     */
    @Override
    public CommonResObject findAll() {
        CommonResObject commonResObject = new CommonResObject();
        //查找所有平台
        List<PlatformPo> platformPos = platformDao.findAll();
        //将平台po封装成vo
        List<PlatformPoVo> platformVos = getPlatformPoVo(platformPos);
        commonResObject.setResMsg("查找成功");
        commonResObject.setResObj(platformVos);
        return commonResObject;
    }

    /**
     *  添加一个平台信息
     */
    @Override
    public CommonResObject addPlatform(UserPo operateUserPo, PlatformForm platformForm) throws ManageException {
        CommonResObject resObject = new CommonResObject();
        //设置状态为正常
        PlatformPo platformPo = new PlatformPo();
        platformForm.setStatus(1);
        //设置创建时间
        platformForm.setCreate_time(DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //设置更新时间
        platformForm.setLastUpdate_time(DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //调用dao层 持久化
        try {
            BeanUtils.copyProperties(platformForm,platformPo);
            PlatformPo platformPo1 =  platformDao.addPlatform(platformPo);
        }catch (Exception e){
            log.info("添加平台失败");
            throw new ManageException("添加平台失败"+platformForm.getPlatformName());
        }
        resObject.setResObj(platformPo);
        resObject.setResMsg("添加平台成功");
        //设置操作记录
        OperatLogPo operatLogPo = new OperatLogPo();
        //操作人
        operatLogPo.setAccount(operateUserPo.getAccount());
        //操作人
        operatLogPo.setRole(operateUserPo.getRole());
        //操作时间
        operatLogPo.setDateStr(DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //操作内容
        operatLogPo.setLogContent(new StringBuffer().append(RoleChangeUtil.change(operateUserPo.getLevel()))
                .append("_").append(operateUserPo.getAccount()).append("在")
                .append(operatLogPo.getDateStr()).append(
                        "添加了平台 ").append(platformPo.getPlatformName()).toString());
        log.info("添加平台操作："+ operatLogPo.getLogContent());
        //操作记录持久化
        operatLogDao.addLog(operatLogPo);
        return resObject;
    }

    /**
     * 删除一个平台 逻辑删除
     */
    @Override
    public CommonResObject delPlatform(UserPo operateUserPo, String id) {
        CommonResObject commonResObject = new CommonResObject();
        //查找该平台
        PlatformPo platformPo = platformDao.findById(id);
        //删除平台
        platformDao.delPlatformStatus(id);
        //该平台下的用户(普通操作员，公司管理员)禁止使用
        List<UserPo> userPos = userDao.findAllByPlatformId(platformPo.get_id());
        for (UserPo userPo : userPos
             ) {
            //假删除用户
            userDao.delUserUpdate(userPo.get_id());
        }
        commonResObject.setResMsg("删除成功！");
        //设置操作记录
        OperatLogPo operatLogPo = new OperatLogPo();
        //操作人
        operatLogPo.setAccount(operateUserPo.getAccount());
        //操作人
        operatLogPo.setRole(operateUserPo.getRole());
        //操作时间
        operatLogPo.setDateStr(DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //操作内容
        operatLogPo.setLogContent(new StringBuffer().append(RoleChangeUtil.change(operateUserPo.getLevel()))
                .append("_").append(operateUserPo.getAccount()).append("在")
                .append(operatLogPo.getDateStr()).append(
                        "删除了平台 ").append(platformPo.getPlatformName()).toString());
        log.info("删除平台操作："+ operatLogPo.getLogContent());
        //操作记录持久化
        operatLogDao.addLog(operatLogPo);
        return commonResObject;
    }

    /**
     *  通过Id 获取平台信息
     */
    @Override
    public CommonResObject getPlatFormById(String id) {
        CommonResObject resObject = new CommonResObject();
        try {
            log.info("获取平台信息成功");
            resObject.setResMsg("获取平台信息成功！");
            PlatformPo platformPo = platformDao.findById(id);
            PlatformVo platformVo = new PlatformVo(platformPo);
            resObject.setResObj(platformVo);
        }catch (Exception e){
            log.info("查询平台信息失败");
        }
        return resObject;
    }

    /**
     *  修改平台信息
     */
    @Override
    public CommonResObject modifyPlatform(UserPo operateUserPo, PlatformForm platformForm) throws ManageException {
        CommonResObject resObject = new CommonResObject();
        //最后更新时间
        PlatformPo platformPo = new PlatformPo();
        try {
            BeanUtils.copyProperties(platformForm,platformPo);
            platformPo.setLastUpdate_time(DateUtil.parseDateToStr(new Date(),
                    DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
            platformDao.update(platformPo);
        }catch (Exception e){
            log.info("修改平台信息失败");
            throw new ManageException("修改平台信息失败");
        }
        resObject.setResMsg("更新平台信息成功!");
        //设置操作记录
        OperatLogPo operatLogPo = new OperatLogPo();
        //操作人
        operatLogPo.setAccount(operateUserPo.getAccount());
        //操作人
        operatLogPo.setRole(operateUserPo.getRole());
        //操作时间
        operatLogPo.setDateStr(DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //操作内容
        operatLogPo.setLogContent(new StringBuffer().append(RoleChangeUtil.change(operateUserPo.getLevel()))
                .append("_").append(operateUserPo.getAccount()).append("在")
                .append(operatLogPo.getDateStr()).append(
                        "更新了平台 ").append(platformPo.getPlatformName()).toString());
        log.info("更新平台操作："+ operatLogPo.getLogContent());
        //操作记录持久化
        operatLogDao.addLog(operatLogPo);
        return resObject;
    }

    @Override
    public CommonResObject getPlatFormByPlatformName(String platformName) {
        CommonResObject resObject = new CommonResObject();
        List<PlatformPo> platformPos = platformDao.findByPlatformName(platformName);
        //将平台po封装成vo
        List<PlatformPoVo> platformVos = getPlatformPoVo(platformPos);
        resObject.setResMsg("查找平台信息成功");
        resObject.setResObj(platformVos);
        return resObject;
    }

    private List<PlatformPoVo> getPlatformPoVo(List<PlatformPo> platformPos ){
        List<PlatformPoVo> platformVos = new ArrayList<>();
        //查找所有管理员
        List<UserPo> userPos = userDao.findAllByLevel("2");
        for (int i = 0; i< platformPos.size(); i++){
            PlatformPo platformPo = getPlatformPo(platformPos, i);
            PlatformPoVo platformVo = new PlatformPoVo();
            platformVo.set_id(platformPo.get_id());
            platformVo.setPlatformName(platformPo.getPlatformName());
            platformVo.setPlatformCode(platformPo.getPlatformCode());
            platformVo.setStatus(platformPo.getStatus());
            platformVo.setCreate_time(platformPo.getCreate_time());
            //循环遍历 查找管理员 是否是本平台的管理员
            for (UserPo u: userPos
            ) {
                if (BaseUtil.stringNotNull(u.getPlatformName())
                        &&u.getPlatformName().equals(platformPo.getPlatformName())) {
                    platformVo.setManage(u.getAccount());
                    break;
                }
            }
            //设置平台上操作人员的人数
            Long operatorCount = userDao.getCountByPlatform(platformPo.get_id());
            platformVo.setOperatorCount(operatorCount);
            platformVos.add(platformVo);
        }
        return platformVos;
    }

    private PlatformPo getPlatformPo(List<PlatformPo> platformPos, int i) {
        return platformPos.get(i);
    }

    /**
     * 查找所有平台
     * @param userPo 操作者
     * @return 返回所有平台信息
     * @throws ManageException
     */
    @Override
    public CommonResObject findAllByUserRole(UserPo userPo) throws ManageException {
        CommonResObject commonResObject = new CommonResObject();
        if (!userPo.getLevel().equals("1")){
            throw new ManageException("无权限访问");
        }
        //查找所有平台
        List<PlatformPo> platformPos = platformDao.findAllByUserRole(userPo);
        //将平台po封装成vo
        List<PlatformPoVo> platformVos = getPlatformPoVo(platformPos);
        commonResObject.setResMsg("查找成功");
        commonResObject.setResObj(platformVos);
        return commonResObject;
    }
}
