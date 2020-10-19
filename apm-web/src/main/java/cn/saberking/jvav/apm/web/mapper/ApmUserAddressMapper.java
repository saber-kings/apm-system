package cn.saberking.jvav.apm.web.mapper;

import cn.saberking.jvav.apm.web.domain.ApmUserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 用户地址Mapper接口
 *
 * @author apm
 * @date 2020-10-12
 */
@Mapper
public interface ApmUserAddressMapper {
    /**
     * 查询用户地址
     *
     * @param id 用户地址ID
     * @return 用户地址
     */
    ApmUserAddress selectApmUserAddressById(Integer id);

    /**
     * 查询用户地址列表
     *
     * @param apmUserAddress 用户地址
     * @return 用户地址集合
     */
    List<ApmUserAddress> selectApmUserAddressList(ApmUserAddress apmUserAddress);

    /**
     * 新增用户地址
     *
     * @param apmUserAddress 用户地址
     * @return 结果
     */
    int insertApmUserAddress(ApmUserAddress apmUserAddress);

    /**
     * 修改用户地址
     *
     * @param apmUserAddress 用户地址
     * @return 结果
     */
    int updateApmUserAddress(ApmUserAddress apmUserAddress);

    /**
     * 删除用户地址
     *
     * @param id 用户地址ID
     * @return 结果
     */
    int deleteApmUserAddressById(Integer id);

    /**
     * 批量删除用户地址
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteApmUserAddressByIds(Integer[] ids);

    /**
     * 批量逻辑删除用户地址
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletedApmUserAddressByIds(Integer[] ids);
}
