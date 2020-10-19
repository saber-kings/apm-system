package cn.saberking.jvav.apm.web.controller.mall;

import cn.saberking.jvav.apm.common.annotation.Log;
import cn.saberking.jvav.apm.common.core.controller.BaseController;
import cn.saberking.jvav.apm.common.core.domain.AjaxResult;
import cn.saberking.jvav.apm.common.enums.BusinessType;
import cn.saberking.jvav.apm.common.utils.DateUtils;
import cn.saberking.jvav.apm.common.utils.poi.ExcelUtil;
import cn.saberking.jvav.apm.mall.domain.ApmStoreCategory;
import cn.saberking.jvav.apm.mall.service.IApmStoreCategoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品分类Controller
 *
 * @author apm
 * @date 2020-10-06
 */
@RestController
@RequestMapping("/mall/category")
public class ApmStoreCategoryController extends BaseController {
    @Resource
    private IApmStoreCategoryService apmStoreCategoryService;

    /**
     * 查询商品分类列表
     */
    @PreAuthorize("@ss.hasPermission('mall:category:list')")
    @GetMapping("/list")
    public AjaxResult list(ApmStoreCategory apmStoreCategory) {
        List<ApmStoreCategory> list = apmStoreCategoryService.selectApmStoreCategoryList(apmStoreCategory);
        return AjaxResult.success(list);
    }

    /**
     * 导出商品分类列表
     */
    @PreAuthorize("@ss.hasPermission('mall:category:export')")
    @Log(title = "商品分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ApmStoreCategory apmStoreCategory) {
        List<ApmStoreCategory> list = apmStoreCategoryService.selectApmStoreCategoryList(apmStoreCategory);
        ExcelUtil<ApmStoreCategory> util = new ExcelUtil<>(ApmStoreCategory.class);
        return util.exportExcel(list, "category");
    }

    /**
     * 获取商品分类详细信息
     */
    @PreAuthorize("@ss.hasPermission('mall:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(apmStoreCategoryService.selectApmStoreCategoryById(id));
    }

    /**
     * 新增商品分类
     */
    @PreAuthorize("@ss.hasPermission('mall:category:add')")
    @Log(title = "商品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ApmStoreCategory apmStoreCategory) {
        apmStoreCategory.setAddTime(DateUtils.getNowDate());
        return toAjax(apmStoreCategoryService.insertApmStoreCategory(apmStoreCategory));
    }

    /**
     * 修改商品分类
     */
    @PreAuthorize("@ss.hasPermission('mall:category:edit')")
    @Log(title = "商品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ApmStoreCategory apmStoreCategory) {

        return toAjax(apmStoreCategoryService.updateApmStoreCategory(apmStoreCategory));
    }

    /**
     * 删除商品分类
     */
    @PreAuthorize("@ss.hasPermission('mall:category:remove')")
    @Log(title = "商品分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(apmStoreCategoryService.deleteApmStoreCategoryByIds(ids));
    }

    /**
     * 查询商品分类树形列表
     */
    @GetMapping("/treeList")
    public AjaxResult treeList() {
        List<ApmStoreCategory> categories = apmStoreCategoryService.selectApmStoreCategoryList();
        return AjaxResult.success(categories);
    }

    /**
     * 查询商品二级分类列表
     */
    @GetMapping("/secondList")
    public AjaxResult secondList(ApmStoreCategory apmStoreCategory) {
        List<ApmStoreCategory> categories = apmStoreCategoryService.selectSecondCategoryList(apmStoreCategory);
        return AjaxResult.success(categories);
    }
}
