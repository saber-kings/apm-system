package cn.saberking.jvav.apm.web.enums;

import lombok.Getter;

/**
 * @author apm
 */

public enum ProductCategoryIsDel {
    /**
     * 商品已经被删除
     */
    YES("商品已经被删除", 1),
    /**
     * 商品未被删除
     */
    NO("商品未被删除", 0),
    ;
    @Getter
    private final String name;
    @Getter
    private final Integer code;

    ProductCategoryIsDel(String name, Integer code) {
        this.name = name;
        this.code = code;
    }
}
