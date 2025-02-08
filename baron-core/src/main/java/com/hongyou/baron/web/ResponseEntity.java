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
public class ResponseEntity {

    /**
     * 成功/错误编号
     */
    @Builder.Default
    private int code = 200;

    /**
     * 成功/错误消息
     */
    @Builder.Default
    private String message = "Success";

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 成功
     */
    public static final ResponseEntity SUCCESS = ResponseEntity.builder().build();
}
