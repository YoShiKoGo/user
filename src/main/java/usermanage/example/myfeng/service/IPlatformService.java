package usermanage.example.myfeng.service;


import usermanage.example.myfeng.common.CommonResObject;
import usermanage.example.myfeng.common.exception.ManageException;
import usermanage.example.myfeng.form.PlatformForm;
import usermanage.example.myfeng.po.PlatformPo;
import usermanage.example.myfeng.po.UserPo;


/**
 *  平台信息服务接口
 */
public interface IPlatformService {
    
    /**
     *  查找所有平台
     */
    CommonResObject findAll();

    /**
     *  添加一个平台信息
     */
    CommonResObject addPlatform(UserPo operateUserPo, PlatformForm platformForm) throws ManageException;

    /**
     *  删除一个平台信息 （逻辑删除）
     */
    CommonResObject delPlatform(UserPo operateUserPo, String id);

    /**
     *  通过id获取平台信息
     */
    CommonResObject getPlatFormById(String id);

    /**
     *  修改平台信息
     */
    CommonResObject modifyPlatform(UserPo operateUserPo, PlatformForm platformForm) throws ManageException;

    /**
     * 查询平台信息
     */
    CommonResObject getPlatFormByPlatformName(String platformName);

    /**
     * 根据用户权限查找平台
     */
    CommonResObject findAllByUserRole(UserPo userPo) throws ManageException;
}
