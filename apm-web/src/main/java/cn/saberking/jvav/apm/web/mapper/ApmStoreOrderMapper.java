package cn.saberking.jvav.apm.web.mapper;


import cn.saberking.jvav.apm.web.domain.ApmStoreOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单Mapper接口
 *
 * @author apm
 * @date 2020-10-06
 */
@Mapper
public interface ApmStoreOrderMapper {
    /**
     * 查询订单
     *
     * @param id 订单ID
     * @return 订单
     */
    ApmStoreOrder selectApmStoreOrderById(Integer id);

    /**
     * 查询订单
     *
     * @param orderId 订单编号
     * @return 订单
     */
    ApmStoreOrder selectApmStoreOrderByOrderId(String orderId);

    /**
     * 根据订单Id查询订单购物详情
     * @param oid 订单Id
     * @return 结果
     */
    String selectCartInfoByOid(Integer oid);

    /**
     * 查询订单列表
     *
     * @param apmStoreOrder 订单
     * @return 订单集合
     */
    List<ApmStoreOrder> selectApmStoreOrderList(ApmStoreOrder apmStoreOrder);

    /**
     * 新增订单
     *
     * @param apmStoreOrder 订单
     * @return 结果
     */
    int insertApmStoreOrder(ApmStoreOrder apmStoreOrder);

    /**
     * 修改订单
     *
     * @param apmStoreOrder 订单
     * @return 结果
     */
    int updateApmStoreOrder(ApmStoreOrder apmStoreOrder);

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 结果
     */
    int deleteApmStoreOrderById(Integer id);

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteApmStoreOrderByIds(Integer[] ids);

    /**
     * 逻辑批量删除订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletedApmStoreOrderByIds(Integer[] ids);

    /**
     * 根据用户id获取订单列表
     *
     * @param userId 用户Id
     * @return 结果
     */
    List<ApmStoreOrder> queryOrderByUserId(Integer userId);

    /**
     * 取消订单
     * @param storeOrder 订单
     * @return 结果
     */
    int cancelOrder(ApmStoreOrder storeOrder);
}
