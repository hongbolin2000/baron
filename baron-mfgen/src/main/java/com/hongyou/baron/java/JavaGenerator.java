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
package com.hongyou.baron.java;

import com.hongyou.baron.*;
import com.hongyou.baron.model.Table;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 数据表实体类文件生成器
 *
 * @author Berlin
 */
public class JavaGenerator extends AbstractGenerator {

    /**
     * 实体类文件输出包
     */
    private static final String ENTITY_SOURCE = "src/data/java";

    /**
     * Mapper文件输出包
     */
    private static final String MAIN_SOURCE = "src/main/java";

    /**
     * 实体类生成模板
     */
    private static final Template ENTITY = CodeTemplate.getTemplate("java/entity.ftl");

    /**
     * 试图类生成模板
     */
    private static final Template VIEW = CodeTemplate.getTemplate("java/view.ftl");

    /**
     * 实体类抽象Mapper生成模板
     */
    private static final Template ABSTRACT_MAPPER = CodeTemplate.getTemplate("java/abstract-mapper.ftl");

    /**
     * 试图类Mapper生成模板
     */
    private static final Template VIEW_MAPPER = CodeTemplate.getTemplate("java/view-mapper.ftl");

    /**
     * 实体类Mapper生成模板
     */
    private static final Template MAPPER = CodeTemplate.getTemplate("java/mapper.ftl");

    /**
     * 数据库组件生成模板
     */
    private static final Template DB = CodeTemplate.getTemplate("java/db.ftl");

    /**
     * Java文件名后缀
     */
    private static final String JAVA_SUFFIX = ".java";

    /**
     * 生成Java对象
     */
    public void compile(
            final List<Table> tables, final GenerationConfig config, final String dbName
    ) throws GenerationException, IOException, TemplateException {

        // 实体类输出主包
        String classPackage = config.getClassPackage().replace(".", "/");
        File entitySource = this.getFolder(config.getBaseDirectory(), JavaGenerator.ENTITY_SOURCE);
        File entityPackage = this.getFolder(entitySource.getPath(), classPackage);

        // 实体类输出子包
        File modelPackage = super.prepare(entityPackage.getPath(), "model");
        File abstractMapperPackage = super.prepare(entityPackage.getPath(), "mapper");

        // Mapper输出主包
        File mainSource = this.getFolder(config.getBaseDirectory(), JavaGenerator.MAIN_SOURCE);
        File mainPackage = this.getFolder(mainSource.getPath(), classPackage);

        // Mapper输出子包
        File mapperPackage = this.getFolder(mainPackage.getPath(), "mapper");

        for (Table table: tables) {
            CodeGenerationMojo.getLogger().info("开始生成数据实体文件: {}...", table.getName());

            HashMap<String, Object> params = new HashMap<>();
            params.put("table", table);
            params.put("classPackage", config.getClassPackage());

            // Entity
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File(modelPackage, table.getJavaName() + JAVA_SUFFIX))
            )) {
                assert JavaGenerator.ENTITY != null;
                JavaGenerator.ENTITY.process(params, writer);
            }

            // View
            if (table.getJoint() != null) {
                try (BufferedWriter writer = new BufferedWriter(
                        new FileWriter(new File(modelPackage, "V" + table.getJavaName() + JAVA_SUFFIX))
                )) {
                    assert JavaGenerator.VIEW != null;
                    JavaGenerator.VIEW.process(params, writer);
                }

                String mapperName = "V" + table.getJavaName() + "Mapper" + JAVA_SUFFIX;
                try (BufferedWriter writer = new BufferedWriter(
                        new FileWriter(new File(abstractMapperPackage, mapperName)))
                ) {
                    assert JavaGenerator.VIEW_MAPPER != null;
                    JavaGenerator.VIEW_MAPPER.process(params, writer);
                }
            }

            // Abstract Mapper
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File(abstractMapperPackage, "Abstract" + table.getJavaName() + "Mapper.java")))
            ) {
                assert JavaGenerator.ABSTRACT_MAPPER != null;
                JavaGenerator.ABSTRACT_MAPPER.process(params, writer);
            }

            // Mapper
            File file = new File(mapperPackage, table.getJavaName() + "Mapper.java");
            if (!file.exists()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    assert JavaGenerator.MAPPER != null;
                    JavaGenerator.MAPPER.process(params, writer);
                }
            }
        }

        // DB Component
        HashMap<String, Object> params = new HashMap<>();
        params.put("tables", tables);
        params.put("dbName", dbName);
        params.put("classPackage", config.getClassPackage());
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(new File(entityPackage, dbName + JAVA_SUFFIX)))
        ) {
            assert JavaGenerator.DB != null;
            JavaGenerator.DB.process(params, writer);
        }
    }

    /**
     * 获取输出文件夹
     */
    private File getFolder(
            final String baseDirectory, final String targetDirectory
    ) throws GenerationException {
        File file = new File(baseDirectory, targetDirectory);
        if (!file.exists() && !file.mkdirs()) {
            throw new GenerationException("文件夹创建失败: {}", file.getPath());
        }
        return file;
    }
}
