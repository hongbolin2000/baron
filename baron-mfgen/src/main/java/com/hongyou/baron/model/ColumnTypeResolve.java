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

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 * 数据库字段关系对应表
 *
 * @author Berlin
 */
public class ColumnTypeResolve {

    /**
     * 数据库类型
     */
    private final HashMap<String, String> sqlTypes = new HashMap<>();

    /**
     * Java类型
     */
    private final HashMap<String, Class<?>> javaTypes = new HashMap<>();

    /**
     * 实例
     */
    private static ColumnTypeResolve instance;

    /**
     * 相同的类型定义
     */
    private static final String BIGINT = "BIGINT";
    private static final String BINARY = "BINARY";
    private static final String VARBINARY = "VARBINARY";

    /**
     * 单例对象
     */
    public static ColumnTypeResolve getInstance() {
        if (ColumnTypeResolve.instance == null) {
            ColumnTypeResolve.instance = new ColumnTypeResolve();
        }
        return ColumnTypeResolve.instance;
    }

    /**
     * 初始化
     */
    private ColumnTypeResolve() {

        // SQL数据类型
        this.sqlTypes.put("INT", "INTEGER");        // 存储整数(-2147483648~2147483647)
        this.sqlTypes.put(BIGINT, BIGINT);      // 存储大整数，ID时间戳等，-2^63到2^63-1

        this.sqlTypes.put("D", "DECIMAL");          // 用于需要精确计算的场景，特别是在财务计算、货币、税率、利率等应用中。
        this.sqlTypes.put("N", "NUMERIC");          // 需要高精度和精确度的场景‌，如财务计算、科学计算、统计分析以及工程计算等。

        this.sqlTypes.put("C", "CHAR");             // 存储固定长度的字符
        this.sqlTypes.put("VC", "VARCHAR");         // 存储可变长度的字符
        this.sqlTypes.put("VC2", "VARCHAR2");       // 存储大数据量的文本，如文字等

        this.sqlTypes.put("DATE", "DATE");          // 日期
        this.sqlTypes.put("TIME", "TIME");          // 时间
        this.sqlTypes.put("TS", "TIMESTAMP");       // 时间戳

        this.sqlTypes.put("CLOB", "CLOB");          // 存储大量的文本数据，如长篇文章、文档或XML数据等
        this.sqlTypes.put("BLOB", "BLOB");          // 数据库BLOB一般使用在需要存储大量二进制数据的场景
        this.sqlTypes.put(BINARY, BINARY);      // 使用在存储大量的非文本数据，尤其是多媒体数据，如图像、音频、视频等
        this.sqlTypes.put(VARBINARY, VARBINARY);// 使用在需要存储大型二进制数据的场景。

        this.sqlTypes.put("BOOL", "CHAR(1)");

        this.sqlTypes.put("ID", BIGINT);

        // Java数据类型
        // 添加Java数据类型
        this.javaTypes.put("INT", Integer.class);
        this.javaTypes.put(BIGINT, Long.class);

        this.javaTypes.put("N", BigDecimal.class);
        this.javaTypes.put("D", BigDecimal.class);

        this.javaTypes.put("C", String.class);
        this.javaTypes.put("VC", String.class);
        this.javaTypes.put("VC2", String.class);

        this.javaTypes.put("DATE", Date.class);
        this.javaTypes.put("TIME", Time.class);
        this.javaTypes.put("TS", Timestamp.class);

        this.javaTypes.put("CLOB", String.class);
        this.javaTypes.put("BLOB", byte[].class);
        this.javaTypes.put(BINARY, byte[].class);
        this.javaTypes.put(VARBINARY, byte[].class);

        this.javaTypes.put("BOOL", Boolean.class);

        this.javaTypes.put("ID", Long.class);
    }

    /**
     * 获取数据库类型
     *
     * @param definedType 定义的类型
     * @return 数据库类型
     * @throws GenerationException 定义类型与预定义的类型无法匹配时抛出的异常
     */
    protected String getSqlType(final String definedType) throws GenerationException {
        if (!this.sqlTypes.containsKey(definedType)) {
            throw new GenerationException("数据类型无效: {0}", definedType);
        }
        return this.sqlTypes.get(definedType);
    }

    protected Class<?> getJavaType(final String definedType) throws GenerationException {
        if (!this.javaTypes.containsKey(definedType)) {
            throw new GenerationException("数据类型无效: {0}", definedType);
        }
        return this.javaTypes.get(definedType);
    }
}
