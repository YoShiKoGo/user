package usermanage.example.myfeng.dao;


import usermanage.example.myfeng.po.UserPo;

import java.util.List;

/**
 *  用户持久化
 */
public interface IUserDao {

    /**
     *  持久化User
     */
    public void save(UserPo userPo);


    /**
     *  通过Name 查找用户
     */
    UserPo findByName(String userName);
    /**
     *  通过_id 查找用户
     */
    UserPo findByUserId(String _id);

    /**
     *  查找所有用户
     */
    List<UserPo> findAll();

    /**
     *  用户登录后更新操作（IP  最后登陆时间）
     */
    void updateLogin(UserPo userPo);

    /**
     *  通过主键查找一个用户
     */
    UserPo selectById(String id);
    /**
     *  通过账户名查找一个用户
     */
    UserPo selectByAccount(String account);

    /**
     *   更新个人资料
     */
    void updatePersonal(UserPo userPo);

    /**
     *  通过用户级别查找用户
     */
    List<UserPo> findAllByLevel(String...strings);

    /**
     *  假删除操作
     */
    void delUserUpdate(String id);

    /**
     *  修改用户等级
     */
    void modifyRole(UserPo userPo);

    /**
     *  通过平台名查找用户的人数
     */
    Long getCountByPlatform(String platforName);

    /**
     *  查找平台下所有用户
     */
    List<UserPo> findAllByPlatformName(String platformName);

    /**
     *  更新 超级管理员公司管理员 更改的信息
     */
    void updateByModify(UserPo userPo);

    /**
     *  通过平台主键查找 用户
     */
    List<UserPo> findAllByPlatformId(String platFormId);

    /**
     *  查找该平台下的普通操作员
     */
    List<UserPo> getUserByPLatFormIdAndOpeator(String platformId);

    /**
     * 查找人员（通过用户权限）
     */
    List<UserPo> findAllByUserRole(UserPo operatUserPo);
}
