package cn.saberking.jvav.apm.web.controller.mall;

import cn.saberking.jvav.apm.common.annotation.Log;
import cn.saberking.jvav.apm.common.config.ApmConfig;
import cn.saberking.jvav.apm.common.constant.Constants;
import cn.saberking.jvav.apm.common.core.controller.BaseController;
import cn.saberking.jvav.apm.common.core.domain.AjaxResult;
import cn.saberking.jvav.apm.common.core.domain.model.UploadBody;
import cn.saberking.jvav.apm.common.core.page.TableDataInfo;
import cn.saberking.jvav.apm.common.enums.BusinessType;
import cn.saberking.jvav.apm.common.exception.file.InvalidExtensionException;
import cn.saberking.jvav.apm.common.utils.StringUtils;
import cn.saberking.jvav.apm.common.utils.file.FileUtils;
import cn.saberking.jvav.apm.common.utils.poi.ExcelUtil;
import cn.saberking.jvav.apm.mall.domain.ApmStoreProduct;
import cn.saberking.jvav.apm.mall.enumeration.ProductCategory;
import cn.saberking.jvav.apm.mall.service.IApmStoreProductService;
import cn.saberking.jvav.apm.system.service.ISysUploadFileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 商品Controller
 *
 * @author apm
 * @date 2020-10-06
 */
@RestController
@RequestMapping("/mall/product")
public class ApmStoreProductController extends BaseController {
    @Resource
    private IApmStoreProductService apmStoreProductService;

    @Resource
    private ISysUploadFileService sysUploadFileService;

    /**
     * 查询商品列表（单表查询）
     */
    @PreAuthorize("@ss.hasPermission('mall:product:list')")
    @GetMapping("/singleList")
    public TableDataInfo singleList(ApmStoreProduct apmStoreProduct) {
        startPage();
        List<ApmStoreProduct> list = apmStoreProductService.selectApmStoreProductList(apmStoreProduct);
        return getDataTable(list);
    }

    /**
     * 查询商品列表（带分类）
     */
    @PreAuthorize("@ss.hasPermission('mall:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(ApmStoreProduct apmStoreProduct) {
        startPage();
        List<ApmStoreProduct> list = apmStoreProductService.selectCateProducts(apmStoreProduct);
        return getDataTable(list);
    }

    /**
     * 导出商品列表
     */
    @PreAuthorize("@ss.hasPermission('mall:product:export')")
    @Log(title = "商品", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ApmStoreProduct apmStoreProduct) {
        List<ApmStoreProduct> list = apmStoreProductService.selectCateProducts(apmStoreProduct);
        ExcelUtil<ApmStoreProduct> util = new ExcelUtil<>(ApmStoreProduct.class);
        return util.exportExcel(list, "product");
    }

    /**
     * 获取商品详细信息
     */
    @PreAuthorize("@ss.hasPermission('mall:product:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(apmStoreProductService.selectApmStoreProductById(id));
    }

    /**
     * 新增商品
     */
    @PreAuthorize("@ss.hasPermission('mall:product:add')")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ApmStoreProduct apmStoreProduct) {
        apmStoreProduct.setCategory(ProductCategory.GENERAL_PRODUCT.getName());
        return toAjax(apmStoreProductService.insertApmStoreProduct(apmStoreProduct));
    }

    /**
     * 修改商品
     */
    @PreAuthorize("@ss.hasPermission('mall:product:edit')")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ApmStoreProduct apmStoreProduct) {
        return toAjax(apmStoreProductService.updateApmStoreProduct(apmStoreProduct));
    }

    /**
     * 删除商品
     */
    @PreAuthorize("@ss.hasPermission('mall:product:remove')")
    @Log(title = "商品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(apmStoreProductService.deleteApmStoreProductByIds(ids));
    }

    /**
     * 上传商品照片
     *
     * @param uploadBody 上传文件接收的参数
     * @return AjaxResult
     */
    @PostMapping("/web/image")
    public AjaxResult imageUpload(UploadBody uploadBody) throws IOException, InvalidExtensionException {
        System.out.println("原始文件：" + uploadBody.getOldUrl());
        if (!uploadBody.getImage().isEmpty()) {
            String url = sysUploadFileService.saveImage(uploadBody.getImage());
            if (url != null && StringUtils.isNotEmpty(uploadBody.getOldUrl())) {
                String domainPath = StringUtils.substring(ApmConfig.getDomainPath(), 0, ApmConfig.getDomainPath().length() - 1);
                String oldPath = uploadBody.getOldUrl().replace(domainPath + Constants.RESOURCE_PREFIX, ApmConfig.getProfile());
                FileUtils.deleteFile(oldPath);
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("imgUrl", url);
            return ajax;
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 上传闲置商品
     *
     * @param apmStoreProduct 商品信息
     */
    @PostMapping("/web/add")
    public AjaxResult add2(@RequestBody ApmStoreProduct apmStoreProduct) {
        apmStoreProduct.setCategory(ProductCategory.IDLE_PRODUCT.getName());
        return toAjax(apmStoreProductService.insertApmStoreProduct(apmStoreProduct));
    }

}
