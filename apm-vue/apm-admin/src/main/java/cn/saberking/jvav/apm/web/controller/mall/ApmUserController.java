package cn.saberking.jvav.apm.web.controller.mall;

import cn.saberking.jvav.apm.common.annotation.Log;
import cn.saberking.jvav.apm.common.constant.UserConstants;
import cn.saberking.jvav.apm.common.core.controller.BaseController;
import cn.saberking.jvav.apm.common.core.domain.AjaxResult;
import cn.saberking.jvav.apm.common.core.page.TableDataInfo;
import cn.saberking.jvav.apm.common.enums.BusinessType;
import cn.saberking.jvav.apm.common.utils.SecurityUtils;
import cn.saberking.jvav.apm.common.utils.poi.ExcelUtil;
import cn.saberking.jvav.apm.mall.domain.ApmUser;
import cn.saberking.jvav.apm.mall.service.IApmUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商城用户Controller
 *
 * @author apm
 * @date 2020-10-07
 */
@RestController
@RequestMapping("/mall/user")
public class ApmUserController extends BaseController {
    @Resource
    private IApmUserService apmUserService;

    /**
     * 查询商城用户列表
     */
    @PreAuthorize("@ss.hasPermission('mall:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(ApmUser apmUser) {
        startPage();
        List<ApmUser> list = apmUserService.selectApmUserList(apmUser);
        return getDataTable(list);
    }

    /**
     * 导出商城用户列表
     */
    @PreAuthorize("@ss.hasPermission('mall:user:export')")
    @Log(title = "商城用户管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ApmUser apmUser) {
        List<ApmUser> list = apmUserService.selectApmUserList(apmUser);
        ExcelUtil<ApmUser> util = new ExcelUtil<>(ApmUser.class);
        return util.exportExcel(list, "user");
    }

    /**
     * 获取商城用户详细信息
     */
    @PreAuthorize("@ss.hasPermission('mall:user:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId) {
        return AjaxResult.success(apmUserService.selectApmUserById(userId));
    }

    /**
     * 新增商城用户
     */
    @PreAuthorize("@ss.hasPermission('mall:user:add')")
    @Log(title = "商城用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ApmUser apmUser) {
        return toAjax(apmUserService.insertApmUser(apmUser));
    }

    /**
     * 修改商城用户
     */
    @PreAuthorize("@ss.hasPermission('mall:user:edit')")
    @Log(title = "商城用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ApmUser apmUser) {
        if (UserConstants.NOT_UNIQUE.equals(apmUserService.checkPhoneUnique(apmUser))) {
            return AjaxResult.error("修改用户账号为'" + apmUser.getUserName() + "'失败，手机号码已存在");
        }
        apmUser.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(apmUserService.updateApmUser(apmUser));
    }

    /**
     * 删除商城用户
     */
    @PreAuthorize("@ss.hasPermission('mall:user:delete')")
    @Log(title = "商城用户管理-彻底删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/complete/{userIds}")
    public AjaxResult delete(@PathVariable Long[] userIds) {
        return toAjax(apmUserService.deleteApmUserByIds(userIds));
    }

    /**
     * 逻辑删除商城用户
     */
    @PreAuthorize("@ss.hasPermission('mall:user:remove')")
    @Log(title = "商城用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(apmUserService.deletedApmUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermission('mall:user:edit')")
    @Log(title = "商城用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody ApmUser apmUser) {
        apmUserService.checkUserAllowed(apmUser);
        apmUser.setPassword(SecurityUtils.encryptApmUserPass(apmUser.getPassword()));
        apmUser.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(apmUserService.resetPwd(apmUser));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermission('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody ApmUser apmUser) {
        apmUserService.checkUserAllowed(apmUser);
        apmUser.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(apmUserService.updateUserStatus(apmUser));
    }
}
