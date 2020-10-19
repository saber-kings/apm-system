package cn.saberking.jvav.apm.web.enums;

import lombok.Getter;

/**
 * @author saber-kings
 */
public enum ProductCategoryIsShow {
    /**
     * 推荐商品
     */
    YES("推荐商品", 1),
    /**
     * 非推荐删除
     */
    NO("非推荐删除", 0),
    ;
    @Getter
    private final String name;
    @Getter
    private final Integer code;

    ProductCategoryIsShow(String name, Integer code) {
        this.name = name;
        this.code = code;
    }
}
