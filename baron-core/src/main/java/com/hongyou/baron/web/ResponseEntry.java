/*
 * Copyright 2024, Hongyou Software Development Studio.
 */
package com.hongyou.baron.web;

import lombok.Builder;
import lombok.Data;

/**
 * Rest（HTTP）请求返回结果
 *
 * @author Berlin
 */
@Data
@Builder
public class ResponseEntry {

    /**
     * 成功/错误编号
     */
    @Builder.Default
    private int code = SUCCESS_CODE;

    /**
     * 成功编号
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 函数执行时捕捉到异常出错
     */
    public static final int UNKNOWN_CODE = Integer.MAX_VALUE;

    /**
     * 成功/错误消息
     */
    @Builder.Default
    private String message = "Success";

    /**
     * 返回数据内容
     */
    private Object body;

    /**
     * 成功
     */
    public static final ResponseEntry SUCCESS = ResponseEntry.builder().build();
}
