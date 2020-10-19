package cn.saberking.jvav.apm.web.service.impl;

import cn.saberking.jvav.apm.web.domain.ApmStoreCategory;
import cn.saberking.jvav.apm.web.domain.ApmStoreProduct;
import cn.saberking.jvav.apm.web.domain.ApmStoreProductAttr;
import cn.saberking.jvav.apm.web.enums.ProductCategoryIsDel;
import cn.saberking.jvav.apm.web.enums.ProductCategoryIsShow;
import cn.saberking.jvav.apm.web.exception.BusinessException;
import cn.saberking.jvav.apm.web.exception.code.BaseResponseCode;
import cn.saberking.jvav.apm.web.mapper.ApmStoreProductAttrMapper;
import cn.saberking.jvav.apm.web.mapper.ApmStoreProductMapper;
import cn.saberking.jvav.apm.web.service.ApmStoreCategoryService;
import cn.saberking.jvav.apm.web.service.ApmStoreProductService;
import cn.saberking.jvav.apm.web.utils.PageUtil;
import cn.saberking.jvav.apm.web.vo.PageRespVO;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wyx
 * @version 1.0
 * @date 2020/10/11
 */
@Slf4j
@Service
public class ApmStoreProductServiceImpl implements ApmStoreProductService {
    @Resource
    private ApmStoreProductMapper apmStoreProductMapper;

    @Resource
    private ApmStoreProductAttrMapper attrMapper;

    @Resource
    private ApmStoreCategoryService categoryService;

    /**
     * 根据商品id获取商品详情列表
     * @param id 商品ID
     * @return 结果
     */
    @Override
    public ApmStoreProduct selectById(Integer id) {
        ApmStoreProduct product = apmStoreProductMapper.selectProductInfoById(id);
        if (product != null) {
            List<ApmStoreProductAttr> attrs = attrMapper.selectByProductId(id);
            List<String> attrNames = attrs.stream().map(ApmStoreProductAttr::getAttrName).collect(Collectors.toList());
            product.setAttrNames(attrNames);
        }
        return product;
    }

    /**
     * 查询商品列表
     *
     * @param product 商品
     * @return 商品
     */
    @Override
    public PageRespVO<ApmStoreProduct> selectProductList(ApmStoreProduct product) {
        //Mapper接口方式的调用，推荐这种使用方式。
        PageHelper.startPage(product.getPageNum(), product.getPageSize());
        List<ApmStoreProduct> products = apmStoreProductMapper.selectApmStoreProductList(product);
        return PageUtil.getPageVO(products);
    }

    /**
     * 获取首页各种分类的推荐商品数据
     *
     * @return 商品数据
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<ApmStoreCategory> selectShowProduct(Integer pageSize) {
        // 获取所有非一级分类
        ApmStoreCategory apmStoreCategory = new ApmStoreCategory();
        // 没有删除的
        apmStoreCategory.setIsDel(ProductCategoryIsDel.NO.getCode());
        // 推荐的
        apmStoreCategory.setIsShow(ProductCategoryIsShow.YES.getCode());
        List<ApmStoreCategory> list = categoryService.selectSecondCategoryList(apmStoreCategory);
        log.info("获取的所有菜单数据：{}", JSON.toJSON(list));

        // 根据二级分类查询指定条数商品数据
        selectProductByCategory(list, pageSize);
        return list;
    }

    /**
     * 新增商品
     *
     * @param apmStoreProduct 商品
     * @return 结果
     */
    @Override
    public ApmStoreProduct add(ApmStoreProduct apmStoreProduct) {
        int i = apmStoreProductMapper.insertApmStoreProduct(apmStoreProduct);
        if (i == 0) {
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        return apmStoreProduct;
    }

    /**
     * 修改商品
     *
     * @param apmStoreProduct 商品
     * @return 结果
     */
    @Override
    public int update(ApmStoreProduct apmStoreProduct) {
        return apmStoreProductMapper.updateApmStoreProduct(apmStoreProduct);
    }

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的商品ID
     * @return 结果
     */
    @Override
    public int deleteByIds(Integer[] ids) {
        return apmStoreProductMapper.deleteApmStoreProductByIds(ids);
    }

    /**
     * 删除商品信息
     *
     * @param id 商品ID
     * @return 结果
     */
    @Override
    public int deleteProductById(Integer id) {
        return apmStoreProductMapper.deleteApmStoreProductById(id);
    }


    /**
     * 根据分类id查询前 pageSize 个商品
     * @param categories 分类集合
     * @param pageSize 查询条数
     */
    private void selectProductByCategory(List<ApmStoreCategory> categories, Integer pageSize) {
        for (ApmStoreCategory category : categories) {
            //获取当前分类id
            Integer cateId = category.getId();
            //创建商品，存储查询参数
            ApmStoreProduct product = new ApmStoreProduct();
            //推荐的
            product.setIsShow(ProductCategoryIsShow.YES.getCode());
            //该分类下的
            product.setCateId(cateId + "");
            PageHelper.startPage(1, pageSize);
            List<ApmStoreProduct> products = apmStoreProductMapper.selectApmStoreProductList(product);
            products = products.stream().sorted(Comparator.comparingInt(ApmStoreProduct::getSort)).collect(Collectors.toList());
            category.setProductList(products);
        }
    }
}
