package cn.saberking.jvav.apm.web.service.impl;

import cn.saberking.jvav.apm.web.domain.ApmUserAddress;
import cn.saberking.jvav.apm.web.exception.BusinessException;
import cn.saberking.jvav.apm.web.exception.code.BaseResponseCode;
import cn.saberking.jvav.apm.web.mapper.ApmUserAddressMapper;
import cn.saberking.jvav.apm.web.service.ApmUserAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/11
 */
@Service
public class ApmUserAddressServiceImpl implements ApmUserAddressService {

    @Resource
    private ApmUserAddressMapper apmStoreAddressMapper;

    /**
     * 添加用户地址
     *
     * @param userAddress 用户地址
     * @return 结果
     */
    @Override
    public ApmUserAddress insertApmUserAddress(ApmUserAddress userAddress) {
        userAddress.setCreateTime(new Date());
        int i = apmStoreAddressMapper.insertApmUserAddress(userAddress);
        if (i==0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        return userAddress;
    }

    /**
     * 查询全部用户地址
     *
     * @return 结果
     * @param userAddress 用户地址
     */
    @Override
    public List<ApmUserAddress> selectUserAddressList(ApmUserAddress userAddress) {
        return apmStoreAddressMapper.selectApmUserAddressList(userAddress);
    }

    /**
     * 更新用户地址信息
     *
     * @param userAddress 用户地址
     * @return 结果
     */
    @Override
    public ApmUserAddress updateApmUserAddress(ApmUserAddress userAddress) {
        int i = apmStoreAddressMapper.updateApmUserAddress(userAddress);
        if (i==0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        return userAddress;
    }

    /**
     * 根据id删除地址信息
     *
     * @param id 用户Id
     */
    @Override
    public void deleteApmUserAddressById(Integer id) {
        int i = apmStoreAddressMapper.deleteApmUserAddressById(id);
        if (i==0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    /**
     * 批量删除用户地址
     *
     * @param ids 需要删除的用户地址ID
     */
    @Override
    public void deleteApmUserAddressByIds(Integer[] ids) {
        int i = apmStoreAddressMapper.deleteApmUserAddressByIds(ids);
        if (i==0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    @Override
    public void deletedApmUserAddressByIds(Integer[] ids) {
        int i = apmStoreAddressMapper.deletedApmUserAddressByIds(ids);
        if (i==0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }


    /**
     * 根据id获取地址
     *
     * @param id 地址Id
     * @return 结果
     */
    @Override
    public ApmUserAddress selectByPrimaryKey(Integer id) {
        return apmStoreAddressMapper.selectApmUserAddressById(id);
    }
}
