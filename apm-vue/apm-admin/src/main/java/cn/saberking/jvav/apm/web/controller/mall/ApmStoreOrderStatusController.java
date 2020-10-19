package cn.saberking.jvav.apm.web.controller.mall;

import cn.saberking.jvav.apm.common.annotation.Log;
import cn.saberking.jvav.apm.common.core.controller.BaseController;
import cn.saberking.jvav.apm.common.core.domain.AjaxResult;
import cn.saberking.jvav.apm.common.core.page.TableDataInfo;
import cn.saberking.jvav.apm.common.enums.BusinessType;
import cn.saberking.jvav.apm.common.utils.poi.ExcelUtil;
import cn.saberking.jvav.apm.mall.domain.ApmStoreOrderStatus;
import cn.saberking.jvav.apm.mall.service.IApmStoreOrderStatusService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单操作记录Controller
 *
 * @author apm
 * @date 2020-10-07
 */
@RestController
@RequestMapping("/mall/status")
public class ApmStoreOrderStatusController extends BaseController {
    @Resource
    private IApmStoreOrderStatusService apmStoreOrderStatusService;

    /**
     * 查询订单操作记录列表
     */
    @PreAuthorize("@ss.hasPermission('mall:status:list')")
    @GetMapping("/list")
    public TableDataInfo list(ApmStoreOrderStatus apmStoreOrderStatus) {
        startPage();
        List<ApmStoreOrderStatus> list = apmStoreOrderStatusService.selectApmStoreOrderStatusList(apmStoreOrderStatus);
        return getDataTable(list);
    }

    /**
     * 导出订单操作记录列表
     */
    @PreAuthorize("@ss.hasPermission('mall:status:export')")
    @Log(title = "订单操作记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ApmStoreOrderStatus apmStoreOrderStatus) {
        List<ApmStoreOrderStatus> list = apmStoreOrderStatusService.selectApmStoreOrderStatusList(apmStoreOrderStatus);
        ExcelUtil<ApmStoreOrderStatus> util = new ExcelUtil<ApmStoreOrderStatus>(ApmStoreOrderStatus.class);
        return util.exportExcel(list, "status");
    }

    /**
     * 获取订单操作记录详细信息
     */
    @PreAuthorize("@ss.hasPermission('mall:status:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(apmStoreOrderStatusService.selectApmStoreOrderStatusById(id));
    }

    /**
     * 新增订单操作记录
     */
    @PreAuthorize("@ss.hasPermission('mall:status:add')")
    @Log(title = "订单操作记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ApmStoreOrderStatus apmStoreOrderStatus) {
        return toAjax(apmStoreOrderStatusService.insertApmStoreOrderStatus(apmStoreOrderStatus));
    }

    /**
     * 修改订单操作记录
     */
    @PreAuthorize("@ss.hasPermission('mall:status:edit')")
    @Log(title = "订单操作记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ApmStoreOrderStatus apmStoreOrderStatus) {
        return toAjax(apmStoreOrderStatusService.updateApmStoreOrderStatus(apmStoreOrderStatus));
    }

    /**
     * 删除订单操作记录
     */
    @PreAuthorize("@ss.hasPermission('mall:status:remove')")
    @Log(title = "订单操作记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(apmStoreOrderStatusService.deleteApmStoreOrderStatusByIds(ids));
    }
}
