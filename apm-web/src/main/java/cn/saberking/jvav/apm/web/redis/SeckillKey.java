package cn.saberking.jvav.apm.web.redis;

/**
 * @author saber-kings
 */
public class SeckillKey extends BasePrefix {
    private SeckillKey(String prefix) {
        super(prefix);
    }

    public static SeckillKey isGoodsOver = new SeckillKey("go");
}
