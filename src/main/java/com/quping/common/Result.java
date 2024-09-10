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
public class Result {
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
    private Object data;
    public static Result ok(Object data){
        return new Result("1","ok",data);
    }

    public static Result ok(){
        return new Result("1","ok",null);
    }

    public static Result fail(Object data){
        return new Result("-1","fail",data);
    }

    public static Result fail(){
        return new Result("-1","fail",null);
    }
    public static Result failWithMsg(String msg){
        return new Result("-1",msg,null);
    }
}
