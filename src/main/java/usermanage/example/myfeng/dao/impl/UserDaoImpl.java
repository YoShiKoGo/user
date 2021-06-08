package usermanage.example.myfeng.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import usermanage.example.myfeng.dao.IUserDao;
import usermanage.example.myfeng.po.PlatformPo;
import usermanage.example.myfeng.po.UserPo;
import usermanage.example.myfeng.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户dao
 */

@Repository("userDao")
public class UserDaoImpl implements IUserDao {

    //注入MongoDBTemplate
    @Autowired
    private MongoTemplate mongoTemplate;
    
    /**
     *  保存用户
     */
    @Override
    public void save(UserPo userPo) {
        mongoTemplate.save(userPo);
    }

    @Override
    public UserPo findByName(String account) {
        //生成查询对象
        Query query = new Query(Criteria.where("account").is(account));
        //查
        UserPo userPo = (UserPo) mongoTemplate.findOne(query, UserPo.class);
        return userPo;
    }

    @Override
    public UserPo findByUserId(String _id) {
        //生成查询对象
        Query query = new Query(Criteria.where("_id").is(_id));
        //查
        UserPo userPo = (UserPo) mongoTemplate.findOne(query, UserPo.class);
        return userPo;
    }

    @Override
    public List<UserPo> findAll() {
        //新建查询对象
        Query query = new Query(Criteria.where("status").is("1"));
        //设置排序对象
        return mongoTemplate.findAll(UserPo.class);
    }


    /**
     *  更新操作 IP 登陆时间
     */
    @Override
    public void updateLogin(UserPo userPo) {
        Query query = new Query(Criteria.where("_id").is(userPo.get_id()));
        //更新具体参数
        //Update update = new Update();
        //update.set("lastLogin_time",user.getLastLogin_time());
        //update.set("lastLogin_ip",user.getLastLogin_ip());
        //更新
        mongoTemplate.save(userPo,"user");
    }

    /**
     *  查找一个用户
     *  @Method_Name    : selectById
     */
    @Override
    public UserPo selectById(String _id) {
        //查询条件
        Query query = new Query(Criteria.where("_id").is(_id));
        //查询
        return mongoTemplate.findOne(query, UserPo.class);
    }

    @Override
    public UserPo selectByAccount(String account) {
        //查询条件
        Query query = new Query(Criteria.where("account").is(account));
        //查询
        return mongoTemplate.findOne(query, UserPo.class);
    }

    /**
     *  更新个人资料
     *  @Method_Name    : updatePersonal
     */
    @Override
    public void updatePersonal(UserPo userPo) {
        Query query = new Query(Criteria.where("_id").is(userPo.get_id()));
        //更新具体参数
        /*Update update = new Update();
        update.set("nick_name",user.getNick_name());
        update.set("mobilePhone",user.getMobilePhone());
        update.set("email",user.getEmail());
        update.set("lastUpdate_time",
                DateUtil.parseDateToStr(new Date(),DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));*/
        //更新
        mongoTemplate.save(userPo,"user");
    }

    /**
     *   通过level查找用户
     */
    @Override
    public List<UserPo> findAllByLevel(String... strings) {
        //查询对象
        Query query = new Query();
        //遍历参数
        List<Criteria> criteria1 = new ArrayList<>();
        for (final  String string:
             strings) {
            criteria1.add(Criteria.where("level").is(string));
        }
        //将集合list转化为数组
        Criteria[] criterias = new Criteria[criteria1.size()];
        //查询条件
        Criteria criteriaOr = new Criteria().orOperator(criteria1.toArray(criterias));
        Criteria criteriaAnd = new Criteria().andOperator(criteriaOr, Criteria.where("status").is(1));
       // Criteria criteria = Criteria.where("status").is(1);
        query.addCriteria(criteriaAnd);
        //查找所有普通用户
        List<UserPo> userPos = mongoTemplate.find(query, UserPo.class);
        for (UserPo u: userPos
        ) {
            Query queryPl = new Query(Criteria.where("_id").is(u.getPlatformId()));
            PlatformPo platformPo = mongoTemplate.findOne(queryPl, PlatformPo.class);
            u.setPlatformName(platformPo.getPlatformName());
        }
        return userPos;
    }

