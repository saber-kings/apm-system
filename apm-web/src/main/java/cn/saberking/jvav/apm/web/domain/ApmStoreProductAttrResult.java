package cn.saberking.jvav.apm.web.domain;

import cn.saberking.jvav.apm.web.domain.common.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 商品属性详情对象 apm_store_product_attr_result
 *
 * @author apm
 * @date 2020-10-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApmStoreProductAttrResult extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 商品ID
     */
    private Integer productId;

    /**
     * 商品属性参数
     */
    private String result;

    /**
     * 上次修改时间
     */
    private Integer changeTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("productId", getProductId())
                .append("result", getResult())
                .append("changeTime", getChangeTime())
                .toString();
    }
}
