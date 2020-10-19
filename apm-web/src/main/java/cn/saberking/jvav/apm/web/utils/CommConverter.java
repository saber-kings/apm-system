package cn.saberking.jvav.apm.web.utils;


import org.springframework.cglib.core.Converter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Saber污妖王
 * TODO: 自定义类型转换器，配合 CGLIB 的 BeanCopier 使用
 * @version 0.0.1
 * @editor Saber污妖王
 * @project company-frame
 * @date 2020/6/10
 * @package com.saber.king.utils
 */
public class CommConverter implements Converter {
    /**
     * 时间转换的格式
     */
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 自定义属性转换
     *
     * @param value   源对象属性类
     * @param target  目标对象里属性对应set方法名, eg.setId
     * @param context 目标对象属性类
     * @return 类型转换后的值
     */
    @Override
    public Object convert(Object value, Class target, Object context) {
        if (value instanceof Integer) {
            return value;
        } else if (value instanceof Byte) {
            return value;
        } else if (value instanceof Short) {
            return value;
        } else if (value instanceof Long) {
            return value;
        } else if (value instanceof Float) {
            return value;
        } else if (value instanceof Double) {
            return value;
        } else if (value instanceof Boolean) {
            return value;
        } else if (value instanceof LocalDateTime) {
            LocalDateTime date = (LocalDateTime) value;
            return dtf.format(date);
        } else if (value instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal) value;
            return bd.toPlainString();
        }
        // 更多类型转换请自定义
        return value;
    }
}
