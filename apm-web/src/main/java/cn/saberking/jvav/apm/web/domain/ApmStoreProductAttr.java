package cn.saberking.jvav.apm.web.domain;

import cn.saberking.jvav.apm.web.domain.common.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 商品属性对象 apm_store_product_attr
 *
 * @author apm
 * @date 2020-10-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApmStoreProductAttr extends BaseDomain {
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
     * 属性名
     */
    private String attrName;

    /**
     * 属性值
     */
    private String attrValues;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("productId", getProductId())
                .append("attrName", getAttrName())
                .append("attrValues", getAttrValues())
                .toString();
    }
}
