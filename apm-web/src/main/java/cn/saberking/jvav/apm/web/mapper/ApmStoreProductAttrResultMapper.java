package cn.saberking.jvav.apm.web.mapper;

import cn.saberking.jvav.apm.web.domain.ApmStoreProductAttrResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 商品属性详情Mapper接口
 *
 * @author apm
 * @date 2020-10-13
 */
@Mapper
public interface ApmStoreProductAttrResultMapper {
    /**
     * 查询商品属性详情
     *
     * @param id 商品属性详情ID
     * @return 商品属性详情
     */
    ApmStoreProductAttrResult selectApmStoreProductAttrResultById(Integer id);

    /**
     * 查询商品属性详情列表
     *
     * @param apmStoreProductAttrResult 商品属性详情
     * @return 商品属性详情集合
     */
    List<ApmStoreProductAttrResult> selectApmStoreProductAttrResultList(ApmStoreProductAttrResult apmStoreProductAttrResult);

    /**
     * 根据商品Id查询商品属性详情列表
     *
     * @param productId 商品Id
     * @return 商品属性详情集合
     */
    ApmStoreProductAttrResult selectListByProductId(Integer productId);

    /**
     * 新增商品属性详情
     *
     * @param apmStoreProductAttrResult 商品属性详情
     * @return 结果
     */
    int insertApmStoreProductAttrResult(ApmStoreProductAttrResult apmStoreProductAttrResult);

    /**
     * 修改商品属性详情
     *
     * @param apmStoreProductAttrResult 商品属性详情
     * @return 结果
     */
    int updateApmStoreProductAttrResult(ApmStoreProductAttrResult apmStoreProductAttrResult);

    /**
     * 删除商品属性详情
     *
     * @param id 商品属性详情ID
     * @return 结果
     */
    int deleteApmStoreProductAttrResultById(Integer id);

    /**
     * 批量删除商品属性详情
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteApmStoreProductAttrResultByIds(Integer[] ids);
}
