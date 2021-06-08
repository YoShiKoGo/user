package usermanage.example.myfeng.dao;



import usermanage.example.myfeng.po.PlatformPo;
import usermanage.example.myfeng.po.UserPo;

import java.util.List;

/**
 * 平台dao层接口
 */
public interface IPlatformDao {
    /**
     *  查找所有平台
     */
    List<PlatformPo> findAll();

    /**
     *  添加一个平台
     */
    PlatformPo addPlatform(PlatformPo platformPo);

    /**
     *  设置当前平台状态为1
     */
    void delPlatformStatus(String _id);

    /**
     *  通过主键查找平台
     */
    PlatformPo findById(String id);

    /**
     *  更新平台名称 平台代码
     */
    void update(PlatformPo platformPo);

    /**
     * 查询平台信息
     */
    List<PlatformPo> findByPlatformName(String platformName);

    /**
     * 根据用户权限查找平台
     * @param userPo
     */
    List<PlatformPo> findAllByUserRole(UserPo userPo);
}
