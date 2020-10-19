package cn.saberking.jvav.apm.web.service.impl;

import cn.saberking.jvav.apm.web.domain.ApmStoreProductAttrValue;
import cn.saberking.jvav.apm.web.mapper.ApmStoreProductAttrValueMapper;
import cn.saberking.jvav.apm.web.service.ApmStoreProductAttrValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Saber污妖王
 * TODO: 类文件简单描述
 * @version 0.0.1
 * @editor Saber污妖王
 * @project apm-web
 * @date 2020/10/12
 * @package cn.saberking.jvav.apm.web.service.impl
 */
@Service
public class ApmStoreProductAttrValueServiceImpl implements ApmStoreProductAttrValueService {
    @Resource
    private ApmStoreProductAttrValueMapper attrValueMapper;

    @Override
    public List<ApmStoreProductAttrValue> queryByProductId(Integer productId) {
        ApmStoreProductAttrValue attrValue = new ApmStoreProductAttrValue();
        attrValue.setProductId(productId);
        return attrValueMapper.selectApmStoreProductAttrValueList(attrValue);
    }
}
