package cn.saberking.jvav.apm.web.controller.monitor;

import cn.saberking.jvav.apm.common.annotation.Log;
import cn.saberking.jvav.apm.common.core.controller.BaseController;
import cn.saberking.jvav.apm.common.core.domain.AjaxResult;
import cn.saberking.jvav.apm.common.core.page.TableDataInfo;
import cn.saberking.jvav.apm.common.enums.BusinessType;
import cn.saberking.jvav.apm.common.utils.poi.ExcelUtil;
import cn.saberking.jvav.apm.system.domain.SysLogininfor;
import cn.saberking.jvav.apm.system.service.ISysLoginInforService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统访问记录
 *
 * @author apm
 */
@RestController
@RequestMapping("/monitor/logininfor")
public class SysLogininforController extends BaseController {
    @Resource
    private ISysLoginInforService loginInforService;

    @PreAuthorize("@ss.hasPermission('monitor:logininfor:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysLogininfor loginInfor) {
        startPage();
        List<SysLogininfor> list = loginInforService.selectLoginInforList(loginInfor);
        return getDataTable(list);
    }

    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermission('monitor:logininfor:export')")
    @GetMapping("/export")
    public AjaxResult export(SysLogininfor loginInfor) {
        List<SysLogininfor> list = loginInforService.selectLoginInforList(loginInfor);
        ExcelUtil<SysLogininfor> util = new ExcelUtil<>(SysLogininfor.class);
        return util.exportExcel(list, "登录日志");
    }

    @PreAuthorize("@ss.hasPermission('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds) {
        return toAjax(loginInforService.deleteLoginInforByIds(infoIds));
    }

    @PreAuthorize("@ss.hasPermission('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public AjaxResult clean() {
        loginInforService.cleanLoginInfor();
        return AjaxResult.success();
    }
}
