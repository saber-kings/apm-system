package cn.saberking.jvav.apm.web.service;

import cn.saberking.jvav.apm.web.domain.ApmStoreCategory;
import cn.saberking.jvav.apm.web.domain.ApmStoreProduct;
import cn.saberking.jvav.apm.web.vo.PageRespVO;

import java.util.List;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/11
 */
public interface ApmStoreProductService {
    /**
     * 根据商品id查询商品（用户查看商品细节）
     *
     * @param id 商品ID
     * @return 结果
     */
    ApmStoreProduct selectById(Integer id);

    /**
     * 查询商品列表
     *
     * @param apmStoreProduct 商品
     * @return 商品集合
     */
    PageRespVO<ApmStoreProduct> selectProductList(ApmStoreProduct apmStoreProduct);

    /**
     * 获取首页各种分类的推荐商品数据
     * @param pageSize 商品数量
     * @return 结果
     */
    List<ApmStoreCategory> selectShowProduct(Integer pageSize);

    /**
     * 新增商品
     *
     * @param apmStoreProduct 商品
     * @return 结果
     */
    ApmStoreProduct add(ApmStoreProduct apmStoreProduct);

    /**
     * 修改商品
     *
     * @param apmStoreProduct 商品
     * @return 结果
     */
    int update(ApmStoreProduct apmStoreProduct);

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的商品ID
     * @return 结果
     */
    int deleteByIds(Integer[] ids);

    /**
     * 删除商品信息
     *
     * @param id 商品ID
     * @return 结果
     */
    int deleteProductById(Integer id);
}
