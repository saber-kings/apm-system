package cn.saberking.jvav.apm.web.vo;

import cn.saberking.jvav.apm.web.dto.AttrDto;
import cn.saberking.jvav.apm.web.dto.ValueDto;
import lombok.Data;

import java.util.List;

/**
 * @author Saber污妖王
 * TODO: 商品属性和属性组合VO
 * @version 0.0.1
 * @editor Saber污妖王
 * @project apm-web
 * @date 2020/10/13
 * @package cn.saberking.jvav.apm.web.vo
 */
@Data
public class ProductAttrVO {
    private List<AttrDto> attrs;

    private List<ValueDto> values;
}
