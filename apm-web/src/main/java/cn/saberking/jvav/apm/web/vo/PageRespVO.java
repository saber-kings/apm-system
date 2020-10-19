package cn.saberking.jvav.apm.web.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Saber污妖王
 * TODO 响应分页查询请求的数据封装类
 * @version 0.0.1
 * @editor Saber污妖王
 * @project company-frame
 * @date 2020/3/25
 * @package com.saber.king.vo.resp
 */
@Data
public class PageRespVO<T> {
    /**
     * 总记录数
     */
    private Long totalRows;

    /**
     * 总页数
     */
    private Integer totalPages;

    /**
     * 当前第几页
     */
    private Integer pageNum;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 当前页记录数
     */
    private Integer curPageSize;

    /**
     * 数据列表
     */
    private List<T> list;
}

