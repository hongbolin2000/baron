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
import com.hongyou.baron.util.ListUtil;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 通用浏览界面定义
 *
 * @author Berlin
 */
public class Grider implements Scheme {

    /**
     * 过滤器
     */
    private Filter filter;

    /**
     * 主数据表
     */
    private final Datatable datatable;

    /**
     * 子数据表
     */
    private final List<Datatable> subTables = new ArrayList<>();

    /**
     * 界面国际化语言
     */
    private final International international;

    /**
     * 全局查询语句
     */
    private final HashMap<String, Statement> supportStatements = new HashMap<>();

    /**
     * 加载界面定义
     *
     * @param root 界面定义配置节点
     */
    public Grider(final Element root) {

        // 国际化语言
        this.international = new International(root);

        // 过滤器
        Element filters = XmlUtil.getChildElement(root, "filters");
        if (filters != null) {
            this.filter = new Filter(filters);
        }

        // 全局查询语句
        List<Element> statements = XmlUtil.getGrandChildElements(root, "support-statements", "statement");
        statements.forEach(statement -> {
            String name = XmlUtil.getAttribute(statement, "name");
            this.supportStatements.put(name, new Statement(statement, true));
        });

        // 主数据表
        Element datatableNode = XmlUtil.getChildElement(root, "datatable");
        this.datatable = new Datatable(datatableNode, this.filter);

        // 子数据表
        List<Element> subTables = XmlUtil.getChildElements(root, "subtable");
        subTables.forEach(subTable -> this.subTables.add(new Datatable(subTable, this.filter)));
    }

    /**
     * 生成浏览界面定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = env.createObjectNode();

        // 传入当前界面国际化语言
        env.setSupportStatements(this.supportStatements);
        env.setInternational(this.international);

        // 过滤器
        root.set("filters", this.filter.generate(env));

        // 主数据表格
        if (this.datatable != null) {
            root.set("datatable", this.datatable.generate(env));
        }

        // 子数据表格
        if (ListUtil.isNotEmpty(this.subTables)) {
            ArrayNode tableNodes = env.createArrayNode();
            this.subTables.forEach(datatable -> {
                JsonNode generated = datatable.generate(env);
                if (!datatable.isHidden()) {
                    tableNodes.add(generated);
                }
            });
            root.set("subTables", tableNodes);
        }
        return root;
    }

    /**
     * 查询主数据表数据
     *
     * @param env 运行参数
     * @param pageNumber 当前页码
     * @param pageSize 每页记录条数
     */
    public JsonNode getTableData(
        final Environment env, final Sorter sorter, final int pageNumber, final int pageSize
    ) {

        // 传入当前界面国际化语言
        env.setSupportStatements(this.supportStatements);
        env.setInternational(this.international);

        ObjectNode result = env.createObjectNode();
        result.setAll((ObjectNode) this.datatable.paginate(env, sorter, pageNumber, pageSize));
        return result;
    }

    /**
     * 查询子数据表数据
     *
     * @param env 运行参数
     * @param subDataTables 需查询的字数据表名称
     */
    public JsonNode getSubTablesData(
        final Environment env, final String subDataTables, final Sorter sorter
    ) {
        ObjectNode result = env.createObjectNode();

        // 传入当前界面国际化语言
        env.setSupportStatements(this.supportStatements);
        env.setInternational(this.international);

        List<String> tables = Arrays.asList(subDataTables.split(","));
        this.subTables.forEach(datatable -> {
            if (tables.contains(datatable.getName())) {
                JsonNode data = datatable.getData(env, sorter);
                result.set(datatable.getName(), data);
            }
        });
        return result;
    }
}
