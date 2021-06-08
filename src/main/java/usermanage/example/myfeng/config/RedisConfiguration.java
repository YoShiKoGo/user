package usermanage.example.myfeng.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * redis配置
 */
@Configuration
// 必须加，使配置生效
@EnableCaching
public class RedisConfiguration {
    //将系统自带的RedisTemplate 示例化为 RedisTemplate<String, Object>，如有其他类型的RedisTemplate需求，可自行增加
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * Logger
     */
    private static final Logger lg = LoggerFactory.getLogger(RedisConfiguration.class);

    /**
     *  @Method_Name    : objRedisTemplate
     */
    @Bean(name = "objRedisTemplate")
    RedisTemplate<String, Object> objRedisTemplate() {
        lg.info("初始化objRedisTemplate成功");
        //key使用string序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value采用json序列化
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(
                Object.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
