package cn.saberking.jvav.apm.web.redis;

/**
 * @author apm
 */
public abstract class BasePrefix implements KeyPrefix {
    private final int expireSeconds;

    private final String prefix;

    /**
     * 0表示不过期
     * @param prefix 前缀
     */
    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        //拿到参数类类名
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}
