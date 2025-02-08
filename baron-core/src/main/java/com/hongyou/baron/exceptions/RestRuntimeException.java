/*
 * Copyright 2024, Hongyou Software Development Studio.
 */
package com.hongyou.baron.exceptions;

import com.hongyou.baron.exceptions.basic.BaseRuntimeException;

import java.io.Serial;

/**
 * Restful异常类
 *
 * @author Berlin
 */
public class RestRuntimeException extends BaseRuntimeException {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 6059682076269147062L;

    /**
     * 捕获文本异常
     *
     * @param pattern 异常消息
     * @param args 异常消息参数
     */
    public RestRuntimeException(final String pattern, final Object... args) {
        super(pattern, args);
    }

    /**
     * 捕获文本以及程序中抛出的异常
     *
     * @param message 异常消息
     */
    public RestRuntimeException(final String message, final Throwable cause, final Object... args) {
        super(message, cause, args);
    }
}
