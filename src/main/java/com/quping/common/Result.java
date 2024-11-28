package com.quping.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 统一封装结果类
 * @author: Punny
 * @date: 2024/8/25 23:22
 */
@Data
@AllArgsConstructor
public class Result<T> {
    /**
     * 状态码
     */
    private String code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;
    /**
     * 分页用
     */
    private PageInfo pageInfo;

    Result(String code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> page(T data,PageInfo pageInfo){
        return new Result<>("1","ok",data,pageInfo);
    }

    public static <T> Result<T> ok(T data){
        return new Result<>("1","ok",data);
    }

    public static <T> Result<T> ok(){
        return new Result<>("1","ok",null);
    }

    public static <T> Result<T> fail(T data){
        return new Result<>("-1","fail",data);
    }

    public static <T> Result<T> fail(){
        return new Result<>("-1","fail",null);
    }
    public static <T> Result<T> failWithMsg(String msg){
        return new Result<>("-1",msg,null);
    }
}
