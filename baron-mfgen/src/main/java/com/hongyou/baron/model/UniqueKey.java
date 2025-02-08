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
package com.hongyou.baron.model;

import com.hongyou.baron.GenerationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 唯一键索引
 *
 * @author Berlin
 */
public class UniqueKey extends AbstractKey {

    /**
     * 匹配方法类型的唯一键（01 getByCode CMPNCD, CMPNCD）
     */
    private static final Pattern PATTERN = Pattern.compile("(\\d+)\\s+(\\w+)\\s+(.+)");

    /**
     * 生成唯一键对象
     *
     * @param line 待解析的文本内容
     * @param table 生成的表对象
     * @throws GenerationException 解析时发生的异常
     */
    protected UniqueKey(final String line, final Table table) throws GenerationException {
        Matcher matcher = UniqueKey.PATTERN.matcher(line);
        if (matcher.matches()) {
            super.name = "U" + table.getName() + matcher.group(1);
            super.method = matcher.group(2);

            for (String name: matcher.group(3).split(",")) {
                super.duplicated(name.trim());
                super.columns.add(table.getColumnByName(name.trim(), line));
            }
        } else {
            throw new GenerationException("唯一键无效\n{}", line);
        }
    }
}
