package cn.saberking.jvav.apm.web.mapper;


import cn.saberking.jvav.apm.web.domain.ApmStoreProductAttrValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性值Mapper接口
 *
 * @author apm
 * @date 2020-10-11
 */
@Mapper
public interface ApmStoreProductAttrValueMapper {
    /**
     * 查询商品属性值
     *
     * @param id 商品属性值ID
     * @return 商品属性值
     */
    ApmStoreProductAttrValue selectApmStoreProductAttrValueById(Integer id);

    /**
     * 查询商品属性值列表
     *
     * @param apmStoreProductAttrValue 商品属性值
     * @return 商品属性值集合
     */
    List<ApmStoreProductAttrValue> selectApmStoreProductAttrValueList(ApmStoreProductAttrValue apmStoreProductAttrValue);

    /**
     * 根据商品Id查询商品属性值列表
     * @param productId 商品Id
     * @return 商品属性值集合
     */
    List<ApmStoreProductAttrValue> queryByProductId(Integer productId);

    /**
     * 根据商品Id和属性值组合查询商品属性值
     * @param pid 商品Id
     * @param suk 商品属性值
     * @return 商品属性值集合
     */
    ApmStoreProductAttrValue queryByPidAndSuck(@Param("pid") Integer pid, @Param("suk") String suk);

    /**
     * 根据商品Id和属性值组合查询商品属性值
     * @param pid 商品Id
     * @param suk 商品属性值
     * @return 商品属性值集合
     */
    ApmStoreProductAttrValue queryByPidAndSuck2(@Param("pid") Integer pid, @Param("suk") String suk);

    /**
     * 新增商品属性值
     *
     * @param apmStoreProductAttrValue 商品属性值
     * @return 结果
     */
    int insertApmStoreProductAttrValue(ApmStoreProductAttrValue apmStoreProductAttrValue);

    /**
     * 修改商品属性值
     *
     * @param apmStoreProductAttrValue 商品属性值
     * @return 结果
     */
    int updateApmStoreProductAttrValue(ApmStoreProductAttrValue apmStoreProductAttrValue);

    /**
     * 删除商品属性值
     *
     * @param id 商品属性值ID
     * @return 结果
     */
    int deleteApmStoreProductAttrValueById(Integer id);

    /**
     * 批量删除商品属性值
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteApmStoreProductAttrValueByIds(Integer[] ids);
}
