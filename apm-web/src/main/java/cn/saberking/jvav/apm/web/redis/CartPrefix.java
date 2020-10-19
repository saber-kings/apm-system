package cn.saberking.jvav.apm.web.redis;

/**
 * @author apm
 */
public class CartPrefix extends BasePrefix{

    public CartPrefix(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 设置购物车缓存
     */
    public static CartPrefix getCartList= new CartPrefix(0,"cart_");
}
