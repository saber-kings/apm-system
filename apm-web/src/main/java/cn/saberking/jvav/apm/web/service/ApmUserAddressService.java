package cn.saberking.jvav.apm.web.service;

import cn.saberking.jvav.apm.web.domain.ApmUserAddress;

import java.util.List;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/11
 */
public interface ApmUserAddressService {
    /**
     * 添加用户地址
     *
     * @param address 用户地址
     * @return 结果
     */
    ApmUserAddress insertApmUserAddress(ApmUserAddress address);


    /**
     * 查询用户地址列表
     *
     * @param apmUserAddress 用户地址
     * @return 用户地址集合
     */
    List<ApmUserAddress> selectUserAddressList(ApmUserAddress apmUserAddress);


    /**
     * 更新用地址
     *
     * @param address 用户地址
     * @return 结果
     */
    ApmUserAddress updateApmUserAddress(ApmUserAddress address);

    /**
     * 根据用户地址id删除地址信息
     *
     * @param id 用户地址Id
     */
    void deleteApmUserAddressById(Integer id);

    /**
     * 批量删除用户地址
     *
     * @param ids 需要删除的用户地址ID
     */
    void deleteApmUserAddressByIds(Integer[] ids);

    /**
     * 批量逻辑删除用户地址
     *
     * @param ids 需要删除的用户地址ID
     */
    void deletedApmUserAddressByIds(Integer[] ids);

    /**
     * 根据id获取地址
     *
     * @param id 地址Id
     * @return 结果
     */
    ApmUserAddress selectByPrimaryKey(Integer id);
}
