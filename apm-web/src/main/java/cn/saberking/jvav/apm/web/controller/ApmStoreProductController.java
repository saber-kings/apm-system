package cn.saberking.jvav.apm.web.controller;

import cn.saberking.jvav.apm.web.config.ApmConfig;
import cn.saberking.jvav.apm.web.domain.ApmStoreCategory;
import cn.saberking.jvav.apm.web.domain.ApmStoreProduct;
import cn.saberking.jvav.apm.web.enums.ProductCategory;
import cn.saberking.jvav.apm.web.exception.InvalidExtensionException;
import cn.saberking.jvav.apm.web.service.ApmStoreProductService;
import cn.saberking.jvav.apm.web.utils.DataResult;
import cn.saberking.jvav.apm.web.utils.file.FileUploadUtils;
import cn.saberking.jvav.apm.web.utils.file.MimeTypeUtils;
import cn.saberking.jvav.apm.web.vo.PageRespVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 商品Controller
 *
 * @author apm
 * @date 2020-10-06
 */
@Slf4j
@RestController
@RequestMapping("/product")
public class ApmStoreProductController {
    @Resource
    private ApmStoreProductService apmStoreProductService;

    /**
     * 查询商品列表
     */
    @GetMapping("/list")
    public DataResult<PageRespVO<ApmStoreProduct>> list(ApmStoreProduct product) {
        return DataResult.success(apmStoreProductService.selectProductList(product));
    }

    /**
     * 获取商品详细信息
     */
    @GetMapping(value = "/{id}")
    public DataResult<ApmStoreProduct> getInfo(@PathVariable("id") Integer id) {
        return DataResult.success(apmStoreProductService.selectById(id));
    }

    /**
     * 查询首页推荐商品列表
     */
    @GetMapping("/web/list")
    public DataResult<List<ApmStoreCategory>> listRecommend(@RequestParam(value = "size", defaultValue = "8") Integer pageSize) {
        return DataResult.success(apmStoreProductService.selectShowProduct(pageSize));
    }

    /**
     * 查询商品详情信息
     *
     * @param id 商品id
     */
    @GetMapping("/web/{id}")
    public DataResult<ApmStoreProduct> getDetail(@PathVariable(value = "id") Integer id) {
        return DataResult.success(apmStoreProductService.selectById(id));
    }

    /**
     * 上传商品照片
     *
     * @param file 图片
     * @return AjaxResult
     */
    @PostMapping("/web/image")
    public DataResult<String> imageUpload(MultipartFile file) {
        String upload = null;
        try {
            upload = FileUploadUtils.upload(ApmConfig.getUploadPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
            log.info("文件上传成功：{}", upload);
        } catch (IOException e) {
            log.warn("商品图片上传失败：{}", e.getMessage());
        } catch (InvalidExtensionException e) {
            log.warn("不合法的文件扩展名：{}", e.getMessage());
        }
        return DataResult.success(upload);
    }

    /**
     * 上传闲置商品
     *
     * @param apmStoreProduct 商品信息
     */
    @PostMapping("/web/add")
    public DataResult<ApmStoreProduct> add2(@RequestBody ApmStoreProduct apmStoreProduct) {
        apmStoreProduct.setCategory(ProductCategory.IDLE_PRODUCT.getName());
        return DataResult.success(apmStoreProductService.add(apmStoreProduct));
    }

}
