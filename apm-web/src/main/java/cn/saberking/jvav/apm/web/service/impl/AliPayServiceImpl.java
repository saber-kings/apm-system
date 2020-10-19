package cn.saberking.jvav.apm.web.service.impl;

import cn.saberking.jvav.apm.web.exception.BusinessException;
import cn.saberking.jvav.apm.web.exception.code.BaseResponseCode;
import cn.saberking.jvav.apm.web.service.AliPayService;
import cn.saberking.jvav.apm.web.utils.Pay;
import cn.saberking.jvav.apm.web.utils.SecurityUtils;
import cn.saberking.jvav.apm.web.vo.AliPayVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Saber污妖王
 * TODO: 类文件简单描述
 * @version 0.0.1
 * @editor Saber污妖王
 * @project apm-web
 * @date 2020/10/13
 * @package cn.saberking.jvav.apm.web.service.impl
 */
@Service
public class AliPayServiceImpl implements AliPayService {
    @Override
    public void payPage(HttpServletResponse response, AliPayVO vo) {
        //商户订单号
        String orderId = SecurityUtils.genOrderId(vo.getUserId());
        //付款金额，从前台获取，必填
        System.out.println(vo.getMoney());
        //订单名称，必填
        String subject = vo.getStoreName();
        Pay.payHandle(response, orderId, vo.getMoney(), subject);
    }

    @Override
    public String pay(AliPayVO vo) {
        //商户订单号
        String orderId = SecurityUtils.genOrderId(vo.getUserId());
        //付款金额，从前台获取，必填
        System.out.println(vo.getMoney());
        //订单名称，必填
        String subject = vo.getStoreName();
        String tradeNo = Pay.getTradeNo(orderId, vo.getMoney(), subject);
        if (tradeNo == null) {
            throw new BusinessException(BaseResponseCode.PAY_ERROR);
        }
        return tradeNo;
    }

    @Override
    public String queryTransaction(AliPayVO vo) {
        String json = Pay.queryHandle(vo.getOrderId(), vo.getTradeNo());
        if (json == null) {
            throw new BusinessException(BaseResponseCode.PAY_NOT_NULL);
        }
        return json;
    }
}
