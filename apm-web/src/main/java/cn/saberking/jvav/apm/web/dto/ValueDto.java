package cn.saberking.jvav.apm.web.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 所有属性值组合的的实体
 * @author saber-kings
 */
@Data
public class ValueDto {
    /**
     * 成本价
     */
    private BigDecimal cost;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 每一种组合的商品缩略图url
     */
    private String pic;

    /**
     * 每一种组合的详情
     */
    private Map<String, Object> detail;

    /**
     * 是否选中
     */
    private Boolean check;

    /**
     * 销量
     */
    private Integer sales;
}
