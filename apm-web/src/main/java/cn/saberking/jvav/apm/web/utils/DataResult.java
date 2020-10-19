package cn.saberking.jvav.apm.web.utils;

import cn.saberking.jvav.apm.web.exception.code.BaseResponseCode;
import cn.saberking.jvav.apm.web.exception.code.ResponseCodeInterface;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @author Saber污妖王
 * TODO: 前后端分离统一数据封装类
 * @version 0.0.1
 * @editor Saber污妖王
 * @project company-frame
 * @date 2020/3/24
 * @package com.saber.king.utils
 */
@Data
@AllArgsConstructor
public class DataResult<T> {
    /**
     * 请求响应code，0为成功，其他表示失败
     */
    private int code;
    /**
     * 响应客户端的提示
     */
    private String msg;
    /**
     * 响应客户端的内容，code = 0 时返回数据
     */
    private T data;

    public DataResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public DataResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public DataResult() {
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.msg = BaseResponseCode.SUCCESS.getMsg();
        this.data = null;
    }

    public DataResult(T data) {
        this.data = data;
        this.code = BaseResponseCode.SUCCESS.getCode();
        this.msg = BaseResponseCode.SUCCESS.getMsg();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface) {
        this.data = null;
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
    }

    public DataResult(ResponseCodeInterface responseCodeInterface, T data) {
        this.data = data;
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
    }

    /**
     * 操作成功 data为null
     *
     * @return com.saber.king.utils.DataResult<T>
     * @Author: 小霍
     * @UpdateUser: Saber污妖王
     * @Version: 0.0.1
     */
    public static <T> DataResult<T> success() {
        return new DataResult<>();
    }

    /**
     * 操作成功 data 不为null
     *
     * @param data 结果
     * @return com.saber.king.utils.DataResult<T>
     * @Author: 小霍
     * @UpdateUser: Saber污妖王
     * @Version: 0.0.1
     */
    public static <T> DataResult<T> success(T data) {
        return new DataResult<>(data);
    }

    /**
     * 操作成功 data 为 null，指定提示信息
     * 注意：
     * 本方法依赖 com.saber.king.exception.code.BaseResponseCode 的固定枚举，
     * 若不用它可删除
     *
     * @param data  提示信息/数据（只有在 isMsg 为 false 即不是提示消息时才是数据）
     * @param isMsg 是否是提示消息
     * @return com.saber.king.utils.DataResult<T>
     */
    public static DataResult<String> success(String data, boolean isMsg) {
        if (isMsg) {
            return new DataResult<>(BaseResponseCode.SUCCESS.getCode(), data);
        } else {
            return new DataResult<>(data);
        }
    }

    /**
     * 自定义 返回操作 data 可控
     *
     * @param code 响应码
     * @param msg  提示
     * @param data 结果
     * @return com.saber.king.utils.DataResult<T>
     * @Author: 小霍
     * @UpdateUser: Saber污妖王
     * @Version: 0.0.1
     */
    public static <T> DataResult<T> getResult(int code, String msg, T data) {
        return new DataResult<>(code, msg, data);
    }

    /**
     * 自定义返回  data为null
     *
     * @param code 响应码
     * @param msg  提示
     * @return com.saber.king.utils.DataResult<T>
     * @Author: 小霍
     * @UpdateUser: Saber污妖王
     * @Version: 0.0.1
     */
    public static <T> DataResult<T> getResult(int code, String msg) {
        return new DataResult<>(code, msg);
    }

    /**
     * 自定义返回 入参一般是异常code枚举 data为空
     *
     * @param responseCode 响应码
     * @return com.saber.king.utils.DataResult<T>
     * @Author: 小霍
     * @UpdateUser: Saber污妖王
     * @Version: 0.0.1
     */
    public static <T> DataResult<T> getResult(BaseResponseCode responseCode) {
        return new DataResult<>(responseCode);
    }

    /**
     * 自定义返回 入参一般是异常code枚举 data 可控
     *
     * @param responseCode 响应码
     * @param data         结果
     * @return com.saber.king.utils.DataResult<T>
     * @Author: 小霍
     * @UpdateUser: Saber污妖王
     * @Version: 0.0.1
     */
    public static <T> DataResult<T> getResult(BaseResponseCode responseCode, T data) {
        return new DataResult<>(responseCode, data);
    }
}
