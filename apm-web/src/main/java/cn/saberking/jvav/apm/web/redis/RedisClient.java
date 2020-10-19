package cn.saberking.jvav.apm.web.redis;

import cn.saberking.jvav.apm.web.utils.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author apm
 */
@Component
@SuppressWarnings("unused")
public class RedisClient {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // Key（键），简单的key-value操作

    /**
     * 实现命令：TTL key，以秒为单位，返回给定 key的剩余生存时间(TTL, time to live)。
     *
     * @param key 键
     * @return 结果
     */
    public long ttl(String key) {
        Long expire = stringRedisTemplate.getExpire(key);
        if (expire != null) {
            return expire;
        } else {
            return 0;
        }
    }

    /**
     * 实现命令：expire 设置过期时间，单位秒
     *
     * @param key 键
     */
    public void expire(String key, long timeout) {
        stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 实现命令：INCR key，增加key一次
     *
     * @param key 键
     * @return 自增的结果
     */
    public long incr(String key, long delta) {
        Long increment = stringRedisTemplate.opsForValue().increment(key, delta);
        if (increment != null) {
            return increment;
        } else {
            return 0;
        }
    }

    /**
     * 实现命令：KEYS pattern，查找所有符合给定模式 pattern的 key
     *
     * @param pattern 正则
     * @return 结果
     */
    public Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(pattern);
    }

    /**
     * 实现命令：DEL key，删除一个key
     *
     * @param key 键
     */
    public void del(String key) {
        stringRedisTemplate.delete(key);
    }

    /**
     * 删除 key
     *
     * @param prefix 前缀
     * @param key    键
     * @return 结果
     */
    public boolean del(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        Boolean isDel = stringRedisTemplate.delete(realKey);
        if (isDel != null) {
            return isDel;
        } else {
            return false;
        }
    }

    // String（字符串）

    /**
     * 实现命令：SET key value，设置一个key-value（将字符串值 value关联到 key）
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 实现命令：SET key value EX seconds，设置key-value和超时时间（秒）
     *
     * @param key     键
     * @param value   值
     * @param timeout （以秒为单位）
     */
    public void set(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 实现命令：GET key，返回 key所关联的字符串值。
     *
     * @param key 键
     * @return 结果值
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 判断key对应的value是否存在
     *
     * @param prefix 前缀
     * @param key    键
     * @param field  字段
     * @return 结果
     */
    public boolean existsValue(KeyPrefix prefix, String key, String field) {
        Object o = stringRedisTemplate.opsForHash().get(key, field);
        return StringUtils.isNotNull(o);
    }

    // Hash（哈希表）

    /**
     * 实现命令：HSET key field value，将哈希表 key中的域 field的值设为 value
     *
     * @param key   键
     * @param field 字段
     * @param value 值
     */
    public void hset(String key, String field, Object value) {
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * @param prefix 前缀
     * @param key    键
     * @param field  字段
     * @param value  值
     */
    public void hset(KeyPrefix prefix, String key, String field, String value) {
        String realKey = prefix.getPrefix() + key;
        stringRedisTemplate.opsForHash().put(realKey, field, value);
    }

    /**
     * 实现命令：HGET key field，返回哈希表 key中给定域 field的值
     *
     * @param key   键
     * @param field 字段
     * @return 结果
     */
    public String hget(String key, String field) {
        return (String) stringRedisTemplate.opsForHash().get(key, field);
    }

    /**
     * 返回指定字段的值
     *
     * @param prefix 前缀
     * @param key    键
     * @param filed  字段
     * @return 结果
     */
    public String hget(KeyPrefix prefix, String key, String filed) {
        String realKey = prefix.getPrefix() + key;
        return (String) stringRedisTemplate.opsForHash().get(realKey, filed);
    }

    /**
     * 实现命令：HDEL key field [field ...]，删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     *
     * @param key    键
     * @param fields 一个或多个字段
     */
    public void hdel(String key, Object... fields) {
        stringRedisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
     *
     * @param prefix 前缀
     * @param key    键
     * @param fields 一个或多个字段
     * @return 删除结果
     */
    public Long hdel(KeyPrefix prefix, String key, Object... fields) {
        String realKey = prefix.getPrefix() + key;
        return stringRedisTemplate.opsForHash().delete(realKey, fields);
    }

    /**
     * 实现命令：HGETALL key，返回哈希表 key中，所有的域和值。
     *
     * @param key 键
     * @return 结果
     */
    public Map<Object, Object> hgetall(String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取列表数值
     *
     * @param prefix 前缀
     * @param key    键
     * @return 结果
     */
    public Map<Object, Object> hvals(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        return stringRedisTemplate.opsForHash().entries(realKey);
    }

    // List（列表）

    /**
     * 实现命令：LPUSH key value，将一个值 value插入到列表 key的表头
     *
     * @param key   键
     * @param value 值
     * @return 结果 执行 LPUSH命令后，列表的长度。
     */
    public long lpush(String key, String value) {
        Long length = stringRedisTemplate.opsForList().leftPush(key, value);
        if (length != null) {
            return length;
        } else {
            return 0;
        }
    }

    /**
     * 实现命令：LPOP key，移除并返回列表 key的头元素。
     *
     * @param key 键
     * @return 结果 列表key的头元素。
     */
    public String lpop(String key) {
        return stringRedisTemplate.opsForList().leftPop(key);
    }

    /**
     * 实现命令：RPUSH key value，将一个值 value插入到列表 key的表尾(最右边)。
     *
     * @param key   键
     * @param value 值
     * @return 结果 执行 LPUSH命令后，列表的长度。
     */
    public long rpush(String key, String value) {
        Long length = stringRedisTemplate.opsForList().rightPush(key, value);
        if (length != null) {
            return length;
        } else {
            return 0;
        }
    }

    /**
     * 取出序列化的值
     *
     * @param body 序列化字节数组
     * @return 结果
     */
    public String getValueSerializer(byte[] body) {
        return (String) stringRedisTemplate.getValueSerializer().deserialize(body);
    }

    /**
     * 取出序列化的值
     *
     * @param channel 序列化字节数组
     * @return 结果
     */
    public String getStringSerializer(byte[] channel) {
        return stringRedisTemplate.getStringSerializer().deserialize(channel);
    }

    /**
     * 队列存值
     */
    public void convertAndSend(String key, String message) {
        stringRedisTemplate.convertAndSend(key, message);
    }
}
