package cn.saberking.jvav.apm.web.domain;

import cn.saberking.jvav.apm.web.domain.common.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 商品属性值对象 apm_store_product_attr_value
 *
 * @author apm
 * @date 2020-10-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApmStoreProductAttrValue extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Integer id;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商品属性索引值 (attr_value|attr_value[|....])
     */
    private String suk;

    /**
     * 属性对应的库存
     */
    private Integer stock;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 属性金额
     */
    private BigDecimal price;

    /**
     * 图片
     */
    private String image;

    /**
     * 唯一值
     */
    private String unique;

    /**
     * 成本价
     */
    private BigDecimal cost;

    /**
     * 商品名称
     */
    private String storeName;

    /**
     * 状态（0：未上架，1：上架）
     */
    private Integer isShow;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("productId", getProductId())
                .append("suk", getSuk())
                .append("stock", getStock())
                .append("sales", getSales())
                .append("price", getPrice())
                .append("image", getImage())
                .append("unique", getUnique())
                .append("cost", getCost())
                .toString();
    }
}
