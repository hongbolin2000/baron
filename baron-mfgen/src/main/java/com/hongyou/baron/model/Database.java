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
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成数据库对象
 *
 * @author Berlin
 */
@Data
public class Database {

    /**
     * 数据库描述
     */
    private String remark;

    /**
     * 数据库版本
     */
    private String version;

    /**
     * 数据库名称
     */
    private String name;

    /**
     * 生成的表
     */
    private List<Table> tables = new ArrayList<>();

    /**
     * 当前实例
     */
    @Setter
    @Getter
    private static Database instance;

    /**
     * 生成数据库
     *
     * @param databaseFile 数据库定义文件路径
     * @throws Exception 生成数据库时抛出的异常
     */
    public Database(final File databaseFile) throws Exception {

        // 避免生成表时使用循环依赖，将数据库对象使用懒加载的方式进行实例化
        Database.setInstance(this);

        // 将文件读读取到流中
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Files.newInputStream(databaseFile.toPath()), StandardCharsets.UTF_8
        ))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String trimLine = line.trim();
                if (trimLine.isEmpty() || trimLine.startsWith("=") || trimLine.startsWith("*")) {
                    continue;
                }

                String type = trimLine.substring(0, 1);
                String value = trimLine.substring(1).trim();
                switch (type) {
                    case "@":
                        this.remark = value;
                        break;
                    case "$" :
                        this.version = value;
                        break;
                    case "D":
                        this.name = value;
                        break;
                    case "T":
                        this.table(databaseFile.getParent(), trimLine);
                        break;
                    default:
                        throw new GenerationException("数据库定义格式无效: {}\n" + type, trimLine);
                }
            }
        }
    }

    /**
     * 生成数据库表
     *
     * @param schemaPath 数据库定义目录路径
     * @param line 表定义字符串
     */
    private void table(final String schemaPath, final String line)
            throws GenerationException, IOException {
        if (!line.contains("*")) {
            throw new GenerationException("数据库定义格式无效, 未匹配到文本分隔符\"*\" \n{}", line);
        }
        String table = line.substring(1, line.indexOf("*"));
        this.tables.add(new Table(new File(schemaPath, table.trim())));
    }

    /**
     * 查找表
     *
     * @param name 表名
     * @param line 定义的文本内容，找不到时用来打印到日志中
     * @return 匹配到的表对象
     * @throws GenerationException 找不到表时抛出的异常
     */
    protected Table getTableByName(final String name, final String line) throws GenerationException {
        Table table = this.tables.stream().filter(i -> name.equals(i.getName())).
                findFirst().orElse(null);
        if (table == null) {
            throw new GenerationException("未找到到表: {}\n", name, line);
        }
        return table;
    }

    /**
     * 检查表文件中定义的表名是否已存在
     *
     * @param name 表名
     */
    protected void checkNameExists(final String name) throws GenerationException {
        if (this.tables.stream().anyMatch(i -> name.equals(i.getName()))) {
            throw new GenerationException("重复的表名: {}", name);
        }
    }

    /**
     * 检查表文件中定义的表头标签是否已存在
     *
     * @param label 表头标签
     */
    protected void checkLabelExists(final String label) throws GenerationException {
        if (this.tables.stream().anyMatch(i -> label.equals(i.getLabel()))) {
            throw new GenerationException("重复的表头标签: {}", label);
        }
    }
}
