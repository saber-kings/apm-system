package cn.saberking.jvav.apm.web.service.impl;

import cn.saberking.jvav.apm.web.constants.Constants;
import cn.saberking.jvav.apm.web.domain.ApmUser;
import cn.saberking.jvav.apm.web.exception.BusinessException;
import cn.saberking.jvav.apm.web.exception.code.BaseResponseCode;
import cn.saberking.jvav.apm.web.mapper.ApmUserMapper;
import cn.saberking.jvav.apm.web.service.ApmUserService;
import cn.saberking.jvav.apm.web.utils.JwtUtils;
import cn.saberking.jvav.apm.web.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/10
 */
@Service
public class ApmUserServiceImpl implements ApmUserService {
    @Resource
    private ApmUserMapper apmUserMapper;

    /**
     * 用户登录
     *
     * @param apmUser 用户
     * @return 结果
     */
    @Override
    public ApmUser login(ApmUser apmUser) {
        //用来存放payload
        ApmUser user = apmUserMapper.selectUserByPhone(apmUser.getPhoneNumber());
        if (user != null) {
            Map<String, String> map = new HashMap<>();
            map.put("userId", user.getUserId().toString());
            map.put("userName", user.getUserName());

            String token = JwtUtils.getToken(map);
            if (StringUtils.isNotBlank(token)) {
                if (Constants.BAN_OR_DEL.equals(apmUser.getStatus())) {
                    throw new BusinessException(BaseResponseCode.ACCOUNT_LOCK);
                }
                if (Constants.BAN_OR_DEL.equals(apmUser.getDelFlag())) {
                    throw new BusinessException(BaseResponseCode.ACCOUNT_HAS_DELETED_ERROR);
                }
                if (SecurityUtils.matchApmUserPass(apmUser.getPassword(), user.getPassword())) {
                    if (StringUtils.isNotBlank(user.getCardId())) {
                        user.setCardId(SecurityUtils.idFormat(user.getCardId()));
                    }
                    user.setToken(token);
                    user.setPassword(null);
                    return user;
                } else {
                    throw new BusinessException(BaseResponseCode.ACCOUNT_PASSWORD_ERROR);
                }
            } else {
                throw new BusinessException(BaseResponseCode.AUTHORIZE_ERROR);
            }
        } else {
            throw new BusinessException(BaseResponseCode.ACCOUNT_NOT_FOUND);
        }
    }

    /**
     * 用户注册
     *
     * @param apmUser 用户
     * @return 结果
     */
    @Override
    public ApmUser registerByPhone(ApmUser apmUser) {
        apmUser.setUserName("apm_" + apmUser.getPhoneNumber());
        apmUser.setNickName("apm_" + apmUser.getPhoneNumber());
        apmUser.setCreateTime(new Date());
        apmUser.setPassword(SecurityUtils.encryptApmUserPass(apmUser.getPassword()));
        int i = apmUserMapper.insertApmUser(apmUser);
        if (i == 0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        } else {
            apmUser.setPassword(null);
            return apmUser;
        }
    }

    /**
     * 个人实名认证
     *
     * @param apmUser 用户
     * @return 结果
     */
    @Override
    public ApmUser personalVerified(ApmUser apmUser) {
        int i = apmUserMapper.updateApmUser(apmUser);
        if (i == 0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        return apmUser;
    }

    /**
     * 根据用户id修改用户信息
     *
     * @param apmUser 用户
     * @return 结果
     */
    @Override
    public ApmUser updateUserInfo(ApmUser apmUser) {
        apmUser.setCardId(null);
        int i = apmUserMapper.updateApmUser(apmUser);
        if (i == 0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        return apmUser;
    }

    /**
     * 根据用户id查询用户信息
     *
     * @param userId 用户Id
     * @return 结果
     */
    @Override
    public ApmUser selectUserById(Long userId) {
        return apmUserMapper.selectApmUserById(userId);
    }
}
