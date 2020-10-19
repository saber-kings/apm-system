package cn.saberking.jvav.apm.web.domain;

import cn.saberking.jvav.apm.web.domain.common.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 购物车对象 apm_store_cart
 *
 * @author apm
 * @date 2020-10-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApmStoreCart extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * 购物车表ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 类型
     */
    private String type;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商品属性
     */
    private String productAttrUnique;

    /**
     * 商品数量
     */
    private Integer cartNum;

    /**
     * 购物车(0=未购买 1=已购买)
     */
    private Integer isPay;

    /**
     * 是否删除(0=存在 1=已删除)
     */
    private Integer isDel;

    /**
     * 是否为立即购买(0=不是 1=立即)
     */
    private Integer isNew;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("userId", getUserId())
                .append("type", getType())
                .append("productId", getProductId())
                .append("productAttrUnique", getProductAttrUnique())
                .append("cartNum", getCartNum())
                .append("createTime", getCreateTime())
                .append("isPay", getIsPay())
                .append("isDel", getIsDel())
                .append("isNew", getIsNew())
                .toString();
    }
}
