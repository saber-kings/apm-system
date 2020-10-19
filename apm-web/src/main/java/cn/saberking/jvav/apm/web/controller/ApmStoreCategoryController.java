package cn.saberking.jvav.apm.web.controller;


import cn.saberking.jvav.apm.web.domain.ApmStoreCategory;
import cn.saberking.jvav.apm.web.service.ApmStoreCategoryService;
import cn.saberking.jvav.apm.web.utils.DataResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品分类Controller
 *
 * @author apm
 * @date 2020-10-06
 */
@RestController
@RequestMapping("/category")
public class ApmStoreCategoryController{
    @Resource
    private ApmStoreCategoryService apmStoreCategoryService;


    /**
     * 查询商品分类列表
     */
    @GetMapping("/web/list")
    public DataResult<List<ApmStoreCategory>> list() {
        return DataResult.success(apmStoreCategoryService.selectApmStoreCategoryList());
    }
}
