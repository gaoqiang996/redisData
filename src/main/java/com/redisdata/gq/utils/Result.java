package com.redisdata.gq.utils;

import lombok.Data;


/**
 * @author gq
 * @description:
 * @date 16:06 2021/12/28
 */
@Data
public class Result <T> {
    public Result(boolean success, int code) {
        this.setSuccess(success);
        this.setCode(code);
    }

    public Result(boolean success, int code, T data) {
        this.setSuccess(success);
        this.setCode(code);
        this.setData(data);
    }

    public Result(boolean success, int code, String msg) {
        this.setSuccess(success);
        this.setCode(code);
        this.setData(data);
    }


    /**
     * 请求是否成功
     * true:成功
     * false：失败
     */
    private boolean success;

    /**
     * 状态码
     * 成功：200
     * 失败：其他
     */
    private int code;

    /**
     * 失败状态码描述
     * 如果成功不返回
     * 失败返回状态码对应的msg消息
     */
    private String msg;

    /**
     * 请求数据的结果
     */
    private T data;


    public static <T> Result<T> success() {
        return new Result<T>(true, 200);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(true, 200, data);
    }


    public static <T> Result<T> fail() {
        return new Result<T>(false, 500);
    }



}
