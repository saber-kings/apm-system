package cn.saberking.jvav.apm.web.controller.system;

import cn.saberking.jvav.apm.common.annotation.Log;
import cn.saberking.jvav.apm.common.annotation.RepeatSubmit;
import cn.saberking.jvav.apm.common.constant.UserConstants;
import cn.saberking.jvav.apm.common.core.controller.BaseController;
import cn.saberking.jvav.apm.common.core.domain.AjaxResult;
import cn.saberking.jvav.apm.common.core.page.TableDataInfo;
import cn.saberking.jvav.apm.common.enums.BusinessType;
import cn.saberking.jvav.apm.common.utils.SecurityUtils;
import cn.saberking.jvav.apm.common.utils.poi.ExcelUtil;
import cn.saberking.jvav.apm.system.domain.SysConfig;
import cn.saberking.jvav.apm.system.service.ISysConfigService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 参数配置 信息操作处理
 *
 * @author apm
 */
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {
    @Resource
    private ISysConfigService configService;

    /**
     * 获取参数配置列表
     */
    @PreAuthorize("@ss.hasPermission('system:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysConfig config) {
        startPage();
        List<SysConfig> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    @Log(title = "参数管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('system:config:export')")
    @GetMapping("/export")
    public AjaxResult export(SysConfig config) {
        List<SysConfig> list = configService.selectConfigList(config);
        ExcelUtil<SysConfig> util = new ExcelUtil<>(SysConfig.class);
        return util.exportExcel(list, "参数数据");
    }

    /**
     * 根据参数编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermission('system:config:query')")
    @GetMapping(value = "/{configId}")
    public AjaxResult getInfo(@PathVariable Long configId) {
        return AjaxResult.success(configService.selectConfigById(configId));
    }

    /**
     * 根据参数键名查询参数值
     */
    @GetMapping(value = "/configKey/{configKey}")
    public AjaxResult getConfigKey(@PathVariable String configKey) {
        return AjaxResult.success(configService.selectConfigByKey(configKey));
    }

    /**
     * 新增参数配置
     */
    @PreAuthorize("@ss.hasPermission('system:config:add')")
    @Log(title = "参数管理", businessType = BusinessType.INSERT)
    @PostMapping
    @RepeatSubmit
    public AjaxResult add(@Validated @RequestBody SysConfig config) {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return AjaxResult.error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setCreateBy(SecurityUtils.getUsername());
        return toAjax(configService.insertConfig(config));
    }

    /**
     * 修改参数配置
     */
    @PreAuthorize("@ss.hasPermission('system:config:edit')")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysConfig config) {
        if (UserConstants.NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return AjaxResult.error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(configService.updateConfig(config));
    }

    /**
     * 删除参数配置
     */
    @PreAuthorize("@ss.hasPermission('system:config:remove')")
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    public AjaxResult remove(@PathVariable Long[] configIds) {
        return toAjax(configService.deleteConfigByIds(configIds));
    }

    /**
     * 清空缓存
     */
    @PreAuthorize("@ss.hasPermission('system:config:remove')")
    @Log(title = "参数管理", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clearCache")
    public AjaxResult clearCache() {
        configService.clearCache();
        return AjaxResult.success();
    }
}
