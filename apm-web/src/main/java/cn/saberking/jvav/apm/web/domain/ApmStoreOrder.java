package cn.saberking.jvav.apm.web.domain;

import cn.saberking.jvav.apm.web.domain.common.BaseDomain;
import cn.saberking.jvav.apm.web.vo.StoreCartVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单对象 apm_store_order
 *
 * @author apm
 * @date 2020-10-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApmStoreOrder extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private Integer id;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 额外订单号
     */
    private String extendOrderId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户姓名
     */
    private String realName;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 详细地址
     */
    private String userAddress;

    /**
     * 购物车id
     */
    private String cartId;

    /**
     * 运费金额
     */
    private BigDecimal freightPrice;

    /**
     * 订单商品总数
     */
    private Integer totalNum;

    /**
     * 订单总价
     */
    private BigDecimal totalPrice;

    /**
     * 邮费
     */
    private BigDecimal totalPostage;

    /**
     * 实际支付金额
     */
    private BigDecimal payPrice;

    /**
     * 支付邮费
     */
    private BigDecimal payPostage;

    /**
     * 抵扣金额
     */
    private BigDecimal deductionPrice;

    /**
     * 优惠券id
     */
    private Integer couponId;

    /**
     * 优惠券金额
     */
    private BigDecimal couponPrice;

    /**
     * 支付状态（0已支付 1未支付）
     */
    private Integer paid;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 支付方式
     */
    private String payType;

    /**
     * 创建时间
     */
    private Date addTime;

    /**
     * 订单状态（-1申请退款 -2退货成功 0待发货 1待收货 2已收货 3待评价）
     */
    private Integer status;

    /**
     * 退货状态（0未退款 1申请中 2已退款）
     */
    private Integer refundStatus;

    /**
     * 退款图片
     */
    private String refundReasonWapImg;

    /**
     * 退款用户说明
     */
    private String refundReasonWapExplain;

    /**
     * 退款时间
     */
    private Date refundReasonTime;

    /**
     * 前台退款原因
     */
    private String refundReasonWap;

    /**
     * 不退款的理由
     */
    private String refundReason;

    /**
     * 退款金额
     */
    private BigDecimal refundPrice;

    /**
     * 快递公司编号
     */
    private String deliverySn;

    /**
     * 快递名称/送货人姓名
     */
    private String deliveryName;

    /**
     * 发货类型
     */
    private String deliveryType;

    /**
     * 快递单号/手机号
     */
    private String deliveryId;

    /**
     * 消费赚取积分
     */
    private BigDecimal gainIntegral;

    /**
     * 使用积分
     */
    private BigDecimal useIntegral;

    /**
     * 给用户退了多少积分
     */
    private BigDecimal backIntegral;

    /**
     * 备注
     */
    private String mark;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 唯一id(md5加密)类似id
     */
    private String unique;

    /**
     * $column.columnComment
     */
    private Integer isMerCheck;

    /**
     * 成本价
     */
    private BigDecimal cost;

    /**
     * 核销码
     */
    private String verifyCode;

    /**
     * 门店id
     */
    private Long storeId;

    /**
     * 配送方式 1=快递 ，2=门店自提
     */
    private Integer shippingType;

    /**
     * 支付渠道(0微信公众号1微信小程序)
     */
    private Integer isChannel;

    /**
     * $column.columnComment
     */
    private Integer isRemind;

    /**
     * $column.columnComment
     */
    private Integer isSystemDel;

    /**
     * 订单id
     */
    private Integer productId;

    /**
     * 购物详情
     */
    private String cartInfo;

    /**
     * 购物车
     */
    @JsonIgnore
    private StoreCartVO cart;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("extendOrderId", getExtendOrderId())
                .append("userId", getUserId())
                .append("realName", getRealName())
                .append("userPhone", getUserPhone())
                .append("userAddress", getUserAddress())
                .append("cartId", getCartId())
                .append("freightPrice", getFreightPrice())
                .append("totalNum", getTotalNum())
                .append("totalPrice", getTotalPrice())
                .append("totalPostage", getTotalPostage())
                .append("payPrice", getPayPrice())
                .append("payPostage", getPayPostage())
                .append("deductionPrice", getDeductionPrice())
                .append("couponId", getCouponId())
                .append("couponPrice", getCouponPrice())
                .append("paid", getPaid())
                .append("payTime", getPayTime())
                .append("payType", getPayType())
                .append("addTime", getAddTime())
                .append("status", getStatus())
                .append("refundStatus", getRefundStatus())
                .append("refundReasonWapImg", getRefundReasonWapImg())
                .append("refundReasonWapExplain", getRefundReasonWapExplain())
                .append("refundReasonTime", getRefundReasonTime())
                .append("refundReasonWap", getRefundReasonWap())
                .append("refundReason", getRefundReason())
                .append("refundPrice", getRefundPrice())
                .append("deliverySn", getDeliverySn())
                .append("deliveryName", getDeliveryName())
                .append("deliveryType", getDeliveryType())
                .append("deliveryId", getDeliveryId())
                .append("gainIntegral", getGainIntegral())
                .append("useIntegral", getUseIntegral())
                .append("backIntegral", getBackIntegral())
                .append("mark", getMark())
                .append("isDel", getIsDel())
                .append("unique", getUnique())
                .append("remark", getRemark())
                .append("isMerCheck", getIsMerCheck())
                .append("cost", getCost())
                .append("verifyCode", getVerifyCode())
                .append("storeId", getStoreId())
                .append("shippingType", getShippingType())
                .append("isChannel", getIsChannel())
                .append("isRemind", getIsRemind())
                .append("isSystemDel", getIsSystemDel())
                .toString();
    }
}
