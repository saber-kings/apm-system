package cn.saberking.jvav.apm.web.mapper;


import cn.saberking.jvav.apm.web.domain.ApmStoreProduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品Mapper接口
 *
 * @author apm
 * @date 2020-10-06
 */
@Mapper
public interface ApmStoreProductMapper {
    /**
     * 查询商品
     *
     * @param id 商品ID
     * @return 商品
     */
    ApmStoreProduct selectApmStoreProductById(Integer id);

    /**
     * 查询商品和商品属性
     *
     * @param id 商品ID
     * @return 商品
     */
    ApmStoreProduct selectProductInfoById(Integer id);

    /**
     * 查询商品列表
     *
     * @param apmStoreProduct 商品
     * @return 商品集合
     */
    List<ApmStoreProduct> selectApmStoreProductList(ApmStoreProduct apmStoreProduct);

    /**
     * 新增商品
     *
     * @param apmStoreProduct 商品
     * @return 结果
     */
    int insertApmStoreProduct(ApmStoreProduct apmStoreProduct);

    /**
     * 修改商品
     *
     * @param apmStoreProduct 商品
     * @return 结果
     */
    int updateApmStoreProduct(ApmStoreProduct apmStoreProduct);

    /**
     * 删除商品
     *
     * @param id 商品ID
     * @return 结果
     */
    int deleteApmStoreProductById(Integer id);

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteApmStoreProductByIds(Integer[] ids);
}
