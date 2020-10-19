package cn.saberking.jvav.apm.web.domain;

import cn.saberking.jvav.apm.web.domain.common.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品对象 apm_store_product
 *
 * @author apm
 * @date 2020-10-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApmStoreProduct extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private Integer id;

    /**
     * 商户Id(0为总后台管理员创建,不为0的时候是商户后台创建)
     */
    private Integer merId;

    /**
     * 某种类型的商品(普通商品、二手闲置商品)
     */
    private String category;

    /**
     * 商品图片
     */
    private String image;

    /**
     * 轮播图
     */
    private String sliderImage;

    /**
     * 商品名称
     */
    private String storeName;

    /**
     * 商品简介
     */
    private String storeInfo;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 产品条码（一维码）
     */
    private String barCode;

    /**
     * 分类id
     */
    private String cateId;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 会员价格
     */
    private BigDecimal vipPrice;

    /**
     * 市场价
     */
    private BigDecimal otPrice;

    /**
     * 邮费
     */
    private BigDecimal postage;

    /**
     * 单位名
     */
    private String unitName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 状态（0：未上架，1：上架）
     */
    private Integer isShow;

    /**
     * 是否热卖
     */
    private Integer isHot;

    /**
     * 是否优惠
     */
    private Integer isBenefit;

    /**
     * 是否精品
     */
    private Integer isBest;

    /**
     * 是否新品
     */
    private Integer isNew;

    /**
     * 产品描述
     */
    private String description;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 是否包邮
     */
    private Integer isPostage;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 商户是否代理 0不可代理1可代理
     */
    private Integer merUse;

    /**
     * 获得积分
     */
    private BigDecimal giveIntegral;

    /**
     * 成本价
     */
    private BigDecimal cost;

    /**
     * 是否优品推荐
     */
    private Integer isGood;

    /**
     * 虚拟销量
     */
    private Integer ficti;

    /**
     * 浏览量
     */
    private Long browse;

    /**
     * 产品二维码地址(用户小程序海报)
     */
    private String codePath;

    /**
     * 淘宝京东1688类型
     */
    private String soureLink;

    /**
     * 商品属性
     */
    private String result;

    private List<String> attrNames;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("merId", getMerId())
                .append("category", getCategory())
                .append("image", getImage())
                .append("sliderImage", getSliderImage())
                .append("storeName", getStoreName())
                .append("storeInfo", getStoreInfo())
                .append("keyword", getKeyword())
                .append("barCode", getBarCode())
                .append("cateId", getCateId())
                .append("price", getPrice())
                .append("vipPrice", getVipPrice())
                .append("otPrice", getOtPrice())
                .append("postage", getPostage())
                .append("unitName", getUnitName())
                .append("sort", getSort())
                .append("sales", getSales())
                .append("stock", getStock())
                .append("isShow", getIsShow())
                .append("isHot", getIsHot())
                .append("isBenefit", getIsBenefit())
                .append("isBest", getIsBest())
                .append("isNew", getIsNew())
                .append("description", getDescription())
                .append("addTime", getAddTime())
                .append("isPostage", getIsPostage())
                .append("isDel", getIsDel())
                .append("merUse", getMerUse())
                .append("giveIntegral", getGiveIntegral())
                .append("cost", getCost())
                .append("isGood", getIsGood())
                .append("ficti", getFicti())
                .append("browse", getBrowse())
                .append("codePath", getCodePath())
                .append("soureLink", getSoureLink())
                .toString();
    }
}
