package cn.saberking.jvav.apm.web.mapper;


import cn.saberking.jvav.apm.web.domain.ApmStoreCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品分类Mapper接口
 *
 * @author apm
 * @date 2020-10-06
 */
@Mapper
public interface ApmStoreCategoryMapper {
    /**
     * 查询商品分类
     *
     * @param id 商品分类ID
     * @return 商品分类
     */
    ApmStoreCategory selectApmStoreCategoryById(Integer id);

    /**
     * 查询商品分类列表
     *
     * @param apmStoreCategory 商品分类
     * @return 商品分类集合
     */
    List<ApmStoreCategory> selectApmStoreCategoryList(ApmStoreCategory apmStoreCategory);

    /**
     * 新增商品分类
     *
     * @param apmStoreCategory 商品分类
     * @return 结果
     */
    int insertApmStoreCategory(ApmStoreCategory apmStoreCategory);

    /**
     * 修改商品分类
     *
     * @param apmStoreCategory 商品分类
     * @return 结果
     */
    int updateApmStoreCategory(ApmStoreCategory apmStoreCategory);

    /**
     * 删除商品分类
     *
     * @param id 商品分类ID
     * @return 结果
     */
    int deleteApmStoreCategoryById(Integer id);

    /**
     * 批量删除商品分类
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteApmStoreCategoryByIds(Integer[] ids);
}
