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
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 外键索引
 *
 * @author Berlin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ForeignKey extends AbstractKey {

    /**
     * 外键表
     */
    private Table refTable;

    /**
     * 外键字段
     */
    private Column refColumn;

    /**
     * 匹配方法类型的外键（01 listByWarehouse (WRHSID WRHSMS (WRHSID)）
     */
    private static final Pattern METHOD_F = Pattern.compile("(\\d+)\\s(\\w+)\\s\\((\\w+)\\)\\s(\\w+)\\s\\((\\w+)\\)");

    /**
     * 匹配字段类型的外键（01 (CMPNID) CMPNMS (CMPNID)）
     */
    private static final Pattern FILED = Pattern.compile("(\\d+)\\s\\((\\w+)\\)\\s(\\w+)\\s\\((\\w+)\\)");

    /**
     * 生成外键索引
     *
     * @param line 待解析的文本行
     * @param table 生成外键的表
     * @throws GenerationException 生成外键时出现的异常
     */
    protected ForeignKey(final String line, final Table table) throws GenerationException {

        Matcher matcher = METHOD_F.matcher(line);
        if (matcher.matches()) {
            super.name = "F" + table.getName() + matcher.group(1);
            super.method = matcher.group(2);
            super.columns.add(table.getColumnByName(matcher.group(3), line));

            // 检查表和字段是否存在
            this.refTable = Database.getInstance().getTableByName(matcher.group(4), line);
            this.refColumn = refTable.getColumnByName(matcher.group(5), line);
            return;
        }

        matcher = FILED.matcher(line);
        if (matcher.matches()) {
            super.name = "F" + table.getName() + matcher.group(1);
            super.columns.add(table.getColumnByName(matcher.group(2), line));

            // 检查表和字段是否存在
            this.refTable = Database.getInstance().getTableByName(matcher.group(3), line);
            this.refColumn = refTable.getColumnByName(matcher.group(4), line);
            return;
        }
        throw new GenerationException("无效的外键\n{0}", line);
    }
}
