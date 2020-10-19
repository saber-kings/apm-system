package cn.saberking.jvav.apm.web.domain;

import cn.saberking.jvav.apm.web.domain.common.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户地址对象 apm_user_address
 * 
 * @author apm
 * @date 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApmUserAddress extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /** 用户地址id */
    private Integer id;

    /** 用户id */
    private Integer userId;

    /** 收货人姓名 */
    private String realName;

    /** 收货人电话 */
    private String phone;

    /** 收货人所在省 */
    private String province;

    /** 收货人所在市 */
    private String city;

    /** 收货人所在区 */
    private String district;

    /** 收货人详细地址 */
    private String detail;

    /** 邮编 */
    private String postCode;

    /** 经度 */
    private String longitude;

    /** 纬度 */
    private String latitude;

    /** 是否默认 */
    private Integer isDefault;

    /** 是否删除 */
    private Integer isDel;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("realName", getRealName())
            .append("phone", getPhone())
            .append("province", getProvince())
            .append("city", getCity())
            .append("district", getDistrict())
            .append("detail", getDetail())
            .append("postCode", getPostCode())
            .append("longitude", getLongitude())
            .append("latitude", getLatitude())
            .append("isDefault", getIsDefault())
            .append("isDel", getIsDel())
            .append("createTime", getCreateTime())
            .toString();
    }
}
