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
package com.hongyou.baron.sql;

import com.hongyou.baron.*;
import com.hongyou.baron.model.Table;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 数据表SQL文件生成器
 *
 * @author Berlin
 */
public class SqlGenerator extends AbstractGenerator {

    /**
     * 数据库表输出项目文件夹
     */
    private static final String SQL = "src/data/sql";

    /**
     * MySQL表模板
     */
    private static final Template MySQL = CodeTemplate.getTemplate("sql/mysql.ftl");

    /**
     * SQLServer表模板
     */
    private static final Template SQLServer = CodeTemplate.getTemplate("sql/sql-server.ftl");

    /**
     * 试图模板
     */
    private static final Template VIEW = CodeTemplate.getTemplate("sql/view.ftl");

    /**
     * 表定义SQL模板
     */
    private static final Template DEFINE = CodeTemplate.getTemplate("sql/table-define.ftl");

    /**
     * 生成SQL语句
     *
     * @param tables 生成的表对象
     * @param config 数据库配置
     */
    public void compile(
            final List<Table> tables, final GenerationConfig config, final String dbName
    ) throws GenerationException, IOException, TemplateException {

        // 获取输出文件夹
        String lowerDbName = dbName.toLowerCase();
        File sqlFolder = super.prepare(config.getBaseDirectory(), SqlGenerator.SQL);

        for (Table table: tables) {
            CodeGenerationMojo.getLogger().info("开始生成数据库表文件: {}...", table.getName());
            HashMap<String, Object> params = new HashMap<>();
            params.put("table", table);

            // MySQL
            if (DatabaseType.MYSQL == config.getType()) {
                assert SqlGenerator.MySQL != null;
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(
                        new File(sqlFolder.getPath(), lowerDbName + ".mysql.sql"), true
                ))) {
                    SqlGenerator.MySQL.process(params, writer);
                }
            }

            // SQLServer
            if (DatabaseType.SQLSERVER == config.getType()) {
                assert SqlGenerator.SQLServer != null;
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(
                        new File(sqlFolder.getPath(), lowerDbName + ".sqlserver.sql"), true
                ))) {
                    SqlGenerator.SQLServer.process(params, writer);
                }
            }

            // View
            if (table.getJoint() != null) {
                params.put("type", config.getType());
                assert SqlGenerator.VIEW != null;
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(
                        new File(sqlFolder.getPath(), lowerDbName + ".view.sql"), true
                ))) {
                    SqlGenerator.VIEW.process(params, writer);
                }
            }

            // 生成表定义描述SQL
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(
                    new File(sqlFolder.getPath(), lowerDbName + ".schema.sql"), true
            ))) {
                assert SqlGenerator.DEFINE != null;
                SqlGenerator.DEFINE.process(params, writer);
            }
        }

        // 反转表顺序
        List<Table> reverseTables = new ArrayList<>(tables);
        Collections.reverse(reverseTables);

        // 生成表删除语句
        try(BufferedWriter deleteWriter = new BufferedWriter(
                new FileWriter(new File(sqlFolder.getPath(), lowerDbName + ".delete.sql"), true)
        )) {
            for (Table table: reverseTables) {
                deleteWriter.write("DELETE FROM " + table.getSqlName()+ ";\n");
            }
            deleteWriter.write("\n");
            for (Table table: reverseTables) {
                deleteWriter.write("DROP TABLE " + table.getSqlName()+ ";\n");
            }
            deleteWriter.write("\n");
            for (Table table: reverseTables) {
                if (table.getJoint() != null) {
                    deleteWriter.write("DROP VIEW v" + table.getSqlName()+ ";\n");
                }
            }
            deleteWriter.flush();
        }
    }
}
