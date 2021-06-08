package usermanage.example.myfeng.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import usermanage.example.myfeng.dao.IPlatformDao;
import usermanage.example.myfeng.po.PlatformPo;
import usermanage.example.myfeng.po.UserPo;
import usermanage.example.myfeng.util.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 平台信息实现类
 */

@Repository
public class PlatformDaoImpl implements IPlatformDao {

    //注入MongoDBTemplate
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     *  查找所有平台信息
     */
    @Override
    public List<PlatformPo> findAll() {
        //设置查询状态为正常1
        Query query = new Query(Criteria.where("status").is(1));
        return mongoTemplate.find(query, PlatformPo.class);
    }

    /**
     *  添加一个平台信息
     */
    @Override
    public PlatformPo addPlatform(PlatformPo platformPo) {
       return  mongoTemplate.save(platformPo);
    }

    /**
     *  锁定当前状态 为1
     */
    @Override
    public void delPlatformStatus(String _id) {
        //更新对象
        Query query = new Query(Criteria.where("_id").is(_id));
        //更新内容
        Update update = new Update();
        update.set("status",0);
        //最后更新时间
        update.set("lastUpdate_time",
                DateUtil.parseDateToStr(new Date(),DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
        mongoTemplate.updateFirst(query,update, PlatformPo.class);
    }

    /**
     *  通过id查找平台
     */
    @Override
    public PlatformPo findById(String id) {
        //查找对象
        Query query = new Query(
                new Criteria().andOperator(
                        Criteria.where("_id").is(id),
                        Criteria.where("status").is(1)));
        return mongoTemplate.findOne(query, PlatformPo.class);
    }

    /**
     *  更新 平台代码 平台名称
     */
    @Override
    public void update(PlatformPo platformPo) {
        //查询对象
        Query query = new Query(Criteria.where("_id").is(platformPo.get_id()));
        //更新内容
        Update update = new Update();
        update.set("platformName", platformPo.getPlatformName());
        update.set("platformCode", platformPo.getPlatformCode());
        update.set("lastUpdate_time", platformPo.getLastUpdate_time());
        //更新
        mongoTemplate.updateFirst(query,update, PlatformPo.class);
    }

    /**
     * 查询平台信息
     */
    @Override
    public List<PlatformPo> findByPlatformName(String platformName) {
        //查找对象
        Criteria criteria = new Criteria();
        Pattern pattern = Pattern.compile("^.*"+platformName+".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(
                criteria.andOperator(
                        Criteria.where("status").is(1)));
        query.addCriteria(criteria.and("platformName").regex(pattern));
        return mongoTemplate.find(query, PlatformPo.class);
    }

    @Override
    public List<PlatformPo> findAllByUserRole(UserPo userPo) {
        //设置查询状态为正常1
        Query query = new Query(Criteria.where("status").is(1));
        if (userPo.getRole().equals("ROLE_ADMIN")){
            return mongoTemplate.find(query, PlatformPo.class);
        }else {
            query.addCriteria(Criteria.where("_id").is(userPo.getPlatformId()));
            return mongoTemplate.find(query, PlatformPo.class);
        }
    }
}
