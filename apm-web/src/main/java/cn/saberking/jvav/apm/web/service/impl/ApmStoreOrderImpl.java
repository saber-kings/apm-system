package cn.saberking.jvav.apm.web.service.impl;

import cn.saberking.jvav.apm.web.domain.ApmStoreCart;
import cn.saberking.jvav.apm.web.domain.ApmStoreOrder;
import cn.saberking.jvav.apm.web.exception.BusinessException;
import cn.saberking.jvav.apm.web.exception.code.BaseResponseCode;
import cn.saberking.jvav.apm.web.mapper.ApmStoreOrderMapper;
import cn.saberking.jvav.apm.web.service.ApmStoreCartService;
import cn.saberking.jvav.apm.web.service.ApmStoreOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/10
 */
@Service
public class ApmStoreOrderImpl implements ApmStoreOrderService {
    @Resource
    private ApmStoreOrderMapper orderMapper;

    @Resource
    private ApmStoreCartService cartService;

    /**
     * 根据用户id查询订单列表
     *
     * @param userId 用户Id
     * @return 结果
     */
    @Override
    public List<ApmStoreOrder> queryOrderByUserId(Integer userId) {
        return orderMapper.queryOrderByUserId(userId);
    }

    /**
     * 根据用户id查询订单列表以及商品列表
     *
     * @param userId 用户Id
     * @return 结果
     */
    @Override
    public List<ApmStoreOrder> queryByUserId(Integer userId) {
        List<ApmStoreOrder> orders = orderMapper.queryOrderByUserId(userId);
        if (orders != null && orders.size() != 0) {
            for (ApmStoreOrder order : orders) {
                String cartInfo = orderMapper.selectCartInfoByOid(order.getId());
                order.setCartInfo(cartInfo);
            }
        }
        return orders;
    }

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderId 订单Id
     * @return 结果
     */
    @Override
    public ApmStoreOrder queryByOrderId(String orderId) {
        ApmStoreOrder order = orderMapper.selectApmStoreOrderByOrderId(orderId);
        if (order != null) {
            String cartInfo = orderMapper.selectCartInfoByOid(order.getId());
            order.setCartInfo(cartInfo);
        }
        return order;
    }

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderId 订单Id
     * @return 结果
     */
    @Override
    public ApmStoreOrder queryOrderByOrderId(String orderId) {
        return orderMapper.selectApmStoreOrderByOrderId(orderId);
    }

    /**
     * 添加订单
     *
     * @param apmStoreOrder 订单
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApmStoreOrder insertApmStoreOrder(ApmStoreOrder apmStoreOrder) {
        int i = orderMapper.insertApmStoreOrder(apmStoreOrder);
        if (i == 0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        return apmStoreOrder;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCart(ApmStoreOrder apmStoreOrder) {
        ApmStoreCart cart = cartService.saveCart(apmStoreOrder.getCart());
        if (cart == null) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        apmStoreOrder.setCartId(cart.getId().toString());
        int i = orderMapper.updateApmStoreOrder(apmStoreOrder);
        if (i==0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    /**
     * 取消订单
     *
     * @param storeOrder 订单
     * @return 结果
     */
    @Override
    public ApmStoreOrder cancelOrder(ApmStoreOrder storeOrder) {
        System.out.println(storeOrder + "///");
        storeOrder.setStatus(4);
        storeOrder.setIsDel(1);
        int i = orderMapper.cancelOrder(storeOrder);
        if (i == 0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        return storeOrder;
    }
}
