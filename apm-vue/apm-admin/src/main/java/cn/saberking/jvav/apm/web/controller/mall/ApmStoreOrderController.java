package cn.saberking.jvav.apm.web.controller.mall;

import cn.saberking.jvav.apm.common.annotation.Log;
import cn.saberking.jvav.apm.common.core.controller.BaseController;
import cn.saberking.jvav.apm.common.core.domain.AjaxResult;
import cn.saberking.jvav.apm.common.core.page.TableDataInfo;
import cn.saberking.jvav.apm.common.enums.BusinessType;
import cn.saberking.jvav.apm.common.utils.poi.ExcelUtil;
import cn.saberking.jvav.apm.mall.domain.ApmStoreOrder;
import cn.saberking.jvav.apm.mall.service.IApmStoreOrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单Controller
 *
 * @author apm
 * @date 2020-10-06
 */
@RestController
@RequestMapping("/mall/order")
public class ApmStoreOrderController extends BaseController {
    @Resource
    private IApmStoreOrderService apmStoreOrderService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermission('mall:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(ApmStoreOrder apmStoreOrder) {
        startPage();
        List<ApmStoreOrder> list = apmStoreOrderService.selectApmStoreOrderList(apmStoreOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermission('mall:order:export')")
    @Log(title = "订单管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ApmStoreOrder apmStoreOrder) {
        List<ApmStoreOrder> list = apmStoreOrderService.selectApmStoreOrderList(apmStoreOrder);
        ExcelUtil<ApmStoreOrder> util = new ExcelUtil<ApmStoreOrder>(ApmStoreOrder.class);
        return util.exportExcel(list, "order");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermission('mall:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(apmStoreOrderService.selectApmStoreOrderById(id));
    }

    /**
     * 根据用户id查询订单列表
     *
     * @param userId
     * @return
     */
    @RequestMapping("/queryOrderByUserId")
    public AjaxResult queryOrderByUserId(Integer userId) {
        return AjaxResult.success(apmStoreOrderService.queryOrderByUserId(userId));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermission('mall:order:add')")
    @Log(title = "订单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ApmStoreOrder apmStoreOrder) {
        return toAjax(apmStoreOrderService.insertApmStoreOrder(apmStoreOrder));
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermission('mall:order:edit')")
    @Log(title = "订单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ApmStoreOrder apmStoreOrder) {
        return toAjax(apmStoreOrderService.updateApmStoreOrder(apmStoreOrder));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermission('mall:order:delete')")
    @Log(title = "订单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/complete/{ids}")
    public AjaxResult delete(@PathVariable Integer[] ids) {
        return toAjax(apmStoreOrderService.deleteApmStoreOrderByIds(ids));
    }

    /**
     * 逻辑删除订单
     */
    @PreAuthorize("@ss.hasPermission('mall:order:remove')")
    @Log(title = "订单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(apmStoreOrderService.deleteApmStoreOrderByIds(ids));
    }
}
