package cn.saberking.jvav.apm.web.domain;

import cn.saberking.jvav.apm.web.domain.common.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 订单购物详情对象 apm_store_order_cart_info
 *
 * @author apm
 * @date 2020-10-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApmStoreOrderCartInfo extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 购物车id
     */
    private Integer cartId;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 购买东西的详细信息
     */
    private String cartInfo;

    /**
     * 唯一id
     */
    private String unique;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("cartId", getCartId())
                .append("productId", getProductId())
                .append("cartInfo", getCartInfo())
                .append("unique", getUnique())
                .toString();
    }
}
