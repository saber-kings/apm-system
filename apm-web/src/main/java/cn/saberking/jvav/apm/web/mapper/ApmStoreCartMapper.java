package cn.saberking.jvav.apm.web.mapper;

import cn.saberking.jvav.apm.web.domain.ApmStoreCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 购物车Mapper接口
 *
 * @author apm
 * @date 2020-10-14
 */
@Mapper
public interface ApmStoreCartMapper {
    /**
     * 查询购物车
     *
     * @param id 购物车ID
     * @return 购物车
     */
    ApmStoreCart selectApmStoreCartById(Long id);

    /**
     * 查询购物车列表
     *
     * @param apmStoreCart 购物车
     * @return 购物车集合
     */
    List<ApmStoreCart> selectApmStoreCartList(ApmStoreCart apmStoreCart);

    /**
     * 新增购物车
     *
     * @param apmStoreCart 购物车
     * @return 结果
     */
    int insertApmStoreCart(ApmStoreCart apmStoreCart);

    /**
     * 修改购物车
     *
     * @param apmStoreCart 购物车
     * @return 结果
     */
    int updateApmStoreCart(ApmStoreCart apmStoreCart);

    /**
     * 删除购物车
     *
     * @param id 购物车ID
     * @return 结果
     */
    int deleteApmStoreCartById(Long id);

    /**
     * 批量删除购物车
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteApmStoreCartByIds(Long[] ids);
}
