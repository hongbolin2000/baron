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
package com.hongyou.baron.ag01.faces;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.ag01.Scheme;
import com.hongyou.baron.ag01.Sorter;
import com.hongyou.baron.ag01.Statement;
import com.hongyou.baron.util.XmlUtil;
import lombok.Getter;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据表格定义
 *
 * @author Hong Bo Lin
 */
public class Datatable implements Scheme {

    /**
     * 数据表名
     */
    @Getter
    private final String name;

    /**
     * 数据表格定义的列
     */
    private final List<Column> columns = new ArrayList<>();

    /**
     * 查询语句
     */
    private final Statement statement;

    /**
     * 双击事件
     */
    private DoubleClick doubleClick;

    /**
     * 过滤器定义
     */
    private final Filter filter;

    /**
     * 加载浏览表格定义
     *
     * @param element 浏览表格定义元素
     */
    public Datatable(final Element element, final Filter filter) {
        this.name = XmlUtil.getAttribute(element, "name", "main");

        // 加载双击事件
        Element doubleClickNode = XmlUtil.getChildElement(element, "double.click");
        if (doubleClickNode != null) {
            this.doubleClick = new DoubleClick(doubleClickNode);
        }

        // 加载定义的列
        List<Element> columns = XmlUtil.getGrandChildElements(element, "columns", "column");
        if (CollUtil.isNotEmpty(columns)) {
            columns.forEach(column -> this.columns.add(
                ColumnFactories.getInstance().create(column)
            ));
        }
        this.filter = filter;

        // 加载定义的查询语句
        Element statement = XmlUtil.getChildElement(element, "statement");
        if (statement != null) {
            this.statement = new Statement(statement, false);
            this.statement.addFields(element, "columns");
            this.statement.addArguments(this.columns);
        } else {
            this.statement = null;
        }
    }

    /**
     * 生成浏览表格界面定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = env.createObjectNode();
        ArrayNode columnsNode = env.createArrayNode();

        // 表格列
        this.columns.forEach(column -> {
            ObjectNode columnNode = (ObjectNode) column.generate(env);

            this.generateColumnFilter(env, columnNode);
            columnsNode.add(columnNode);
        });

        // 双击事件
        if (this.doubleClick != null) {
            root.set("doubleClick", this.doubleClick.generate(env));
        }

        root.put("name", this.name);
        root.set("columns", columnsNode);
        return root;
    }

    /**
     * 生成列过滤选项
     *
     * @param env 运行参数
     * @param columnNode 列json节点
     */
    private void generateColumnFilter(final Environment env, final ObjectNode columnNode) {
        if (this.filter == null) {
            return;
        }
        String filter = columnNode.get("filter").asText();
        if (filter.startsWith("@")) {
            FilterOpt opt = this.filter.getOptByName(filter.split("@")[1]);
            columnNode.set("filterOptions", env.convertValue(opt.getFilterOptions()));
            columnNode.put("filterType", opt.getType());
        }
    }

    /**
     * 分页查询表格数据
     *
     * @param env 运行参数
     * @param pageNumber 当前页面
     * @param pageSize 每页记录数
     */
    public JsonNode paginate(
            final Environment env, final Sorter sorter, final int pageNumber, final int pageSize
    ) {
        return this.statement.paginate(env, sorter, pageNumber, pageSize);
    }

    /**
     * 非分页查询表格数据
     *
     * @param env 运行参数
     */
    public JsonNode getData(final Environment env, final Sorter sorter) {
        return (JsonNode) this.statement.getData(env, sorter);
    }
}
