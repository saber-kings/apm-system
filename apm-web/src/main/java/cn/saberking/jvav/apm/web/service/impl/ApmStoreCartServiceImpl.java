package cn.saberking.jvav.apm.web.service.impl;

import cn.saberking.jvav.apm.web.constants.Constants;
import cn.saberking.jvav.apm.web.domain.ApmStoreCart;
import cn.saberking.jvav.apm.web.domain.ApmStoreProductAttrValue;
import cn.saberking.jvav.apm.web.exception.BusinessException;
import cn.saberking.jvav.apm.web.exception.code.BaseResponseCode;
import cn.saberking.jvav.apm.web.mapper.ApmStoreCartMapper;
import cn.saberking.jvav.apm.web.mapper.ApmStoreProductAttrValueMapper;
import cn.saberking.jvav.apm.web.redis.CartPrefix;
import cn.saberking.jvav.apm.web.redis.RedisClient;
import cn.saberking.jvav.apm.web.service.ApmStoreCartService;
import cn.saberking.jvav.apm.web.utils.CastUtils;
import cn.saberking.jvav.apm.web.utils.DateUtils;
import cn.saberking.jvav.apm.web.vo.StoreCartVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author saber-kings
 */
@Service
@Slf4j
public class ApmStoreCartServiceImpl implements ApmStoreCartService {
    @Resource
    private RedisClient redisClient;

    @Resource
    private ApmStoreProductAttrValueMapper attrValueMapper;

