package usermanage.example.myfeng.service;

/*
 *  通用DB操作
 */
public interface IRedisService {


    /**
     *   通过Key 获取对应的Value
     *  @Method_Name    : getValue
     */
    public Object getValue(String key);


    /**
     *  将key对应的值  进行加一处理 若key值不存在 则新建一个再 加一
     */
    public Long incr(String key);


}
