package com.quping.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T>{
    private Long total = 0L;
    private List<T> dataList;
}
