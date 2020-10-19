package cn.saberking.jvav.apm.web.controller;

import cn.saberking.jvav.apm.web.domain.ApmUserAddress;
import cn.saberking.jvav.apm.web.service.ApmUserAddressService;
import cn.saberking.jvav.apm.web.utils.DataResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/11
 */
@RestController
@RequestMapping("/address")
public class ApmUserAddressController {
    @Resource
    private ApmUserAddressService addressService;

    /**
     * 查询全部用户地址
     * @param userAddress 用户地址
     * @return 结果
     */
    @GetMapping("/list")
    public DataResult<List<ApmUserAddress>> list(ApmUserAddress userAddress) {
        return DataResult.success(addressService.selectUserAddressList(userAddress));
    }

    /**
     * 添加用户地址
     *
     * @param userAddress 用户地址
     * @return 结果
     */
    @PostMapping("/")
    public DataResult<ApmUserAddress> add(@RequestBody ApmUserAddress userAddress) {
        return DataResult.success(addressService.insertApmUserAddress(userAddress));
    }

    /**
     * 修改用户地址信息
     *
     * @param userAddress 用户地址
     * @return 结果
     */
    @PutMapping("/")
    public DataResult<ApmUserAddress> edit(@RequestBody ApmUserAddress userAddress) {
        return DataResult.success(addressService.updateApmUserAddress(userAddress));
    }

    /**
     * 删除
     *
     * @param id 用户地址id
     * @return 结果
     */
    @DeleteMapping("/{id}")
    public DataResult<Object> delete(@PathVariable("id") Integer id) {
        addressService.deleteApmUserAddressById(id);
        return DataResult.success();
    }

    /**
     * 删除用户地址
     */
    @DeleteMapping("/batch/{ids}")
    public DataResult<Object> remove(@PathVariable Integer[] ids) {
        addressService.deletedApmUserAddressByIds(ids);
        return DataResult.success();
    }

    /**
     * 删除用户地址
     */
    @DeleteMapping("/complete/{ids}")
    public DataResult<Object> completeDel(@PathVariable Integer[] ids) {
        addressService.deleteApmUserAddressByIds(ids);
        return DataResult.success();
    }

    /**
     * 根据id获取地址
     *
     * @param id id
     * @return 结果
     */
    @GetMapping("/{id}")
    public DataResult<ApmUserAddress> getInfo(@PathVariable("id") Integer id) {
        return DataResult.success(addressService.selectByPrimaryKey(id));
    }
}
