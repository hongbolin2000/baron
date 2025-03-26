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
package com.hongyou.baron.ag01.faces;

/**
 * 过滤类型
 *
 * @author Berlin
 */
public class FilterType {

    /**
     * 文本
     */
    private static final String TEXT = "text";

    /**
     * 日期
     */
    private static final String DATE = "date";

    /**
     * 过滤选项
     */
    private static final String OPT = "@";

    /**
     * 检查是否文本类型的过滤
     */
    public static boolean isText(final String filter) {
        return FilterType.TEXT.equals(filter);
    }

    /**
     * 检查是覅日期类型的过滤
     */
    public static boolean isDate(final String filter) {
        return FilterType.DATE.equals(filter);
    }

    /**
     * 检查是否选项类型的过滤
     */
    public static boolean isOpt(final String filter) {
        return filter.startsWith(FilterType.OPT);
    }
}
