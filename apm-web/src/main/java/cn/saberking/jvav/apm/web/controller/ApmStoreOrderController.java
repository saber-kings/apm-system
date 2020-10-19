package cn.saberking.jvav.apm.web.controller;

import cn.saberking.jvav.apm.web.domain.ApmStoreOrder;
import cn.saberking.jvav.apm.web.service.ApmStoreOrderService;
import cn.saberking.jvav.apm.web.utils.DataResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单处理控制器
 *
 * @author wyx
 * @version 1.0
 * @date 2020/10/10
 */
@RestController
@RequestMapping("/order")
public class ApmStoreOrderController {

    @Resource
    private ApmStoreOrderService orderService;

    /**
     * 添加订单
     *
     * @param storeOrder 订单
     * @return 结果
     */
    @PostMapping("/")
    public DataResult<ApmStoreOrder> insertOrder(@RequestBody ApmStoreOrder storeOrder) {
        return DataResult.success(orderService.insertApmStoreOrder(storeOrder));
    }

    /**
     * 根据用户id查询订单列表
     *
     * @param storeOrder 订单
     * @return 结果
     */
    @GetMapping("/")
    public DataResult<List<ApmStoreOrder>> queryOrderListByUserId(ApmStoreOrder storeOrder) {
        Integer userId = storeOrder.getUserId();
        return DataResult.success(orderService.queryOrderByUserId(userId));
    }

    /**
     * 根据用户id查询订单列表以及商品列表
     *
     * @param userId 用户Id
     * @return 结果
     */
    @GetMapping("/{userId}")
    public DataResult<List<ApmStoreOrder>> query(@PathVariable Integer userId) {
        return DataResult.success(orderService.queryByUserId(userId));
    }


    /**
     * 根据订单编号查询订单
     *
     * @param orderId 订单id
     * @return 结果
     */
    @GetMapping("/order/{orderId}")
    public DataResult<ApmStoreOrder> order(@PathVariable String orderId) {
        return DataResult.success(orderService.queryOrderByOrderId(orderId));
    }

    /**
     * 根据订单编号查询订单
     *
     * @param orderId 订单编号
     * @return 结果
     */
    @GetMapping("/orderGood/{orderId}")
    public DataResult<ApmStoreOrder> orderGood(@PathVariable String orderId) {
        return DataResult.success(orderService.queryByOrderId(orderId));
    }


    /**
     * 取消订单
     *
     * @param storeOrder 订单
     * @return 结果
     */
    @PutMapping("/cancel")
    public DataResult<ApmStoreOrder> cancel(ApmStoreOrder storeOrder) {
        return DataResult.success(orderService.cancelOrder(storeOrder));
    }
}
