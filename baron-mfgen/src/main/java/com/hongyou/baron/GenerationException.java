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
package com.hongyou.baron;

import com.hongyou.baron.lang.BaseException;

import java.io.Serial;

/**
 * 代码生成器异常类
 *
 * @author Berlin
 */
public class GenerationException extends BaseException {

    /**
     * Serial Version UID
     */
    @Serial
    private static final long serialVersionUID = 653133727752015562L;

    /**
     * 打印符，打印的错误内容更直观
     */
    private static final String PRINT = "\n========================================================================================================================\n";

    /**
     * 捕获代码生成器异常
     *
     * @param pattern 异常消息
     * @param args 异常消息参数
     */
    public GenerationException(final String pattern, final Object... args) {
        super(GenerationException.PRINT + pattern + GenerationException.PRINT, args);
    }
}
