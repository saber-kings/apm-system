package cn.saberking.jvav.apm.web.controller;

import cn.saberking.jvav.apm.web.domain.ApmUser;
import cn.saberking.jvav.apm.web.service.ApmUserService;
import cn.saberking.jvav.apm.web.utils.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/10
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class ApmUserController {

    @Resource
    private ApmUserService apmUserService;


    /**
     * 用户登录
     * @param apmUser 用户
     * @return 结果
     */
    @GetMapping("/login")
    public DataResult<ApmUser> login(ApmUser apmUser) {
        return DataResult.success(apmUserService.login(apmUser));
    }

    /**
     * 用户注册
     * @param apmUser 用户
     * @return 结果
     */
    @PostMapping("/registerByPhone")
    public DataResult<ApmUser> registerByPhone(@RequestBody ApmUser apmUser){
        return DataResult.success(apmUserService.registerByPhone(apmUser));
    }

    /**
     * 个人实名认证
     * @param apmUser 用户
     * @return 结果
     */
    @PutMapping("/personalVerified")
    public  DataResult<ApmUser> personalVerified(@RequestBody ApmUser apmUser){
        return DataResult.success(apmUserService.personalVerified(apmUser));
    }

    /**
     * 根据用户id更新用户信息并进行返回
     * @param apmUser
     * @return
     */
    @PutMapping("/")
    public DataResult<ApmUser> updateUserInfoByUserId(@RequestBody ApmUser apmUser){
        return DataResult.success(apmUserService.personalVerified(apmUser));
    }
}
