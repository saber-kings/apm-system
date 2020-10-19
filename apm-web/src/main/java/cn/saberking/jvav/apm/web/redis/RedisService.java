package cn.saberking.jvav.apm.web.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.List;

/**
 * redis服务
 * @author apm
 */
@Service
@SuppressWarnings("uncheck")
public class RedisService {
    @Resource
    JedisPool jedisPool;

    /**
     * 从redis连接池获取redis实例
     *
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = new Jedis();
        try {
            jedis = jedisPool.getResource();
            //对key增加前缀，可用于分类，避免key重复
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);

            return stringToBean(str, clazz);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 存储对象
     *
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> Boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = new Jedis();
        try {
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if (str == null || str.length() <= 0) {
                return false;
            }
            String realKey = prefix.getPrefix() + key;
            //获取过期时间
            int seconds = prefix.expireSeconds();
            if (seconds <= 0) {
                jedis.set(realKey, str);
            } else {
                jedis.setex(realKey, seconds, str);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 删除
     *
     * @param prefix
     * @param key
     * @return
     */
    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = new Jedis();
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            long ret = jedis.del(realKey);
            return ret > 0;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = new Jedis();
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断key对应的value是否存在
     *
     * @param prefix
     * @param key
     * @param field
     * @return
     */
    public boolean existsValue(KeyPrefix prefix, String key, String field) {
        Jedis jedis = new Jedis();
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.hexists(realKey, field);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = new Jedis();
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = new Jedis();
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 返回指定字段的值
     *
     * @param prefix
     * @param key
     * @param filed
     * @param <T>
     * @return
     */
    public <T> String hget(KeyPrefix prefix, String key, String filed) {
        Jedis jedis = new Jedis();
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.hget(realKey, filed);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * @param prefix
     * @param key
     * @param field
     * @param value
     * @param <T>
     * @return
     */
    public <T> Long hset(KeyPrefix prefix, String key, String field, String value) {
        Jedis jedis = new Jedis();
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.hset(realKey, field, value);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 获取列表数值
     *
     * @param prefix
     * @param key
     * @return
     */
    public List<String> hvals(KeyPrefix prefix, String key) {
        Jedis jedis = new Jedis();
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.hvals(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 删除值
     *
     * @param prefix
     * @param key
     * @param field
     * @return
     */
    public Long hdel(KeyPrefix prefix, String key, String field) {
        Jedis jedis = new Jedis();
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix() + key;
            return jedis.hdel(realKey, field);
        } finally {
            returnToPool(jedis);
        }
    }


    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == Integer.class) {
            return value.toString();
        } else if (clazz == Long.class) {
            return value.toString();
        } else if (clazz == String.class) {
            return (String) value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * 将string类型转换为实体类
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }


    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
