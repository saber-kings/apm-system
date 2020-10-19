package cn.saberking.jvav.apm.web.controller.mall;

import cn.saberking.jvav.apm.common.annotation.Log;
import cn.saberking.jvav.apm.common.core.controller.BaseController;
import cn.saberking.jvav.apm.common.core.domain.AjaxResult;
import cn.saberking.jvav.apm.common.core.page.TableDataInfo;
import cn.saberking.jvav.apm.common.enums.BusinessType;
import cn.saberking.jvav.apm.common.utils.poi.ExcelUtil;
import cn.saberking.jvav.apm.mall.domain.ApmStoreOrderCartInfo;
import cn.saberking.jvav.apm.mall.service.IApmStoreOrderCartInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单购物详情Controller
 *
 * @author apm
 * @date 2020-10-07
 */
@RestController
@RequestMapping("/mall/info")
public class ApmStoreOrderCartInfoController extends BaseController {
    @Resource
    private IApmStoreOrderCartInfoService apmStoreOrderCartInfoService;

    /**
     * 查询订单购物详情列表
     */
    @PreAuthorize("@ss.hasPermission('mall:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(ApmStoreOrderCartInfo apmStoreOrderCartInfo) {
        startPage();
        List<ApmStoreOrderCartInfo> list = apmStoreOrderCartInfoService.selectApmStoreOrderCartInfoList(apmStoreOrderCartInfo);
        return getDataTable(list);
    }

    /**
     * 导出订单购物详情列表
     */
    @PreAuthorize("@ss.hasPermission('mall:info:export')")
    @Log(title = "订单购物详情", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ApmStoreOrderCartInfo apmStoreOrderCartInfo) {
        List<ApmStoreOrderCartInfo> list = apmStoreOrderCartInfoService.selectApmStoreOrderCartInfoList(apmStoreOrderCartInfo);
        ExcelUtil<ApmStoreOrderCartInfo> util = new ExcelUtil<ApmStoreOrderCartInfo>(ApmStoreOrderCartInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 获取订单购物详情详细信息
     */
    @PreAuthorize("@ss.hasPermission('mall:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(apmStoreOrderCartInfoService.selectApmStoreOrderCartInfoById(id));
    }

    /**
     * 新增订单购物详情
     */
    @PreAuthorize("@ss.hasPermission('mall:info:add')")
    @Log(title = "订单购物详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ApmStoreOrderCartInfo apmStoreOrderCartInfo) {
        return toAjax(apmStoreOrderCartInfoService.insertApmStoreOrderCartInfo(apmStoreOrderCartInfo));
    }

    /**
     * 修改订单购物详情
     */
    @PreAuthorize("@ss.hasPermission('mall:info:edit')")
    @Log(title = "订单购物详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ApmStoreOrderCartInfo apmStoreOrderCartInfo) {
        return toAjax(apmStoreOrderCartInfoService.updateApmStoreOrderCartInfo(apmStoreOrderCartInfo));
    }

    /**
     * 删除订单购物详情
     */
    @PreAuthorize("@ss.hasPermission('mall:info:remove')")
    @Log(title = "订单购物详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(apmStoreOrderCartInfoService.deleteApmStoreOrderCartInfoByIds(ids));
    }
}
