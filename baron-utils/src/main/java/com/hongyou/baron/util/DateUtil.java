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
package com.hongyou.baron.util;

import java.time.*;

/**
 * 日期处理工具类
 * <p>本工具类使用的是java time中线程安全的日期</p>
 * <p>不推荐使用java util中非线程安全的日期避免带来不必要的影响</p>
 *
 * @author Berlin
 */
public class DateUtil {

    /**
     * 私有构造函数
     */
    private DateUtil() {}

    /**
     * 系统缺省时区
     */
    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    /**
     * 获取当前时间
     */
    public static LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前日期
     */
    public static LocalDate getDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间戳
     */
    public static long getTimestamp() {
        return DateUtil.getTimestamp(DateUtil.getDateTime());
    }

    /**
     * 获取时间戳
     *
     * @param time 需要转成时间戳的时间
     */
    public static long getTimestamp(final LocalDateTime time) {
        ZonedDateTime zonedDateTime = time.atZone(DEFAULT_ZONE);
        Instant instant = zonedDateTime.toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 获取日期时间戳
     *`
     * @param date 需转换为时间戳的日期
     */
    public static long getTimestamp(final LocalDate date) {
        ZonedDateTime zonedDateTime = date.atStartOfDay(DEFAULT_ZONE);
        Instant instant = zonedDateTime.toInstant();
        return instant.toEpochMilli();
    }
}
