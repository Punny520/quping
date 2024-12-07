package com.quping.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 分页查询信息类
 */
@Data
@Slf4j
public class PageInfo{
    /**
     * 页码
     */
    private Integer pageNumber = 1;
    /**
     * 每页的数据条数
     */
    private Integer pageSize = 10;
    /**
     * 查询条件，目前只做字符串匹配
     */
    private String condition;

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber >= 1 ? pageNumber : 1;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize >= 1 ? pageSize : 10;
    }
}
