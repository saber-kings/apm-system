package cn.saberking.jvav.apm.web.controller.mall;

import cn.saberking.jvav.apm.common.annotation.Log;
import cn.saberking.jvav.apm.common.core.controller.BaseController;
import cn.saberking.jvav.apm.common.core.domain.AjaxResult;
import cn.saberking.jvav.apm.common.core.page.TableDataInfo;
import cn.saberking.jvav.apm.common.enums.BusinessType;
import cn.saberking.jvav.apm.common.utils.poi.ExcelUtil;
import cn.saberking.jvav.apm.mall.domain.ApmStoreProductAttrValue;
import cn.saberking.jvav.apm.mall.service.IApmStoreProductAttrValueService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品属性值Controller
 *
 * @author apm
 * @date 2020-10-11
 */
@RestController
@RequestMapping("/mall/value")
public class ApmStoreProductAttrValueController extends BaseController {
    @Resource
    private IApmStoreProductAttrValueService apmStoreProductAttrValueService;

    /**
     * 查询商品属性值列表
     */
    @PreAuthorize("@ss.hasPermission('mall:value:list')")
    @GetMapping("/list")
    public TableDataInfo list(ApmStoreProductAttrValue apmStoreProductAttrValue) {
        startPage();
        List<ApmStoreProductAttrValue> list = apmStoreProductAttrValueService.selectApmStoreProductAttrValueList(apmStoreProductAttrValue);
        return getDataTable(list);
    }

    /**
     * 导出商品属性值列表
     */
    @PreAuthorize("@ss.hasPermission('mall:value:export')")
    @Log(title = "商品属性值", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ApmStoreProductAttrValue apmStoreProductAttrValue) {
        List<ApmStoreProductAttrValue> list = apmStoreProductAttrValueService.selectApmStoreProductAttrValueList(apmStoreProductAttrValue);
        ExcelUtil<ApmStoreProductAttrValue> util = new ExcelUtil<ApmStoreProductAttrValue>(ApmStoreProductAttrValue.class);
        return util.exportExcel(list, "value");
    }

    /**
     * 获取商品属性值详细信息
     */
    @PreAuthorize("@ss.hasPermission('mall:value:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(apmStoreProductAttrValueService.selectApmStoreProductAttrValueById(id));
    }

    /**
     * 新增商品属性值
     */
    @PreAuthorize("@ss.hasPermission('mall:value:add')")
    @Log(title = "商品属性值", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ApmStoreProductAttrValue apmStoreProductAttrValue) {
        return toAjax(apmStoreProductAttrValueService.insertApmStoreProductAttrValue(apmStoreProductAttrValue));
    }

    /**
     * 修改商品属性值
     */
    @PreAuthorize("@ss.hasPermission('mall:value:edit')")
    @Log(title = "商品属性值", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ApmStoreProductAttrValue apmStoreProductAttrValue) {
        return toAjax(apmStoreProductAttrValueService.updateApmStoreProductAttrValue(apmStoreProductAttrValue));
    }

    /**
     * 删除商品属性值
     */
    @PreAuthorize("@ss.hasPermission('mall:value:remove')")
    @Log(title = "商品属性值", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(apmStoreProductAttrValueService.deleteApmStoreProductAttrValueByIds(ids));
    }
}
