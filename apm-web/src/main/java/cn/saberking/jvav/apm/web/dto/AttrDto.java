package cn.saberking.jvav.apm.web.dto;

import lombok.Data;

import java.util.List;

/**
 * 所有属性的实体
 * @author saber-kings
 */
@Data
public class AttrDto {
    /**
     * 属性是否隐藏
     */
    private  boolean attrHidden;

    /**
     * 属性详情
     */
    private  String detailValue;

    /**
     * 每个属性所有的值
     */
    private List<String> detail;

    /**
     * 属性名称
     */
    private String value;
}
