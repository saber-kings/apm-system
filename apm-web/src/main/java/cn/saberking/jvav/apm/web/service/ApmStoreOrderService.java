package cn.saberking.jvav.apm.web.service;

import cn.saberking.jvav.apm.web.domain.ApmStoreOrder;

import java.util.List;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/10
 */
public interface ApmStoreOrderService {
    /**
     * 根据用户id获取订单列表
     *
     * @param userId 用户Id
     * @return 结果
     */
    List<ApmStoreOrder> queryOrderByUserId(Integer userId);

    /**
     * 根据用户id查询订单列表以及商品列表
     *
     * @param userId 用户Id
     * @return 结果
     */
    List<ApmStoreOrder> queryByUserId(Integer userId);

    /**
     * 根据订单编号查询订单和商品列表
     *
     * @param orderId 订单编号
     * @return 结果
     */
    ApmStoreOrder queryByOrderId(String orderId);

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderId 订单Id
     * @return 结果
     */
    ApmStoreOrder queryOrderByOrderId(String orderId);

    /**
     * 新增商品订单
     *
     * @param apmStoreOrder 订单
     * @return 结果
     */
    ApmStoreOrder insertApmStoreOrder(ApmStoreOrder apmStoreOrder);

    /**
     * 保存购物车
     * @param apmStoreOrder 订单
     */
    void saveCart(ApmStoreOrder apmStoreOrder);

    /**
     * 取消订单
     *
     * @param storeOrder 订单
     * @return 结果
     */
    ApmStoreOrder cancelOrder(ApmStoreOrder storeOrder);
}
