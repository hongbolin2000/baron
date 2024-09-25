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
package com.hongyou.baron;

import com.hongyou.baron.java.JavaGenerator;
import com.hongyou.baron.logging.Log;
import com.hongyou.baron.logging.LogFactory;
import com.hongyou.baron.model.Database;
import com.hongyou.baron.sql.SqlGenerator;
import lombok.Getter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;

/**
 * 数据库模块构建插件
 *
 * @author Berlin
 */
@Mojo(name = "mfgen", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class CodeGenerationMojo extends AbstractMojo {

    /**
     * logger
     */
    @Getter
    private static final Log logger = LogFactory.getLog(CodeGenerationMojo.class);

    /**
     * 调用当前插件的Maven工程对象
     */
    @Parameter(readonly = true, defaultValue="${project}", required = true)
    private MavenProject project;

    /**
     * 数据库定义文件路径
     */
    @Parameter(property = "databaseFile", defaultValue = "src/data/schema/main.txt", required = true)
    private String databaseFile;

    /**
     * 生成的数据库类型
     */
    @Parameter(property = "databaseType", defaultValue = "MYSQL", required = true)
    private DatabaseType databaseType;

    /**
     * Java代码输出包
     */
    @Parameter(property = "classPackage", defaultValue = "com.hongyou.abner.data", required = true)
    private String classPackage;

    @Override
    public void execute() {
        GenerationConfig config = GenerationConfig.builder().
                baseDirectory(this.project.getBasedir().getPath()).
                type(databaseType).
                classPackage(this.classPackage).
                build();
        CodeGenerationMojo.getLogger().info("开始构建数据库模块: {0}", config.getBaseDirectory());
        CodeGenerationMojo.getLogger().info(
                "构建数据库类型: {0}, 输出包: {1}", config.getType(), config.getClassPackage()
        );

        // 检查数据库文件定义是否存在
        File file = new File(config.getBaseDirectory(), this.databaseFile);
        if (!file.exists()) {
            CodeGenerationMojo.getLogger().error("数据库定义文件不存在: {0}", this.databaseFile);
            return;
        }

        Database database;
        try {
            CodeGenerationMojo.getLogger().info("加载数据库定义文件: {0}...", this.databaseFile);
            database = new Database(file);
        } catch (Exception e) {
            CodeGenerationMojo.getLogger().error("数据库定义文件加载失败...", e);
            return;
        }

        try {
            new SqlGenerator().compile(database.getTables(), config, database.getName());
        } catch (Exception e) {
            CodeGenerationMojo.getLogger().error("数据库表文件生成失败...", e);
        }

        try {
            new JavaGenerator().compile(database.getTables(), config, database.getName());
        } catch (Exception e) {
            CodeGenerationMojo.getLogger().error("数据库实体文件生成失败...", e);
        }
        CodeGenerationMojo.getLogger().info("数据库模块构建完成: {0}", config.getBaseDirectory());

        this.project.addCompileSourceRoot("src/data/java");
    }
}
