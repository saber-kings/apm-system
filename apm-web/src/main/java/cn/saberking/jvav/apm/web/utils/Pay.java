package cn.saberking.jvav.apm.web.utils;

import cn.saberking.jvav.apm.web.config.AlipayConfig;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/6/27
 */
public class Pay {
    private static final AlipayClient ALIPAY_CLIENT = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

    public static void payHandle(HttpServletResponse response, String orderId, BigDecimal money, String subject) {
        AlipayTradePagePayRequest pagePayRequest = getPageRequest(orderId, money, subject);
        try {
            AlipayTradePagePayResponse payResponse = getPageResponse(pagePayRequest);
            String form = payResponse.getBody();
            response.setContentType("text/html;charset=" + StandardCharsets.UTF_8);
            //直接将完整的表单html输出到页面
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTradeNo(String orderId, BigDecimal money, String subject) {
        AlipayTradePagePayRequest pagePayRequest = getPageRequest(orderId, money, subject);
        String tradeNo = null;
        try {
            AlipayTradePagePayResponse payResponse = getPageResponse(pagePayRequest);
            tradeNo = payResponse.getTradeNo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tradeNo;
    }

    private static AlipayTradePagePayRequest getPageRequest(String orderId, BigDecimal money, String subject) {
        AlipayTradePagePayRequest pagePayRequest = new AlipayTradePagePayRequest();
        pagePayRequest.setReturnUrl(AlipayConfig.return_url);
        pagePayRequest.setNotifyUrl(AlipayConfig.notify_url);

        pagePayRequest.setBizContent("{\"out_trade_no\":\"" + orderId + "\","
                + "\"total_amount\":\"" + money + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        return pagePayRequest;
    }

    private static AlipayTradePagePayResponse getPageResponse(AlipayTradePagePayRequest pagePayRequest) throws AlipayApiException {
        return ALIPAY_CLIENT.pageExecute(pagePayRequest);
    }

    public static String queryHandle(String orderId, String tradeNo) {
        //创建API对应的request类
        AlipayTradeQueryRequest queryRequest = new AlipayTradeQueryRequest();
        //设置业务参数
        queryRequest.setBizContent("{" +
                " \"out_trade_no\":\"" + orderId + "\"," +
                " \"trade_no\":\"" + tradeNo + "\"" +
                " }");
        try {
            AlipayTradeQueryResponse response = ALIPAY_CLIENT.execute(queryRequest);
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
