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
 * 索引
 *
 * @author Berlin
 */
public class IndexKey extends AbstractKey {

    /**
     * 匹配方法类型的索引（02 listByRefDocType WRHSID, RDOCTY, STATUS）
     */
    private static final Pattern METHOD_I = Pattern.compile("(\\d+)\\s+(\\w+)\\s+(.+)");

    /**
     * 匹配字段类型的索引（01 CMPNID, SORTNG, UNITCD, ENABLD）
     */
    private static final Pattern FILED = Pattern.compile("(\\d+)\\s+(.+)");

    /**
     * 生成索引对象
     *
     * @param line 待解析的文本行
     * @param table 生成外键的表
     * @throws GenerationException 生成索引时出现的异常
     */
    protected IndexKey(final String line, final Table table) throws GenerationException {
        Matcher matcher = IndexKey.METHOD_I.matcher(line);
        if (matcher.matches()) {
            super.name = "I" + table.getName() + matcher.group(1);
            super.method = matcher.group(2);

            for (String name: matcher.group(3).split(",")) {
                super.duplicated(name.trim());
                super.columns.add(table.getColumnByName(name.trim(), line));
            }
            return;
        }

        matcher = IndexKey.FILED.matcher(line);
        if (matcher.matches()) {
            super.name = "I" + table.getName() + matcher.group(1);
            for (String name: matcher.group(2).split(",")) {
                super.duplicated(name.trim());
                super.columns.add(table.getColumnByName(name.trim(), line));
            }
            return;
        }
        throw new GenerationException("无效的索引\n{0}", line);
    }
}
