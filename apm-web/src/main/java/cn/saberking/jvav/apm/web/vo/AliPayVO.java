package cn.saberking.jvav.apm.web.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Saber污妖王
 * TODO: 类文件简单描述
 * @version 0.0.1
 * @editor Saber污妖王
 * @project apm-web
 * @date 2020/10/13
 * @package cn.saberking.jvav.apm.web.vo
 */
@Data
public class AliPayVO {
    /**
     * 商品名称
     */
    private String storeName;
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 支付金额
     */
    private BigDecimal money;
    /**
     * 查询商户订单
     */
    private String orderId;
    /**
     * 查询的支付宝交易号
     */
    private String tradeNo;
}
