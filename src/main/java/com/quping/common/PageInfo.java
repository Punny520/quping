package com.quping.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 分页查询信息类
 */
@Data
@Slf4j
public class PageInfo {
    /**
     * 页码
     */
    private Integer pageNumber;

    /**
     * 每页的数据条数
     */
    private Integer pageSize;

    /**
     * 偏移量
     */
    private Integer offset;

    PageInfo(){
        this.pageNumber = 1;
        this.pageSize = 10;
        this.offset = 0;
    }

    public void setOffset(){
        log.info("set Index");
        this.offset = (pageNumber - 1) * pageSize;
    }

    public void setPageNumber(Integer pageNumber) {
        log.info("set pageNumber:{}",pageNumber);
        if(pageNumber <= 0) pageNumber = 1;
        this.pageNumber = pageNumber;
        setOffset();
    }

    public void setPageSize(Integer pageSize) {
        log.info("set pageSize:{}",pageSize);
        if(pageSize <= 0) pageSize = 10;
        this.pageSize = pageSize;
        setOffset();
    }
}
