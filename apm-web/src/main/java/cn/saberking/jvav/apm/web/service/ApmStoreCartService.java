package cn.saberking.jvav.apm.web.service;

import cn.saberking.jvav.apm.web.domain.ApmStoreCart;
import cn.saberking.jvav.apm.web.vo.StoreCartVO;

import java.util.List;

/**
 * @author saber-kings
 */
public interface ApmStoreCartService {

    /**
     * 保存购物车
     *
     * @param vo 参数
     * @return 结果
     */
    ApmStoreCart saveCart(StoreCartVO vo);

    /**
     * 加入购物车
     *
     * @param vo 参数
     * @return 结果
     */
    StoreCartVO addCart(StoreCartVO vo);

    /**
     * 购物车列表
     *
     * @param userId 用户Id
     * @return 购物车商品列表
     */
    List<StoreCartVO> getCartList(String userId);

    /**
     * 修改数量
     *
     * @param vo 参数
     */
    void updateCartNum(StoreCartVO vo);

    /**
     * 全选
     *
     * @param userId  用户Id
     * @param checked 是否全选
     */
    void checkAll(String userId, String checked);

    /**
     * 删除勾选商品
     *
     * @param vo 参数
     */
    void delCartProduct(StoreCartVO vo);

    /**
     * 删除勾选商品
     *
     * @param vo 参数
     */
    void delCartProducts(StoreCartVO vo);

    /**
     * 删除购物车
     *
     * @param userId 用户Id
     */
    void delCart(String userId);
}
