package usermanage.example.myfeng.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import usermanage.example.myfeng.dao.IOperatLogDao;
import usermanage.example.myfeng.po.OperatLogPo;

import java.util.List;

/**
 * 操作记录dao实现类
 */
@Service
public class OperatLogImpl implements IOperatLogDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *  添加一条操作记录
     */
    @Override
    public void addLog(OperatLogPo operatLogPo) {
        //插入一条记录
        mongoTemplate.insert(operatLogPo);
    }

    /**
     *  查找操作记录  （日期排序）
     */
    @Override
    public List<OperatLogPo> findTopTen(int pageSize, int currentPage) {
        //跳过多少个
        int skip = (currentPage-1)*pageSize;
       //新建查询对象
        Query query = new Query();
        //设置排序对象
        query.with(Sort.by(Sort.Direction.DESC,"dateStr"));
        return mongoTemplate.find(query.limit(pageSize).skip(skip), OperatLogPo.class);
    }

    @Override
    public List<OperatLogPo> getTotalUserLog() {
        //新建查询对象
        Query query = new Query();
        //设置排序对象
        query.with(Sort.by(Sort.Direction.DESC,"dateStr"));
        return mongoTemplate.find(query, OperatLogPo.class);
    }
}
