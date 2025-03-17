/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.ag01;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.faces.FormEditor;
import com.hongyou.baron.ag01.faces.Sheeter;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用编辑界面
 *
 * @author Hong Bo Lin
 */
public class Editor implements Scheme {

    /**
     * 新增时显示的标题
     */
    private final String atitle;

    /**
     * 编辑时显示的标题
     */
    private final String etitle;

    /**
     * 提交时调用的服务器地址(如果设置了则直接调用服务器，否则使用JavaScript)
     */
    private final String url;

    /**
     * 查询语句
     */
    private Statement statement;

    /**
     * 编辑表单
     */
    private final List<FormEditor> formEditors = new ArrayList<>();

    /**
     * 编辑表格
     */
    private final List<Sheeter> sheeters = new ArrayList<>();

    /**
     * 界面国际化语言
     */
    private final International international;

    /**
     * 加载界面定义
     *
     * @param root 界面定义配置节点
     */
    public Editor(final Element root) {

        // 国际化语言
        this.international = new International(root);

        // 新增/编辑的标题
        Element atitle = XmlUtil.getChildElement(root, "atitle");
        this.atitle = XmlUtil.getTextContent(atitle);
        Element etitle = XmlUtil.getChildElement(root, "etitle");
        this.etitle = XmlUtil.getTextContent(etitle);

        // 提交时调用的接口地址
        Element url = XmlUtil.getChildElement(root, "url");
        this.url = XmlUtil.getTextContent(url);

        // 查询语句
        Element statement = XmlUtil.getChildElement(root, "statement");
        if (statement != null) {
            this.statement = new Statement(statement, false);
            this.statement.setResult(Statement.ResultType.SINGLE);
        }

        // 编辑表单
        List<Element> editors = XmlUtil.getChildElements(root, "editor");
        editors.forEach(editor -> {
            if (this.statement != null) {
                this.statement.addFields(editor, null, "input");
            }
            this.formEditors.add(new FormEditor(editor));
        });

        // 编辑表格
        List<Element> sheeters = XmlUtil.getChildElements(root, "sheeter");
        sheeters.forEach(sheeterNode -> this.sheeters.add(new Sheeter(sheeterNode)));
    }

    /**
     * 生成编辑界面定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {

        // 传入当前界面国际化语言
        env.setInternational(this.international);

        ObjectNode result = env.createObjectNode();
        result.put("atitle", env.getLocalResource(atitle));
        result.put("etitle", env.getLocalResource(etitle));
        result.put("url", this.url);

        // 表单按照设定的显示行分组
        LinkedHashMap<String, List<FormEditor>> formEditorRows = new LinkedHashMap<>();
        for (FormEditor editor : this.formEditors) {
            if (!formEditorRows.containsKey(editor.getRow())) {
                formEditorRows.put(editor.getRow(), new ArrayList<>());
            }
            formEditorRows.get(editor.getRow()).add(editor);
        }

        // 编辑表单
        ArrayNode editorRowsNode = env.createArrayNode();
        for (Map.Entry<String, List<FormEditor>> entry : formEditorRows.entrySet()) {
            List<FormEditor> editors = entry.getValue();

            // 生成当前行编辑表单
            ArrayNode editorsNode = env.createArrayNode();
            for (FormEditor editor: editors) {
                ObjectNode generated = (ObjectNode) editor.generate(env);
                if (!editor.isHidden()) {
                    editorsNode.add(generated);
                }
            }

            ObjectNode editorRowNode = env.createObjectNode();
            editorRowNode.set("editors", editorsNode);
            editorRowsNode.add(editorRowNode);
        }
        result.set("editorRows", editorRowsNode);

        // 表格按照设定的显示行分组
        LinkedHashMap<String, List<Sheeter>> sheeterRows = new LinkedHashMap<>();
        for (Sheeter sheeter: this.sheeters) {
            if (!sheeterRows.containsKey(sheeter.getRow())) {
                sheeterRows.put(sheeter.getRow(), new ArrayList<>());
            }
            sheeterRows.get(sheeter.getRow()).add(sheeter);
        }

        // 编辑表格
        ArrayNode sheeterRowsNode = env.createArrayNode();
        for (Map.Entry<String, List<Sheeter>> entry : sheeterRows.entrySet()) {
            List<Sheeter> sheeters = entry.getValue();

            // 生成当前行编辑表单
            ArrayNode sheetersNode = env.createArrayNode();
            for (Sheeter Sheeter: sheeters) {
                ObjectNode generated = (ObjectNode) Sheeter.generate(env);
                if (!Sheeter.isHidden()) {
                    sheetersNode.add(generated);
                }
            }

            ObjectNode sheeterRowNode = env.createObjectNode();
            sheeterRowNode.set("sheeters", sheetersNode);
            sheeterRowsNode.add(sheeterRowNode);
        }
        result.set("sheeterRows", sheeterRowsNode);

        return result;
    }

    /**
     * 查询编辑表单数据
     *
     * @param env 运行参数
     */
    public JsonNode getData(final Environment env) {

        // 传入当前界面国际化语言
        env.setInternational(this.international);

        // 编辑表单
        ObjectNode result = env.createObjectNode();
        result.set("editor", (ObjectNode) this.statement.getData(env));

        // 编辑表格
        ObjectNode sheeterData = env.createObjectNode();
        this.sheeters.forEach(sheeter ->
            sheeterData.set(sheeter.getName(), sheeter.getData(env))
        );
        result.set("sheeter", sheeterData);
        return result;
    }
}
