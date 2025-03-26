/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.xml;

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
 * 数据表xml文件生成器
 *
 * @author Berlin
 */
public class XmlGenerator extends AbstractGenerator {

    /**
     * xml输出项目文件夹
     */
    private static final String XML_SOURCE = "src/data/xml";

    /**
     * 通用浏览表格生成模板
     */
    private static final Template GRIDER = CodeTemplate.getTemplate("xml/grider.ftl");

    /**
     * 通用编辑表单生成模板
     */
    private static final Template EDITOR = CodeTemplate.getTemplate("xml/editor.ftl");

    /**
     * 通用查询建议器生成模板
     */
    private static final Template SUGGESTOR = CodeTemplate.getTemplate("xml/suggestor.ftl");

    /**
     * 通用界面注册文件
     */
    private static final Template METADATA = CodeTemplate.getTemplate("xml/metadata.ftl");

    /**
     * 生成Xml文件
     */
    public void compile(
            final List<Table> tables, final GenerationConfig config
    ) throws GenerationException, IOException, TemplateException {

        // 获取输出文件夹
        File xmlFolder = super.prepare(config.getBaseDirectory(), XmlGenerator.XML_SOURCE);

        for (Table table: tables) {
            CodeGenerationMojo.getLogger().info("开始生成数据库表XML文件: {}...", table.getName());

            HashMap<String, Object> params = new HashMap<>();
            params.put("table", table);

            // 创建表文件夹
            File tableFolder = new File(xmlFolder, table.getSqlName());
            if (!tableFolder.mkdir()) {
                throw new GenerationException("文件夹创建失败: {}", tableFolder.getPath());
            }

            // 通用界面文件名称
            String genericFileName = this.getGenericFileName(table.getElabel());
            String griderFileName = genericFileName + "-grider.xml";
            String editorFileName = genericFileName + "-editor.xml";
            String suggestorFileName = genericFileName + "-suggestor.xml";

            // 通用界面名称
            String genericName = this.getGenericName(table.getElabel());
            String griderName = genericName + "List";
            String editorName = genericName + "Edit";
            String suggestorName = genericName + "Suggestor";

            params.put("griderFileName", griderFileName);
            params.put("editorFileName", editorFileName);
            params.put("suggestorFileName", suggestorFileName);
            params.put("griderName", griderName);
            params.put("editorName", editorName);
            params.put("suggestorName", suggestorName);

            // grider
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File(tableFolder, griderFileName))
            )) {
                assert XmlGenerator.GRIDER != null;
                XmlGenerator.GRIDER.process(params, writer);
            }

            // editor
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File(tableFolder, editorFileName))
            )) {
                assert XmlGenerator.EDITOR != null;
                XmlGenerator.EDITOR.process(params, writer);
            }

            // suggestor
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File(tableFolder, suggestorFileName))
            )) {
                assert XmlGenerator.SUGGESTOR != null;
                XmlGenerator.SUGGESTOR.process(params, writer);
            }

            // 创建metadata文件夹
            File metaFolder = new File(tableFolder, "metadata");
            if (!metaFolder.mkdir()) {
                throw new GenerationException("文件夹创建失败: {}", metaFolder.getPath());
            }

            // 通用界面注册文件
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File(metaFolder, "baron-rindja-config.xml"))
            )) {
                assert XmlGenerator.METADATA != null;
                XmlGenerator.METADATA.process(params, writer);
            }
        }
    }

    /**
     * 设定通用界面名称
     */
    private String getGenericName(final String label) {
        String value = label.replace(" ", "");
        return value.substring(0, 1).toLowerCase() + value.substring(1);
    }

    /**
     * 设定通用界面文件名称
     */
    private String getGenericFileName(final String label) {
        StringBuilder builder = new StringBuilder();
        for (String value: label.split(" ")) {
            builder.append(builder.isEmpty() ? "" : "-");
            builder.append(value.toLowerCase());
        }
        return builder.toString();
    }
}