    @Resource
    private ApmStoreCartMapper cartMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApmStoreCart saveCart(StoreCartVO vo) {
        dataNotNull(vo.getUserId(), vo.getProductId(), vo.getSuk(), vo.getTotalNum());
        //判断库存是否足够
        ApmStoreProductAttrValue attrValue = judgeStock(vo);
        ApmStoreCart cart = new ApmStoreCart();
        cart.setUserId(Integer.parseInt(vo.getUserId()));
        cart.setProductId(attrValue.getProductId());
        cart.setCartNum(vo.getTotalNum());
        cart.setProductAttrUnique(attrValue.getSuk());
        cart.setCreateTime(DateUtils.getNowDate());
        cart.setIsPay(1);
        int i = cartMapper.insertApmStoreCart(cart);
        if (i == 0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        return cart;
    }

    /**
     * 判断库存的方法
     * @param vo 参数
     * @return 结果
     */
    private ApmStoreProductAttrValue judgeStock(StoreCartVO vo) {
        ApmStoreProductAttrValue attrValue = attrValueMapper.queryByPidAndSuck2(Integer.parseInt(vo.getProductId()), vo.getSuk());
        //根据商品id和商品属性值组合获取商品
        if (attrValue == null) {
            throw new BusinessException(BaseResponseCode.CART_GOOD_SUK_NOT_FOUND);
        }
        if (attrValue.getStock() <= vo.getTotalNum()) {
            throw new BusinessException(BaseResponseCode.CART_GOOD_NOT_FOUND);
        }
        return attrValue;
    }

    /**
     * 加入购物车
     *
     * @param vo@return 结果
     */
    @Override
    public StoreCartVO addCart(StoreCartVO vo) {
        dataNotNull(vo.getUserId(), vo.getProductId(), vo.getSuk(), vo.getTotalNum());
        StoreCartVO storeCartVO;

        //判断库存是否足够
        ApmStoreProductAttrValue attrValue = judgeStock(vo);

        String key = getKey(vo);
        //获取现有的购物车中的数据
        String json = redisClient.hget(CartPrefix.getCartList, vo.getUserId(), key);
        //key为 userId_cart,校验是否已存在
        if (json != null) {
            //转换为java实体类
            storeCartVO = JSON.toJavaObject(JSONObject.parseObject(json), StoreCartVO.class);
            storeCartVO.setTotalNum(storeCartVO.getTotalNum() + vo.getTotalNum());
        } else {
            //设置购物车值
            storeCartVO = new StoreCartVO();
            storeCartVO.setProductId(vo.getProductId());
            storeCartVO.setStoreName(attrValue.getStoreName());
            storeCartVO.setProductIcon(attrValue.getImage());
            storeCartVO.setPrice(attrValue.getPrice());
            storeCartVO.setIsShow(attrValue.getIsShow());
            storeCartVO.setTotalNum(vo.getTotalNum());
            storeCartVO.setCheck("1");
        }
        redisClient.hset(CartPrefix.getCartList, vo.getUserId(), key, JSON.toJSON(storeCartVO).toString());
        return storeCartVO;
    }

    /**
     * 根据产品Id和产品属性值组合得到Key
     *
     * @param vo 参数
     * @return key
     */
    private String getKey(StoreCartVO vo) {
        StringBuilder sb = new StringBuilder();
        String[] sucks = vo.getSuk().split(",");
        sb.append(vo.getProductId());
        for (String suck : sucks) {
            sb.append("_").append(suck);
        }
        return sb.toString();
    }

    /**
     * 展示购物车
     *
     * @param userId 用户ID
     * @return 购物车商品列表
     */
    @Override
    public List<StoreCartVO> getCartList(String userId) {
        dataNotNull(userId);
        Map<Object, Object> jsonMap = redisClient.hvals(CartPrefix.getCartList, userId);
        List<Object> list = new ArrayList<>(jsonMap.values());
        List<String> jsonList = CastUtils.castList(list, String.class);
        List<StoreCartVO> cartDtoList = new LinkedList<>();
        for (String json : jsonList) {
            StoreCartVO storeCartVO = JSON.toJavaObject(JSONObject.parseObject(json), StoreCartVO.class);
            cartDtoList.add(storeCartVO);
        }
        return cartDtoList;
    }

    /**
     * 更新数量
     *
     * @param vo 参数
     */
    @Override
    public void updateCartNum(StoreCartVO vo) {
        dataNotNull(vo.getUserId(), vo.getProductId(), vo.getSuk(), vo.getTotalNum());
        String key = getKey(vo);
        String json = redisClient.hget(CartPrefix.getCartList, vo.getUserId(), key);
        if (json == null) {
            throw new BusinessException(BaseResponseCode.CART_GOOD_NOT_FOUND);
        }
        StoreCartVO cartDto = JSON.toJavaObject(JSONObject.parseObject(json), StoreCartVO.class);
        cartDto.setTotalNum(vo.getTotalNum());
        redisClient.hset(CartPrefix.getCartList, vo.getUserId(), key, JSON.toJSON(cartDto).toString());
    }

    /**
     * 全选商品
     *
     * @param userId  用户ID
     * @param checked 是否勾选
     */
    @Override
    public void checkAll(String userId, String checked) {
        dataNotNull(userId, checked);
        //获取商品列表
        Map<Object, Object> values = redisClient.hvals(CartPrefix.getCartList, userId);
        List<String> jsonList = CastUtils.castList(new ArrayList<>(values.values()), String.class);
        String isCheck;
        if (Constants.BOOLEAN_TRUE.equals(checked)) {
            isCheck = "1";
        } else if (Constants.BOOLEAN_FALSE.equals(checked)) {
            isCheck = "0";
        } else {
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        for (String json : jsonList) {
            StoreCartVO storeCartVO = JSON.toJavaObject(JSONObject.parseObject(json), StoreCartVO.class);
            storeCartVO.setCheck(isCheck);
            redisClient.hset(CartPrefix.getCartList, userId, storeCartVO.getProductId(), JSON.toJSON(storeCartVO).toString());
        }
    }

    /**
     * 删除商品
     *
     * @param vo 参数
     */
    @Override
    public void delCartProduct(StoreCartVO vo) {
        dataNotNull(vo.getUserId(), vo.getProductId(), vo.getSuk());
        String key = getKey(vo);
        Long hdel = redisClient.hdel(CartPrefix.getCartList, vo.getUserId(), key);
        if (hdel == 0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    private void dataNotNull(Object... params) {
        for (Object param : params) {
            if (param == null) {
                throw new BusinessException(BaseResponseCode.DATA_NOT_NULL);
            }
        }
    }

    /**
     * 删除勾选商品
     *
     * @param vo 参数
     */
    @Override
    public void delCartProducts(StoreCartVO vo) {
        dataNotNull(vo.getUserId(), vo.getKeys());
        Long hdel = 0L;
        for (String key : vo.getKeys()) {
            hdel += redisClient.hdel(CartPrefix.getCartList, vo.getUserId(), key);
        }
        if (hdel == 0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    /**
     * 清空购物车
     *
     * @param userId 用户ID
     */
    @Override
    public void delCart(String userId) {
        dataNotNull(userId);
        boolean del = redisClient.del(CartPrefix.getCartList, userId);
        if (!del) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }
}
