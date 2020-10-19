package cn.saberking.jvav.apm.web.utils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Saber污妖王
 * TODO: 类型转换工具
 * @version 0.0.1
 * @editor Saber污妖王
 * @project company-frame
 * @date 2020/5/2
 * @package com.saber.king.utils
 */
public class CastUtils {
    /**
     * 将一个不带泛型的 List 类型转换成带泛型的 List<T>
     *
     * @param obj   需要转换的 List
     * @param clazz 你要传递的泛型
     * @param <T>   泛型
     * @return 带泛型的 List<T>
     */
    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        if (obj instanceof List<?>) {
            return ((List<?>) obj).stream().map(clazz::cast).collect(Collectors.toList());
        }
        return null;
    }
}
