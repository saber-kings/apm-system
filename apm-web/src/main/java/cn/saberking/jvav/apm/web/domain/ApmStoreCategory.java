package cn.saberking.jvav.apm.web.domain;

import cn.saberking.jvav.apm.web.domain.common.TreeDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 商品分类对象 apm_store_category
 *
 * @author apm
 * @date 2020-10-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApmStoreCategory extends TreeDomain {
    private static final long serialVersionUID = 1L;

    /**
     * 商品分类表ID
     */
    private Integer id;

    /**
     * 父id
     */
    private Integer pid;

    /**
     * 分类名称
     */
    private String cateName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标
     */
    private String pic;

    /**
     * 是否推荐
     */
    private Integer isShow;

    /**
     * 添加时间
     */
    private Date addTime;

    /**
     * 删除状态
     */
    private Integer isDel;

    /**
     * 子分类
     */
    private List<ApmStoreCategory> subCategoryList;

    /**
     * 该分类下的所有商品
     */
    private List<ApmStoreProduct> productList;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("pid", getPid())
                .append("cateName", getCateName())
                .append("sort", getSort())
                .append("pic", getPic())
                .append("isShow", getIsShow())
                .append("addTime", getAddTime())
                .append("isDel", getIsDel())
                .toString();
    }
}
