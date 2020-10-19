package cn.saberking.jvav.apm.web.utils;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/6/21
 */
@Data
public class BizContent {

    /**
     * 订单号
     */
    private String outTradeNo;
    /**
     * 总金额
     */
    private BigDecimal totalAmount;
    /**
     * 订单名称
     */
    private String subject;

    private String productCode = "FAST_INSTANT_TRADE_PAY";
}
