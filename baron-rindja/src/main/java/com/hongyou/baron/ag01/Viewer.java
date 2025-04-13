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
package com.hongyou.baron.ag01;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.faces.*;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

import java.util.*;

/**
 * 通用浏览表单界面
 *
 * @author Berlin
 */
public class Viewer implements Scheme {

    /**
     * 标题
     */
    private final String title;

    /**
     * 过滤器
     */
    private final Filter filter;

    /**
     * 动作按钮栏
     */
    private final ActionBar actionBar;

    /**
     * 浏览表单
     */
    private final List<FormViewer> formViewers = new ArrayList<>();

    /**
     * 浏览表格
     */
    private final List<Datatable> datatables = new ArrayList<>();

    /**
     * 主查询语句
     */
    private final Statement statement;

    /**
     * 全局查询语句
     */
    private final HashMap<String, Statement> supportStatements = new HashMap<>();

    /**
     * 界面国际化语言
     */
    private final International international;

    /**
     * 加载界面定义
     *
     * @param root 界面定义配置节点
     */
    public Viewer(final Element root) {

        // 国际化语言
        this.international = new International(root);
        Element title = XmlUtil.getChildElement(root, "title");
        this.title = XmlUtil.getTextContent(title);

        // 过滤器
        Element filters = XmlUtil.getChildElement(root, "filters");
        if (filters != null) {
            this.filter = new Filter(filters);
        } else {
            this.filter = null;
        }

        // 动作按钮栏
        Element actions = XmlUtil.getChildElement(root, "actions");
        if (actions != null) {
            this.actionBar = new ActionBar(actions);
        } else {
            this.actionBar = null;
        }

        // 全局查询语句
        List<Element> statements = XmlUtil.getGrandChildElements(root, "support-statements", "statement");
        statements.forEach(statement -> {
            String name = XmlUtil.getAttribute(statement, "name");
            this.supportStatements.put(name, new Statement(statement, true));
        });

        // 加载主查询语句
        Element statement = XmlUtil.getChildElement(root, "statement");
        if (statement != null) {
            this.statement = new Statement(statement, false);
        } else {
            this.statement = null;
        }

        // 浏览表单
        List<Element> masters = XmlUtil.getChildElements(root, "master");
        masters.forEach(master -> {
            FormViewer formViewer = new FormViewer(master);
            if (this.statement != null && formViewer.getStatement() == null) {
                this.statement.addFields(master, null, "input");
            }
            this.formViewers.add(formViewer);
        });

        // 浏览表格
        List<Element> datatables = XmlUtil.getChildElements(root, "datatable");
        datatables.forEach(datatable -> this.datatables.add(new Datatable(datatable, this.filter)));
    }

    /**
     * 生成浏览表单界面定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {

        // 传入当前界面国际化语言
        env.setInternational(this.international);
        env.setSupportStatements(this.supportStatements);

        ObjectNode result = env.createObjectNode();
        result.put("title", env.getLocalResource(title));

        // 动作按钮栏
        if (this.actionBar != null) {
            result.setAll((ObjectNode) this.actionBar.generate(env));
        } else {
            result.set("actions", env.createArrayNode());
        }

        // 表单按照设定的显示行分组
        LinkedHashMap<String, List<FormViewer>> formViewerRows = new LinkedHashMap<>();
        for (FormViewer viewer : this.formViewers) {
            if (!formViewerRows.containsKey(viewer.getRow())) {
                formViewerRows.put(viewer.getRow(), new ArrayList<>());
            }
            formViewerRows.get(viewer.getRow()).add(viewer);
        }

        // 浏览表单
        ArrayNode viewerRowsNode = env.createArrayNode();
        for (Map.Entry<String, List<FormViewer>> entry : formViewerRows.entrySet()) {
            List<FormViewer> viewers = entry.getValue();

            // 生成当前行浏览表单
            ArrayNode viewersNode = env.createArrayNode();
            for (FormViewer viewer: viewers) {
                ObjectNode generated = (ObjectNode) viewer.generate(env);
                if (!viewer.isHidden()) {
                    viewersNode.add(generated);
                }
            }

            ObjectNode viewerRowNode = env.createObjectNode();
            viewerRowNode.set("viewers", viewersNode);
            viewerRowsNode.add(viewerRowNode);
        }
        result.set("viewerRows", viewerRowsNode);

        // 表格按照设定的显示行分组
        LinkedHashMap<String, List<Datatable>> datatableRows = new LinkedHashMap<>();
        for (Datatable datatable: this.datatables) {
            if (!datatableRows.containsKey(datatable.getRow())) {
                datatableRows.put(datatable.getRow(), new ArrayList<>());
            }
            datatableRows.get(datatable.getRow()).add(datatable);
        }

        // 浏览表格
        ArrayNode datatableRowsNode = env.createArrayNode();
        for (Map.Entry<String, List<Datatable>> entry : datatableRows.entrySet()) {
            List<Datatable> datatables = entry.getValue();

            // 生成当前行编辑表单
            ArrayNode datatablesNode = env.createArrayNode();
            for (Datatable datatable: datatables) {
                ObjectNode generated = (ObjectNode) datatable.generate(env);
                if (!datatable.isHidden()) {
                    datatablesNode.add(generated);
                }
            }

            ObjectNode datatableRowNode = env.createObjectNode();
            datatableRowNode.set("datatables", datatablesNode);
            datatableRowsNode.add(datatableRowNode);
        }
        result.set("datatableRows", datatableRowsNode);

        return result;
    }
}
