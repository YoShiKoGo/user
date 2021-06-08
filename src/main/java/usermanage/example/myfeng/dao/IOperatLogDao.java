package usermanage.example.myfeng.dao;


import usermanage.example.myfeng.po.OperatLogPo;

import java.util.List;

/**
 * 操作记录 Dao
 */
public interface IOperatLogDao {


    /**
     *  添加一条操作记录
     */
    void addLog(OperatLogPo operatLogPo);

    /**
     *  查找操作记录 （日期排序）
     */
    List<OperatLogPo> findTopTen(int pageSize, int currentPage);

    /**
     * 查找操作记录
     */
    List<OperatLogPo> getTotalUserLog();
}
