/*
 * Copyright 2024, Hongyou Software Development Studio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hongyou.baron.exceptions.basic;

import com.hongyou.baron.util.StringUtil;

import java.io.Serial;

/**
 * 定义程序抛出的运行时类型异常基础类
 *
 * @author Berlin
 */
public class BaseRuntimeException extends RuntimeException {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 2523739898565466972L;

    /**
     * 仅仅捕获文本异常消息
     *
     * @param pattern 异常消息
     * @param  args 异常消息参数
     */
    public BaseRuntimeException(final String pattern, final Object... args) {
        super(StringUtil.format(pattern, args));
    }

    /**
     * 捕获文本以及程序中抛出的异常
     *
     * @param pattern 异常消息
     * @param cause 异常原因
     * @param  args 异常消息参数
     */
    public BaseRuntimeException(final String pattern, Throwable cause, final Object... args) {
        super(StringUtil.format(pattern, args), cause);
    }
}
