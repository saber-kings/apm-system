package cn.saberking.jvav.apm.web.service;

import cn.saberking.jvav.apm.web.vo.AliPayVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Saber污妖王
 * TODO: 类文件简单描述
 * @version 0.0.1
 * @editor Saber污妖王
 * @project apm-web
 * @date 2020/10/13
 * @package cn.saberking.jvav.apm.web.service
 */
public interface AliPayService {
    /**
     * 支付方式一：
     * 直接跳转到支付宝页面
     * @param response 响应
     * @param vo 参数
     */
    void payPage(HttpServletResponse response, AliPayVO vo);

    /**
     * 支付方式二：
     * 支付后返回支付宝交易号
     * @param vo 参数
     * @return 结果
     */
    String pay(AliPayVO vo);

    /**
     * 查询支付宝交易
     * @param vo 参数
     * @return 订单
     */
    String queryTransaction(AliPayVO vo);
}
