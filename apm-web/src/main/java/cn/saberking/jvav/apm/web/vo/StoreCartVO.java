package cn.saberking.jvav.apm.web.vo;

import cn.saberking.jvav.apm.web.utils.text.Convert;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author saber-kings
 */
@Data
public class StoreCartVO implements Serializable {
    /**
     * 用户Id
     */
    private String userId;

    /**
     * 商品Id
     */
    private String productId;

    @JsonIgnore
    private String[] productIds;

    @JSONField(serialize=false)
    public String[] getProductIds() {
        return Convert.toStrArray(",", productId);
    }

    /**
     * 商品名称
     */
    private String storeName;

    /**
     * 商品属性索引值 (attr_value|attr_value[|....])
     */
    private String suk;

    /**
     * 商品属性索引值 (attr_value|attr_value[|....])
     */
    @JsonIgnore
    private List<String> keys;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 订单商品总数
     */
    private Integer totalNum;

    /**
     * 是否勾选
     */
    private String check;

    /**
     * 状态（0：未上架，1：上架）
     */
    private Integer isShow;

    /**
     * 商品小图
     */
    private String productIcon;

    /**
     * 购物车(0=未购买 1=已购买)
     */
    @JsonIgnore
    private Integer isPay;

    /**
     * 是否为立即购买(0=不是 1=立即)
     */
    @JsonIgnore
    private Integer isNew;
}
