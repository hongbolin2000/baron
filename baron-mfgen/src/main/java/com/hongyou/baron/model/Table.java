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

import com.hongyou.baron.CodeGenerationMojo;
import com.hongyou.baron.GenerationException;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

/**
 * 生成数据库表对象
 *
 * @author Berlin
 */
@Data
public class Table {

    /**
     * 表名
     */
    private String name;

    /**
     * 表头标签
     */
    private String label;

    /**
     * 中文标签
     */
    private String clabel;

    /**
     * 英文标签
     */
    private String elabel;

    /**
     * 表的描述
     */
    private StringBuilder remark = new StringBuilder();

    /**
     * 生成的字段
     */
    private List<Column> columns = new ArrayList<>();

    /**
     * 主键
     */
    private List<Column> primaryKeys = new ArrayList<>();

    /**
     * 唯一键
     */
    private List<UniqueKey> uniqueKeys = new ArrayList<>();

    /**
     * 索引
     */
    private List<IndexKey> indexKeys = new ArrayList<>();

    /**
     * 外键
     */
    private List<ForeignKey> foreignKeys = new ArrayList<>();

    /**
     * 外连接表字段
     */
    private Map<String, List<String>> jointLinks = new HashMap<>();

    /**
     * 外键试图
     */
    private TableJoint joint;

