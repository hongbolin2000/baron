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
package com.hongyou.baron.schedule;

import com.hongyou.baron.exceptions.basic.BaseException;

import java.io.Serial;

/**
 * 系统任务运行异常类
 *
 * @author Berlin
 */
public class JobExecutionException extends BaseException {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = -206864329769254970L;

    /**
     * 捕获文本异常
     *
     * @param pattern 异常消息
     * @param args 异常消息参数
     */
    public JobExecutionException(final String pattern, final Object... args) {
        super(pattern, args);
    }

    /**
     * 捕获文本以及程序中抛出的异常
     *
     * @param message 异常消息
     */
    public JobExecutionException(final String message, final Throwable cause, final Object... args) {
        super(message, cause, args);
    }
}
