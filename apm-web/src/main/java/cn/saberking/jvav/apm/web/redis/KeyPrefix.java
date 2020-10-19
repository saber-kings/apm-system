package cn.saberking.jvav.apm.web.redis;

/**
 * @author saber-kings
 */
public interface KeyPrefix {
    /**
     * 有效期
     *
     * @return 有效期
     */
    int expireSeconds();

    /**
     * key前缀，防止其他的人使用redis时覆盖
     *
     * @return key 前缀
     */
    String getPrefix();
}
