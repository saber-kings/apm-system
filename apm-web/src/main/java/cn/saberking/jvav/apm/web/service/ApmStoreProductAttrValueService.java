package cn.saberking.jvav.apm.web.service;

import cn.saberking.jvav.apm.web.domain.ApmStoreProductAttrValue;

import java.util.List;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/12
 */
public interface ApmStoreProductAttrValueService {
    /**
     * 根据商品id查询商品属性
     * @param productId 商品Id
     * @return
     */
    List<ApmStoreProductAttrValue> queryByProductId(Integer productId);
}
