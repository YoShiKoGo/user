package usermanage.example.myfeng.service;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 通用DB操作
 * @Project : user-manage
 * @Class Name    : RedisServiceImpl
 */

@Service("redisService")
public class RedisServiceImpl implements IRedisService {

    //注入RedisTemplate
    @Resource(name = "objRedisTemplate")
    private RedisTemplate<String,Object> objectRedisTemplate ;

    //注入StringTemplate
    @Resource
    private RedisTemplate<String,?> redisTemplate;



    @Override
    public Object getValue(String key) {
        return objectRedisTemplate.boundValueOps(key).get();
    }


    /**
     *  +1
     *  @Method_Name    : incr
     */
    @Override
    public Long incr(String key) {
        /*   AtomicLong只能在一个应用中使用
            RedisAtomicLong可以在所有与Redis有连接的应用中使用
            */

       //获得key对应数值对应的原子性对象
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key,redisTemplate.getConnectionFactory());

        //自增  返回自增后的值
        Long incrLong = redisAtomicLong.incrementAndGet();
        return incrLong;
    }


}
