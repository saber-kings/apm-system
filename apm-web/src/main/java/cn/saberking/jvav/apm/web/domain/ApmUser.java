package cn.saberking.jvav.apm.web.domain;

import cn.saberking.jvav.apm.web.domain.common.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户管理对象 apm_user
 *
 * @author apm
 * @date 2020-10-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApmUser extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户类型（00普通用户）
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 身份证号
     */
    private String cardId;

    /**
     * 剩余积分
     */
    private BigDecimal integral;

    /**
     * 连续签到天数
     */
    private Long signNum;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 购买次数
     */
    private Integer payCount;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 登陆类型
     */
    private String loginType;

    /**
     * token
     */
    private String token;

    /**
     * 验证码
     */
    @JsonIgnore
    private String code;

    /**
     * 唯一标识
     */
    @JsonIgnore
    private String uuid = "";

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("userName", getUserName())
                .append("nickName", getNickName())
                .append("userType", getUserType())
                .append("email", getEmail())
                .append("phoneNumber", getPhoneNumber())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("password", getPassword())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("realName", getRealName())
                .append("birthday", getBirthday())
                .append("cardId", getCardId())
                .append("integral", getIntegral())
                .append("signNum", getSignNum())
                .append("level", getLevel())
                .append("payCount", getPayCount())
                .append("address", getAddress())
                .append("loginType", getLoginType())
                .append("remark", getRemark())
                .toString();
    }
}

