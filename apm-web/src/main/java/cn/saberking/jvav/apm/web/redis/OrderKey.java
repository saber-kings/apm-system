package cn.saberking.jvav.apm.web.redis;

/**
 * @author saber-kings
 */
public class OrderKey extends BasePrefix {
    public OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getSeckillOrderByUidGid = new OrderKey("seckill");
}
