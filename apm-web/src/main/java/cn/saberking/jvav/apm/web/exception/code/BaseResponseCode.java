package cn.saberking.jvav.apm.web.exception.code;

import cn.saberking.jvav.apm.web.utils.file.FileUploadUtils;
import lombok.AllArgsConstructor;

/**
 * @author Saber污妖王
 * TODO: 封装统一的相应 code 的枚举
 * @version 0.0.1
 * @editor Saber污妖王
 * @project company-frame
 * @date 2020/3/24
 * @package com.saber.king.exception.code
 */
@AllArgsConstructor
public enum BaseResponseCode implements ResponseCodeInterface {
    /**
     * 这个要和前端约定好：
     * code=200：服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
     * code=400：（授权异常） 请求要求身份验证。 客户端需要跳转到登录页面重新登录。
     * code=601：(凭证过期) 客户端请求刷新凭证接口。
     * code=60x：系统主动抛出的业务异常。
     * code=500：系统异常。
     */
    SUCCESS(200, "操作成功"),
    SYSTEM_ERROR(500, "系统异常，请稍后再试"),
    AUTHORIZE_ERROR(400, "登陆失败，请重试"),
    TOKEN_ERROR(400, "用户未登录，请重新登录"),
    TOKEN_NOT_NULL(400, "token 不能为空"),
    TOKEN_PAST_DUE_INVALID(601, "token 失效，请重新登陆"),
    DATA_ERROR(602, "传入数据异常"),
    DATA_NOT_NULL(602, "必传数据不能为空"),
    METHOD_IDENTITY_ERROR(603, "数据校验异常"),
    ACCOUNT_NOT_FOUND(604, "该账号不存在"),
    ACCOUNT_LOCK(604, "该账号被锁定,请联系系统管理员"),
    ACCOUNT_PASSWORD_ERROR(604, "用户名密码不匹配"),
    OLD_PASSWORD_ERROR(604, "旧密码不匹配"),
    ACCOUNT_LOCK_TIP(604, "该账号被锁定,请联系系统管理员"),
    ACCOUNT_HAS_DELETED_ERROR(604, "该账号已被删除，请联系系统管理员"),
    OPERATION_ERROR(605, "操作失败"),
    CART_NOT_FOUND(606, "该商品不存在"),
    CART_GOOD_NOT_FOUND(606, "该商品不存在"),
    CART_GOOD_SUK_NOT_FOUND(606, "该属性商品不存在"),
    CART_GOOD_STOCK_NOT_ENOUGH(606, "该商品库存不足"),
    UPLOAD_FILE_ERROR(607, "文件上传失败"),
    UPLOAD_FILE_TOO_LARGE(607, "上传的文件大小超出范围"),
    UPLOAD_FILE_NOT_NULL(607, "没有接收到文件"),
    UPLOAD_FILE_NAME_NOT_NULL(607, "文件名称不能为空"),
    FILE_NAME_LENGTH_LIMIT_EXCEEDED(607, "文件名称长度不能超过"+ FileUploadUtils.DEFAULT_FILE_NAME_LENGTH),
    FILE_NOT_FOUND(607, "文件没有找到"),
    FILE_TYPE_ERROR(607, "文件类型错误"),
    PAY_ERROR(608, "支付失败，请重试"),
    PAY_NOT_NULL(608, "该交易不存在"),
    ;

    /**
     * 响应码
     */
    private final int code;

    /**
     * 提示
     */
    private final String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
