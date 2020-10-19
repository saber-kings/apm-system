package cn.saberking.jvav.apm.web.redis;

import cn.saberking.jvav.apm.web.config.RedisConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * @author saber-kings
 */
@Component
public class RedisPoolFactory {

    @Resource
    RedisConfig redisConfig;

    @Bean
    public JedisPool jedisPoolFactory() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisConfig.getPoolMaxIdle());
        config.setMaxTotal(redisConfig.getPoolMaxTotal());
        config.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(false);
        config.setTestOnCreate(true);
        config.setBlockWhenExhausted(true);
        return new JedisPool(config, redisConfig.getHost(), redisConfig.getPort(),
                redisConfig.getTimeout() * 1000, null, 0);
    }
}
