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
package com.hongyou.baron.logging;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;

/**
 * 日志
 *
 * @author Berlin
 */
public class Log {

    /**
     * 日志
     */
    private final Logger logger;

    /**
     * 创建日志
     */
    public Log(final Logger logger) {
        this.logger = logger;
    }

    /**
     * 打印普通级别的日志
     *
     * @param pattern 日志消息，可以是普通的字符串，也可以是字符串模板（info: {}）
     * @param args 消息参数
     */
    public void info(final String pattern, Object... args) {
        if (logger.isInfoEnabled()) {
            this.logger.info(StrUtil.format(pattern, args));
        }
    }

    /**
     * 打印警告级别的日志
     *
     * @param pattern 日志消息，可以是普通的字符串，也可以是字符串模板（warn: {}）
     * @param args 消息参数
     */
    public void warn(final String pattern, Object... args) {
        if (logger.isWarnEnabled()) {
            this.logger.warn(StrUtil.format(pattern, args));
        }
    }

    /**
     * 打印异常级别的日志
     *
     * @param pattern 日志消息，可以是普通的字符串，也可以是字符串模板（error: {}）
     * @param args 消息参数
     */
    public void error(final String pattern, Object... args) {
        if (logger.isErrorEnabled()) {
            this.logger.error(StrUtil.format(pattern, args));
        }
    }

    /**
     * 打印异常级别带异常原因的日志
     *
     * @param pattern 日志消息，可以是普通的字符串，也可以是字符串模板（error: {}）
     * @param args 消息参数
     */
    public void error(final String pattern, Throwable cause, Object... args) {
        if (logger.isErrorEnabled()) {
            this.logger.error(StrUtil.format(pattern, args), cause);
        }
    }

    /**
     * 打印调试级别的日志
     *
     * @param pattern 日志消息，可以是普通的字符串，也可以是字符串模板（debug: {}）
     * @param args 消息参数
     */
    public void debug(final String pattern, Object... args) {
        if (logger.isDebugEnabled()) {
            this.logger.debug(StrUtil.format(pattern, args));
        }
    }
}