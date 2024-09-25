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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 生成字段类型
 *
 * @author Berlin
 */
@Data
public class ColumnType {

    /**
     * 匹配（ID, BIGINT, INT, TIMESTAMP, DATE, BLOB）等普通字符的类型
     */
    private static final Pattern NORMAL_TYPE = Pattern.compile("\\w+");

    /**
     * 匹配（VARHCHAR(16)，CHAR(16)）等带长度的字符
     */
    private static final Pattern LENGTH_TYPE = Pattern.compile("(\\w+)\\((\\d+)\\)");

    /**
     * 匹配（Numeric(24.5)）
     */
    private static final Pattern NUMERIC_TYPE = Pattern.compile("(\\w+)\\((\\d+)(.)(\\d)\\)");

    /**
     * SQL类型
     */
    private String sqlType;

    /**
     * Java类型
     */
    private Class<?> javaType;

    /**
     * 长度
     */
    private int length;

    /**
     * 小数
     */
    private int scale;

    /**
     * 生成字段类型
     *
     * @param definedType 定义的类型
     * @throws GenerationException 定义类型与无法解析为数据库类型时抛出的异常
     */
    protected ColumnType(final String definedType) throws GenerationException {
        Matcher matcher = ColumnType.NORMAL_TYPE.matcher(definedType);
        if (matcher.matches()) {
            this.sqlType = ColumnTypeResolve.getInstance().getSqlType(matcher.group(0));
            this.javaType = ColumnTypeResolve.getInstance().getJavaType(matcher.group(0));
            return;
        }

        matcher = ColumnType.LENGTH_TYPE.matcher(definedType);
        if (matcher.matches()) {
            this.sqlType = ColumnTypeResolve.getInstance().getSqlType(matcher.group(1));
            this.javaType = ColumnTypeResolve.getInstance().getJavaType(matcher.group(1));
            this.length = Integer.parseInt(matcher.group(2));
            this.sqlType = this.sqlType + "(" + this.length + ")";
            return;
        }

        matcher = ColumnType.NUMERIC_TYPE.matcher(definedType);
        if (matcher.matches()) {
            this.sqlType = ColumnTypeResolve.getInstance().getSqlType(matcher.group(1));
            this.javaType = ColumnTypeResolve.getInstance().getJavaType(matcher.group(1));
            this.length = Integer.parseInt(matcher.group(2));
            this.scale = Integer.parseInt(matcher.group(4));
            this.sqlType = this.sqlType + "(" + this.length + ", " + this.scale + ")";
            return;
        }
        throw new GenerationException("数据类型无效: {0}", definedType);
    }
}
