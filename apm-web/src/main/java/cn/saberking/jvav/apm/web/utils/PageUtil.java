package cn.saberking.jvav.apm.web.utils;

import cn.saberking.jvav.apm.web.vo.PageRespVO;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author Saber污妖王
 * TODO: 分页相关的工具类
 * @version 0.0.1
 * @editor Saber污妖王
 * @project company-frame
 * @date 2020/3/25
 * @package com.saber.king.utils
 */
public class PageUtil {
    private PageUtil() {
    }

    public static <T> PageRespVO<T> getPageVO(List<T> list) {
        PageRespVO<T> result = new PageRespVO<>();
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            result.setTotalRows(page.getTotal());
            result.setTotalPages(page.getPages());
            result.setPageNum(page.getPageNum());
            result.setCurPageSize(page.size());
            result.setPageSize(page.getPageSize());
            if (page.getResult()!=null) {
                result.setList(page.getResult());
            }
        }
        return result;
    }
}