    /**
     * 生成数据库表
     *
     * @param tableFile 源表文件
     */
    protected Table(final File tableFile) throws IOException, GenerationException {
        CodeGenerationMojo.getLogger().info("加载数据库表定义文件: {}...", tableFile.getPath());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Files.newInputStream(tableFile.toPath()), StandardCharsets.UTF_8
        ))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String trimLine = line.trim();
                if (trimLine.isEmpty() || trimLine.startsWith("S") || trimLine.startsWith("@")
                        || trimLine.startsWith("#")) {
                    continue;
                }

                String type = trimLine.substring(0, 1).trim();
                String value = trimLine.substring(1).trim();
                if (value.isEmpty()) {
                    throw new GenerationException("定义格式无效，不能为空\n", trimLine);
                }

                switch (type) {
                    case "T":
                        this.name = value;
                        break;
                    case "L":
                        this.label = value;
                        break;
                    case "C":
                        this.clabel = value;
                        break;
                    case "Y":
                        this.elabel = value;
                        break;
                    case "@":
                        this.remark.append(value);
                        break;
                    case "N":
                        this.column(value);
                        break;
                    case "#":
                        this.remark(value);
                        break;
                    case "B", "E":
                        this.enumerate(value);
                        break;
                    case "P":
                        this.primary(value);
                        break;
                    case "U":
                        this.unique(value);
                        break;
                    case "I":
                        this.index(value);
                        break;
                    case "F":
                        this.foreign(value);
                        break;
                    case "K":
                        this.joint(value);
                        break;
                    case "*":
                        break;
                    case "=":
                        break;
                    default:
                        throw new GenerationException("起始符无效\"{}\" \n{}", type, trimLine);
                }
            }
            if (this.primaryKeys.isEmpty()) {
                throw new GenerationException("未定义主键: {}", this.name);
            }

            // 生成外键试图
            if (!this.jointLinks.isEmpty()) {
                joint = new TableJoint(this);
            }
        }
    }

    /**
     * 解析数据库定义字段
     */
    private Column lastColumn;
    private void column(final String line) throws GenerationException {
        if (line.split(",").length < 7) {
            throw new GenerationException("表字段格式无效, 未匹配到列分隔符\",\" \n{}", line);
        }
        lastColumn = new Column(line);

        if (this.columns.stream().anyMatch(i -> i.getName().equals(lastColumn.getName()))) {
            throw new GenerationException("重复的表字段\n{}", line);
        }
        if (this.columns.stream().anyMatch(i -> i.getElabel().equals(lastColumn.getJlabel()))) {
            throw new GenerationException("重复的JSON标签\n{}", line);
        }
        this.columns.add(lastColumn);
    }

    /**
     * 枚举
     */
    private void enumerate(final String line) throws GenerationException {
        if (this.lastColumn == null) {
            throw new GenerationException("无效的枚举值, 必须定义在字段后\n{}", line);
        }

        String[] items = line.split("\\s+", 3);
        if (items.length == 3) {
            this.lastColumn.getEnums().add(
                    new com.hongyou.baron.model.Enumeration(items[0], items[1], items[1], items[2])
            );
        } else {
            throw new GenerationException("无效的枚举值\n{}", line);
        }
    }

    /**
     * 字段备注
     */
    private void remark(final String line) {
        if (this.lastColumn == null) {
            return;
        }
        this.lastColumn.setRemark(this.lastColumn.getRemark() + line + "\n");
    }

    /**
     * 主键
     */
    private void primary(final String line) throws GenerationException {
        if (!this.primaryKeys.isEmpty()) {
            throw new GenerationException("重复的主键\n{}", line);
        }
        if (line.isEmpty()) {
            throw new GenerationException("主键定义格式无效");
        }
        for (String primary: line.split(",")) {
            if (this.primaryKeys.stream().anyMatch(i -> primary.trim().equals(i.getName()))) {
                throw new GenerationException("重复的主键字段: {}", primary);
            }
            this.primaryKeys.add(this.getColumnByName(primary.trim(), line));
        }
    }

    /**
     * 唯一键
     */
    private void unique(final String line) throws GenerationException {
        UniqueKey uniqueKey = new UniqueKey(line, this);
        if (this.uniqueKeys.stream().anyMatch(i -> i.getName().equals(uniqueKey.getName()))) {
            throw new GenerationException("重复的唯一键: {}", line);
        }
        this.uniqueKeys.add(uniqueKey);
    }

    /**
     * 索引
     */
    private void index(final String line) throws GenerationException {
        IndexKey indexKey = new IndexKey(line, this);
        if (this.indexKeys.stream().anyMatch(i -> i.getName().equals(indexKey.getName()))) {
            throw new GenerationException("重复的索引: {}", line);
        }
        this.indexKeys.add(indexKey);
    }

    /**
     * 外键
     */
    private void foreign(final String line) throws GenerationException {
        ForeignKey foreignKey = new ForeignKey(line, this);
        if (this.foreignKeys.stream().anyMatch(i -> i.getName().equals(foreignKey.getName()))) {
            throw new GenerationException("重复的外键: {}", line);
        }
        this.foreignKeys.add(foreignKey);
    }

    /**
     * 外连接字段（CMPNMS - CMPNCD, CMPNNM）
     */
    private void joint(final String line) throws GenerationException {
        String[] splits = line.split("-");
        if (splits.length != 2) {
            throw new GenerationException("外联接试图无效格式: \n{}", line);
        }
        String table = splits[0].trim();
        if (table.split(":").length > 3) {
            throw new GenerationException("外联接试图不能超过3张表: \n{}", line);
        }

        this.jointLinks.computeIfAbsent(table, k -> new ArrayList<>());
        for (String column: splits[1].split(",")) {
            this.jointLinks.get(table).add(column.trim());
        }
    }

    /**
     * 查找字段
     *
     * @param column 字段名
     * @param line 定义的源字段文件内容，出现异常时用来打印
     * @return 匹配到的列
     * @throws GenerationException 匹配不到是抛出的异常
     */
    protected Column getColumnByName(final String column, final String line) throws GenerationException {
        if (this.columns.stream().noneMatch(i -> column.equals(i.getName()))) {
            throw new GenerationException("未找到到表({})中的字段({})\n{}", this.name, column, line);
        }
        return this.getColumns().stream().filter(i -> column.equals(i.getName())).
                findFirst().orElse(null);
    }

    /**
     * 获取SQL表名
     */
    public String getSqlName() {
        return this.name.toLowerCase();
    }

    /**
     * 获取Java表名
     */
    public String getJavaName() {
        String sqlName = this.getSqlName();
        return sqlName.substring(0, 1).toUpperCase() + sqlName.substring(1);
    }

    /**
     * 是否有定义的查询方法
     */
    public boolean getHasMethod() {
        return this.foreignKeys.stream().anyMatch(i -> i.getMethod() != null) ||
                this.indexKeys.stream().anyMatch(i -> i.getMethod() != null);
    }
}
