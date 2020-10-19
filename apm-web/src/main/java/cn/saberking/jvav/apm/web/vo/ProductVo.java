package cn.saberking.jvav.apm.web.vo;

import cn.saberking.jvav.apm.web.domain.ApmStoreProduct;
import cn.saberking.jvav.apm.web.domain.ApmStoreProductAttrValue;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductVo extends ApmStoreProduct {
    private List<ApmStoreProductAttrValue> list;
}
