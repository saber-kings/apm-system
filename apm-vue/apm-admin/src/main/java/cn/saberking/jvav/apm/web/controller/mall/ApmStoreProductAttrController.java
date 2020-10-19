package cn.saberking.jvav.apm.web.controller.mall;

import cn.saberking.jvav.apm.common.annotation.Log;
import cn.saberking.jvav.apm.common.core.controller.BaseController;
import cn.saberking.jvav.apm.common.core.domain.AjaxResult;
import cn.saberking.jvav.apm.common.core.page.TableDataInfo;
import cn.saberking.jvav.apm.common.enums.BusinessType;
import cn.saberking.jvav.apm.common.utils.poi.ExcelUtil;
import cn.saberking.jvav.apm.mall.domain.ApmStoreProductAttr;
import cn.saberking.jvav.apm.mall.service.IApmStoreProductAttrService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品属性Controller
 *
 * @author apm
 * @date 2020-10-11
 */
@RestController
@RequestMapping("/mall/attr")
public class ApmStoreProductAttrController extends BaseController {
    @Resource
    private IApmStoreProductAttrService apmStoreProductAttrService;

    /**
     * 查询商品属性列表
     */
    @PreAuthorize("@ss.hasPermission('mall:attr:list')")
    @GetMapping("/list")
    public TableDataInfo list(ApmStoreProductAttr apmStoreProductAttr) {
        startPage();
        List<ApmStoreProductAttr> list = apmStoreProductAttrService.selectApmProductAttrList(apmStoreProductAttr);
        return getDataTable(list);
    }

    /**
     * 导出商品属性列表
     */
    @PreAuthorize("@ss.hasPermission('mall:attr:export')")
    @Log(title = "商品属性", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ApmStoreProductAttr apmStoreProductAttr) {
        List<ApmStoreProductAttr> list = apmStoreProductAttrService.selectApmStoreProductAttrList(apmStoreProductAttr);
        ExcelUtil<ApmStoreProductAttr> util = new ExcelUtil<ApmStoreProductAttr>(ApmStoreProductAttr.class);
        return util.exportExcel(list, "attr");
    }

    /**
     * 获取商品属性详细信息
     */
    @PreAuthorize("@ss.hasPermission('mall:attr:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(apmStoreProductAttrService.selectApmStoreProductAttrById(id));
    }

    /**
     * 新增商品属性
     */
    @PreAuthorize("@ss.hasPermission('mall:attr:add')")
    @Log(title = "商品属性", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ApmStoreProductAttr apmStoreProductAttr) {
        return toAjax(apmStoreProductAttrService.insertApmStoreProductAttr(apmStoreProductAttr));
    }

    /**
     * 修改商品属性
     */
    @PreAuthorize("@ss.hasPermission('mall:attr:edit')")
    @Log(title = "商品属性", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ApmStoreProductAttr apmStoreProductAttr) {
        return toAjax(apmStoreProductAttrService.updateApmStoreProductAttr(apmStoreProductAttr));
    }

    /**
     * 删除商品属性
     */
    @PreAuthorize("@ss.hasPermission('mall:attr:remove')")
    @Log(title = "商品属性", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(apmStoreProductAttrService.deleteApmStoreProductAttrByIds(ids));
    }
}
