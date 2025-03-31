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
import cn.hutool.core.util.StrUtil;

/**
 * 字符串工具类
 *
 * @author Berlin
 */
public class StringUtil extends StrUtil {

    /**
     * 将字符串指定的索引每位都特换为特定字符
     *
     * @param content 需替换的内容
     * @param start 起始索引
     * @param end 结束索引
     * @param replace 替换字符
     */
    public static String replace(final String content, final int start, final int end, final String replace) {
        if (StringUtil.isEmpty(content)) {
            return content;
        }
        if (start >= content.length()) {
            return content;
        }
        return content.substring(0, start) +
                replace.repeat(Math.max(0, end - start)) +
                content.substring(end);
    }
}