    /**
     *  逻辑删除
     *  @Method_Name    : delUserUpdate
     */
    @Override
    public void delUserUpdate(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        //更新具体参数
        Update update = new Update();
        update.set("status",0);
        update.set("lastUpdate_time",
                DateUtil.parseDateToStr(new Date(),DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //更新
        mongoTemplate.updateFirst(query,update, UserPo.class);
    }

    /**
     *  修改用户等级
     *  @Method_Name    : modifyRole
     */
    @Override
    public void modifyRole(UserPo userPo) {
        Query query = new Query(Criteria.where("_id").is(userPo.get_id()));
        //更新具体参数
        Update update = new Update();
        update.set("level", userPo.getLevel());
        update.set("lastUpdate_time",
                DateUtil.parseDateToStr(new Date(),DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //更新
        mongoTemplate.updateFirst(query,update, UserPo.class);
    }

    /**
     *  通过平台得到用户人数
     *  @Method_Name    : getCountByPlatform
     */
    @Override
    public Long getCountByPlatform(String platformId) {
        //查询对象
        Query query = new Query(Criteria.where("platformId").is(platformId));

        //得到查询人数
        Long count = mongoTemplate.count(query, UserPo.class);
        return count;
    }

    /**
     *  查找该平台下所有用户
     *  @Method_Name    : findAllByPlatformName
     */
    @Override
    public List<UserPo> findAllByPlatformName(String platformName) {
        //查询对象
        Query query = new Query(Criteria.where("platformName").is(platformName));
        List<UserPo> userPos =  mongoTemplate.find(query, UserPo.class);

        return userPos;
    }

    /**
     *  更新信息
     *  @Method_Name    : updateByModify
     */
    @Override
    public void updateByModify(UserPo userPo) {
        //更新对象
        Query query = new Query(Criteria.where("account").is(userPo.getAccount()));
        //更新内容
        Update update = new Update();
        update.set("nick_name", userPo.getNick_name());
        update.set("level", userPo.getLevel());
//        if (!user.getPlatformId().equals("")){
//            update.set("platformId",user.getPlatformId());
//        }
        update.set("mobilePhone", userPo.getMobilePhone());
        update.set("email", userPo.getEmail());
        update.set("employee_no", userPo.getEmployee_no());
        update.set("role", userPo.getRole());
        update.set("lastUpdate_time",DateUtil.parseDateToStr(new Date(),
                DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        //更新
        mongoTemplate.updateFirst(query,update, UserPo.class);
    }

    /**
     *  通过平台id查询用户
     *  @Method_Name    : findAllByPlatformId
     */
    @Override
    public List<UserPo> findAllByPlatformId(String platformId) {
        Query query = new Query(Criteria.where("platformId").is(platformId));
        List<UserPo> userPos = mongoTemplate.find(query, UserPo.class);
        for (UserPo u: userPos
        ) {
            Query queryPl = new Query(Criteria.where("_id").is(u.getPlatformId()));
            PlatformPo platformPo = mongoTemplate.findOne(queryPl, PlatformPo.class);
            u.setPlatformName(platformPo.getPlatformName());
        }
        return userPos;
    }

    @Override
    public List<UserPo> getUserByPLatFormIdAndOpeator(String platformId) {
        Query query = new Query(new Criteria().andOperator(Criteria.where("platformId").is(platformId),
                Criteria.where("status").is(1), Criteria.where("level").is("3")));
        List<UserPo> userPos = mongoTemplate.find(query, UserPo.class);
        for (UserPo u: userPos
        ) {
            Query queryPl = new Query(Criteria.where("_id").is(u.getPlatformId()));
            PlatformPo platformPo = mongoTemplate.findOne(queryPl, PlatformPo.class);
            u.setPlatformName(platformPo.getPlatformName());
        }
        return userPos;
    }

    @Override
    public List<UserPo> findAllByUserRole(UserPo operatUserPo) {
        //查找人员（通过用户权限）1:所有 2：根据管理员所在平台查找 todo
        if (operatUserPo.getLevel().equals("1")){
            Query query = new Query(new Criteria().andOperator(
                    Criteria.where("status").is(1)));
            return mongoTemplate.find(query, UserPo.class);
        }else {
            Query query = new Query(new Criteria().andOperator(Criteria.where("platformId").is(operatUserPo.getPlatformId()),
                    Criteria.where("status").is(1)));
            return mongoTemplate.find(query, UserPo.class);
        }
    }

}
