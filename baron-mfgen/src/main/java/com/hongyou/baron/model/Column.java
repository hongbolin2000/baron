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

import cn.hutool.core.util.IdUtil;
import com.hongyou.baron.GenerationException;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成数据库字段对象
 *
 * @author Berlin
 */
@Data
public class Column {

    /**
     * 表定义字段记录ID(中英文)
     */
    private String ctfdsid;
    private String etfdsid;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段类型
     */
    private ColumnType type;

    /**
     * 是否可空
     */
    private boolean nullable;

    /**
     * 缺省值
     */
    private String javaValue;
    private String sqlValue;

    /**
     * JSON标签
     */
    private String jlabel;

    /**
     * 英文标签
     */
    private String elabel;

    /**
     * 中文标签
     */
    private String clabel;

    /**
     * 描述
     */
    private String remark = "";

    /**
     * 是否主键
     */
    private boolean identity = false;

    /**
     * 枚举
     */
    private List<Enumeration> enums = new ArrayList<>();

    /**
     * 生成数据库列
     *
     * @param line 需解析的行内容
     * @throws GenerationException 生成数据库字段类型时抛出的异常
     */
    protected Column(final String line) throws GenerationException {
        String[] items = line.split(",");
        String value = items[3].trim();

        this.name = items[0].trim();
        this.type = new ColumnType(items[1].trim());
        this.nullable = items[2].trim().contains("NOT") && items[2].trim().contains("NULL");
        this.jlabel = items[4].trim();
        this.elabel = items[5].trim();
        this.clabel = items[6].trim();

        this.ctfdsid = Long.toString(IdUtil.getSnowflakeNextId());
        this.etfdsid = Long.toString(IdUtil.getSnowflakeNextId());

        if (!value.isBlank()) {
            if ("CURRENT_TIMESTAMP".equals(value)) {
                this.javaValue = "new java.sql.Timestamp(new java.util.Date().getTime())";
            } else if (this.type.getJavaType().equals(BigDecimal.class)) {
                this.javaValue = "new java.math.BigDecimal(\"" + value + "\")";
            } else if (this.type.getJavaType().equals(Long.class)) {
                this.javaValue = value + "L";
            } else {
                this.javaValue = value.replace("'", "\"");
            }
            sqlValue = value;
        }

        if ("ID".equalsIgnoreCase(items[1].trim())) {
            this.identity = true;
        }
    }

    /**
     * 获取MySQL数据库类型
     */
    public String getMySQLType() {
        if (this.type.getSqlType().equals("BLOB")) {
            return "LONGTEXT";
        }
        return this.type.getSqlType();
    }

    /**
     * 获取SQLServer数据库类型
     */
    public String getSqlServerType() {
        return switch (this.type.getSqlType()) {
            case "CLOB" -> "TEXT";
            case "BLOB" -> "IMAGE";
            case "TIMESTAMP" -> "DATETIME";
            default -> this.type.getSqlType();
        };
    }

    /**
     * 获取Java类型
     */
    public String getJavaType() {
        Class<?> javaType = this.type.getJavaType();
        if (javaType.getPackage().getName().contains("java.lang")) {
            return this.type.getJavaType().getSimpleName();
        }
        return javaType.getPackage().getName() + "." + javaType.getSimpleName();
    }

    /**
     * 获取SQL字段名
     */
    public String getSqlName() {
        return this.name.toLowerCase();
    }

    /**
     * 获取定义SQL值（提供给TB表使用）
     */
    public String getDefineSqlValue() {
        if (this.sqlValue == null || "CURRENT_TIMESTAMP".equals(this.sqlValue)) {
            return "''";
        }
        return this.sqlValue;
    }
}
