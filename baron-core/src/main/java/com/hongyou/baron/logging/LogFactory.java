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

import org.slf4j.LoggerFactory;

/**
 * 日志建造工厂
 *
 * @author Berlin
 */
public class LogFactory {

    /**
     * 私有构造函数
     */
    private LogFactory() {}

    /**
     * 通过class类对象生成日志
     *
     * @param clazz 生成日志的class对象
     * @return 日志对象
     */
    public static Log getLog(Class<?> clazz) {
        return new Log(LoggerFactory.getLogger(clazz));
    }
}
