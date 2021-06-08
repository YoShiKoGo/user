package usermanage.example.myfeng.service;


import com.travelsky.component.encrypt.common.exeption.EncryptComponentException;
import usermanage.example.myfeng.common.CommonResObject;
import usermanage.example.myfeng.common.exception.ManageException;
import usermanage.example.myfeng.form.OperatorForm;
import usermanage.example.myfeng.po.UserPo;

import java.util.List;

/**
 *  用户服务层
 */
public interface IUserService {


    /**
     *  登录用户
     *  @Method_Name    : login
     */
    CommonResObject login(String userName, String password,String ip) throws ManageException, EncryptComponentException;

    /**
     *   通过主键查找一个用户
     */
    CommonResObject getUserById(String _id, UserPo userPo) throws ManageException;

    /**
     *  用户更新操作
     */
    CommonResObject updateSomeInfo(OperatorForm operatorForm, UserPo userPo) throws ManageException;


    /**
     *   查找所有用户
     */
    CommonResObject getAllByLevel(UserPo operatUserPo, String...strings);

    /**
     *  查询所有用户
     */
    List<UserPo> getAll();

    /**
     *  删除用户 逻辑删除
     */
    CommonResObject delUserById(UserPo operatUserPo, String id) throws ManageException;
    /**
     *  修改用户资料
     */
    CommonResObject updateUser(UserPo operatUserPo, OperatorForm operatorForm) throws ManageException;


    /**
     *  通过平台名查找用户
     */
    CommonResObject getUserByPlatFormName(String platFormName);

    /**
     *  获取统计用户数据
     */
    CommonResObject getUserStats();

    /**
     * 获取操作日志
     */
    CommonResObject getUserLog(int pageSize, int currentPage);

    /**
     * 获取操作日志条数
     */
    CommonResObject getTotalUserLog();

    /**
     * 添加用户
     */
    CommonResObject creatUser(UserPo onlineUserPo, OperatorForm operatorForm) throws Exception;
}
