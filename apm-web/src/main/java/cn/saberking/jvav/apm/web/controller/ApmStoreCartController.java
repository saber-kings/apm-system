package cn.saberking.jvav.apm.web.controller;

import cn.saberking.jvav.apm.web.service.ApmStoreCartService;
import cn.saberking.jvav.apm.web.utils.DataResult;
import cn.saberking.jvav.apm.web.vo.StoreCartVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author saber-kings
 */
@RestController
@RequestMapping("/cart")
public class ApmStoreCartController {
    @Resource
    private ApmStoreCartService apmStoreCartService;

    /**
     * 加入购物车
     *
     * @param vo 参数
     * @return 结果
     */
    @PostMapping(value = "/")
    public DataResult<StoreCartVO> addCart(@RequestBody StoreCartVO vo) {
        //加入购物车
        return DataResult.success(apmStoreCartService.addCart(vo));
    }

    /**
     * 获取购物车列表
     *
     * @param userId 用户Id
     * @return 结果
     */
    @GetMapping(value = "/{userId}")
    public DataResult<List<StoreCartVO>> getCartList(@PathVariable String userId) {
        return DataResult.success(apmStoreCartService.getCartList(userId));
    }

    /**
     * 更新购物车商品数量
     *
     * @param vo 参数
     * @return 结果
     */
    @PutMapping(value = "/")
    public DataResult<Object> updateCartNum(@RequestBody StoreCartVO vo) {
        apmStoreCartService.updateCartNum(vo);
        return DataResult.success();
    }

    /**
     * 全选
     *
     * @param vo 参数
     * @return 结果
     */
    @PutMapping(value = "/checkAll")
    public DataResult<Object> checkAll(@RequestBody StoreCartVO vo) {
        apmStoreCartService.checkAll(vo.getUserId(), vo.getCheck());
        return DataResult.success();
    }

    /**
     * 删除勾选商品
     *
     * @param vo 参数
     * @return 结果
     */
    @DeleteMapping("/")
    public DataResult<Object> delCartProduct(@RequestBody StoreCartVO vo) {
        apmStoreCartService.delCartProduct(vo);
        return DataResult.success();
    }

    /**
     * 批量删除勾选商品
     *
     * @param vo 参数
     * @return 结果
     */
    @DeleteMapping("/batch")
    public DataResult<Object> delCartProducts(@RequestBody StoreCartVO vo) {
        apmStoreCartService.delCartProducts(vo);
        return DataResult.success();
    }

    /**
     * 清空购物车
     *
     * @param vo 参数
     * @return 结果
     */
    @DeleteMapping("/delCart")
    public DataResult<Object> delCart(@RequestBody StoreCartVO vo) {
        apmStoreCartService.delCart(vo.getUserId());
        return DataResult.success();
    }

}
