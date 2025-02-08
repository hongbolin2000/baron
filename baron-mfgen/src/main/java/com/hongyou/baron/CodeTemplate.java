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

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * Freemarker模板引擎
 *
 * @author Berlin
 */
public class CodeTemplate {

    /**
     * 私有构造函数
     */
    private CodeTemplate() {}

    /**
     * Freemarker模板引擎
     */
    private static Configuration cfg;

    /**
     * 配置模板引擎
     */
    private static void configure() {
        CodeTemplate.cfg = new Configuration(Configuration.VERSION_2_3_33);
        CodeTemplate.cfg.setClassLoaderForTemplateLoading(
                CodeTemplate.class.getClassLoader(), "/templates"
        );
    }

    /**
     * 获取模板
     */
    public static Template getTemplate(final String name) {

        try {
            if (CodeTemplate.cfg == null) {
                CodeTemplate.configure();
            }
            return CodeTemplate.cfg.getTemplate(name);
        } catch (Exception e) {
            CodeGenerationMojo.getLogger().error("模板: {}加载失败", e, name);
            return null;
        }
    }
}
