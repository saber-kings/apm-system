package cn.saberking.jvav.apm.web.service;

import cn.saberking.jvav.apm.web.domain.ApmUser;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/10
 */
public interface ApmUserService {
    /**
     * 用户登录
     *
     * @param apmUser 用户
     * @return 结果
     */
    ApmUser login(ApmUser apmUser);

    /**
     * 用户注册
     *
     * @param apmUser 用户
     * @return
     */
    ApmUser registerByPhone(ApmUser apmUser);

    /**
     * 个人实名认证
     *
     * @param apmUser 用户
     * @return 结果
     */
    ApmUser personalVerified(ApmUser apmUser);

    /**
     * 根据用户id更新用户数据
     *
     * @param user 用户
     * @return 结果
     */
    ApmUser updateUserInfo(ApmUser user);

    /**
     * 根据用户id查询个人信息
     *
     * @param userId 用户Id
     * @return 结果
     */
    ApmUser selectUserById(Long userId);
}
