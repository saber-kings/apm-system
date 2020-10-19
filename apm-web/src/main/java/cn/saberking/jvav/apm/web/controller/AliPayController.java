package cn.saberking.jvav.apm.web.controller;

import cn.saberking.jvav.apm.web.service.AliPayService;
import cn.saberking.jvav.apm.web.utils.DataResult;
import cn.saberking.jvav.apm.web.vo.AliPayVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/6/27
 */
@RestController("/pay")
public class AliPayController {
    @Resource
    private AliPayService aliPayService;

    @PostMapping("/aliPayPage")
    public void payPage(HttpServletResponse response, @RequestBody AliPayVO vo) {
        aliPayService.payPage(response, vo);
    }

    @PostMapping("/aliPay")
    public DataResult<String> pay(@RequestBody AliPayVO vo) {
        return DataResult.success(aliPayService.pay(vo));
    }

    @GetMapping("/query")
    public DataResult<String> query(AliPayVO vo) {
        return DataResult.success(aliPayService.queryTransaction(vo));
    }

    /**
     * 支付成功后的异步回调
     *
     * @return 结果
     */
    @ResponseBody
    @RequestMapping("/notifyUrl")
    public String notifyUrl() {
        return "服务端成功的回调";
    }

    /**
     * 支付成功后同步回调
     *
     * @return 结果
     */
    @ResponseBody
    @RequestMapping("/returnUrl")
    public String returnUrl() {
        return "success";
    }
}
