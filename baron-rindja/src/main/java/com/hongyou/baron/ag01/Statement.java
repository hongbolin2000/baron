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

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.faces.Column;
import com.hongyou.baron.ag01.faces.FilterType;
import com.hongyou.baron.util.XmlUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import lombok.Setter;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 通用界面定义的查询语句
 *
 * @author Hong Bo Lin
 */
public class Statement {

    /**
     * 查询结果类型
     */
    protected interface ResultType {

        /**
         * 集合
         */
        String LIST = "list";

        /**
         * 单值集合
         */
        String VALUES = "values";

        /**
         * 单条记录
         */
        String SINGLE = "single";

        /**
         * 单个值
         */
        String VALUE = "value";
    }

    /**
     * 查询结果类型
     */
    @Setter
    private String result;

    /**
     * 查询的表名
     */
    private String table;

    /**
     * 默认排序SQL
     */
    private String orderBy;

    /**
     * 查询参数(条件过滤参数)
     */
    private final List<Argument> arguments = new ArrayList<>();

    /**
     * 查询的字段(需要展示到界面的字段)
     */
    private final List<Field> fields = new ArrayList<>();

    /**
     * 加载查询语句定义
     *
     * @param element 查询语句定义元素
     */
     public Statement(final Element element, boolean isSupportStatement) {

        // 查询的数据表
        Element tableNode = XmlUtil.getChildElement(element, "table");
        if (tableNode == null) {
            return;
        }
        this.result = XmlUtil.getAttribute(element, "result", ResultType.LIST);
        this.table = XmlUtil.getAttribute(tableNode, "name");

        // Support Statement
        if (isSupportStatement) {
            this.addFields(element, null, "column");
        }

        // 查询参数
        List<Element> params = XmlUtil.getChildElements(element, "param");
        params.forEach(param -> this.arguments.add(new Argument(param)));

        // 排序SQL
         Element orderNode = XmlUtil.getChildElement(element, "order");
         if (orderNode != null) {
             this.orderBy = XmlUtil.getAttribute(orderNode, "by");
         }
     }

    /**
     * 加载界面中定义的查询字段
     *
     * @param element 界面定义的根元素
     * @param parentName 界面字段定义根元素节点名称
     * @param childName 界面字段定义子元素节点名称
     */
    public void addFields(final Element element, final String parentName, final String childName) {
        List<Element> fieldNodes = XmlUtil.getGrandChildElements(element, parentName, childName);
        fieldNodes.forEach(node -> this.fields.add(new Field(node)));
    }

    /**
     * 将数据表格中可以过滤的列添加到参数定义中
     *
     * @param columns 表格列
     */
    public void addArguments(final List<Column> columns) {
        for (Column column : columns) {
            if (StrUtil.isBlank(column.getFilter())) {
                continue;
            }
            boolean existed = this.arguments.stream().anyMatch(i -> i.getExpr().equals(column.getName()));
            if (!existed) {
                String sqlFilterType = getSQLFilterType(column.getFilter());
                this.arguments.add(new Argument(column.getName(), column.getExpr(), sqlFilterType));
            }
        }
    }

    /**
     * 根据表格列定义的过滤类型返回SQL类型
     *
     * @param filter 过滤器定义
     */
    private String getSQLFilterType(final String filter) {
        // 文本模糊搜索
        boolean isText = FilterType.isText(filter);
        if (isText) {
            return ArgumentType.LIKE;
        }
        boolean isDate = FilterType.isDate(filter);
        if (isDate) {
            return ArgumentType.DATE;
        }
        boolean isOpt = FilterType.isOpt(filter);
        if (isOpt) {
            return ArgumentType.EQ;
        }
        return ArgumentType.LIKE;
    }

    /**
     * 执行查询语句 - 分页查询
     *
     * @param env 运行参数
     * @param pageNumber 当前页码
     * @param pageSize 每页记录数
     */
    public JsonNode paginate(
        final Environment env, final Sorter sorter, final int pageNumber, final int pageSize)
    {
        ObjectNode result = env.createObjectNode();
        ArrayNode data = env.createArrayNode();

        // 构造查询条件
        QueryWrapper wrapper = this.resolveQuery(env);
        this.resolveOrderBy(sorter, wrapper);

        // 准备分页查询对象（设置第一页才查询总记录数）
        Page<Row> page = new Page<>(pageNumber, pageSize, (pageNumber == 1) ? -1 : 1);
        page.setTotalPage((pageNumber == 1) ? -1 : pageNumber  + 1);

        // 查询并生成返回数据
        Page<Row> paginate = Db.paginate(this.table, page, wrapper);
        paginate.getRecords().forEach(record -> data.add(this.loadRow(env, record)));

        result.set("data", data);
        result.put("total", paginate.getTotalRow());
        return result;
    }

    /**
     * 字段排序
     *
     * @param sorter 排序参数
     * @param wrapper 条件构造器
     */
    private void resolveOrderBy(final Sorter sorter, final QueryWrapper wrapper) {
        if (sorter != null) {
            Stream<Field> fieldStream = this.fields.stream().filter(i -> i.getName().equals(sorter.getName()));
            fieldStream.findFirst().ifPresent(field -> {
                wrapper.orderBy(field.getExpr(), sorter.getOrder() == Sorter.Order.asc);
            });
            return;
        }
        if (this.orderBy != null) {
            wrapper.orderBy(this.orderBy);
        }
    }

    /**
     * 执行查询语句
     *
     * @param env 运行参数
     */
    public Object getData(final Environment env) {
        return this.getData(env, null);
    }

    /**
     * 执行查询语句
     *
     * @param env 运行参数
     * @param sorter 排序字段
     */
    public Object getData(final Environment env, final Sorter sorter) {

        // 构造查询条件
        QueryWrapper wrapper = this.resolveQuery(env);
        this.resolveOrderBy(sorter, wrapper);

        // 查询集合列表
        if (ResultType.LIST.equals(this.result)) {
            List<Row> rows = Db.selectListByQuery(this.table, wrapper);
            ArrayNode data = env.createArrayNode();
            rows.forEach(record -> data.add(this.loadRow(env, record)));
            return data;
        }

        // 查询单值集合
        if (ResultType.VALUES.equals(this.result)) {
            List<Row> rows = Db.selectListByQuery(this.table, wrapper);
            return this.loadValues(env, rows);
        }

        // 查询单条记录
        if (ResultType.SINGLE.equals(this.result)) {
            Row record = Db.selectOneByQuery(this.table, wrapper);
            return this.loadRow(env, record);
        }

        // 查询单个值
        if (ResultType.VALUE.equals(this.result)) {
            Row record = Db.selectOneByQuery(this.table, wrapper);
            if (CollUtil.isEmpty(this.fields)) return StrUtil.EMPTY;
            return this.fields.get(0).getData(env, record).asText();
        }
        return null;
    }

    /**
     * 生成查询参数
     *
     * @param env 运行参数
     */
    private QueryWrapper resolveQuery(final Environment env) {
        QueryWrapper wrapper = new QueryWrapper();

        // 设置查询的字段(只查询必要的字段，减轻数据库查询压力)
        String[] columns = this.fields.stream().map(Field::getExpr).toArray(String[]::new);
        wrapper.select(columns);

        // 生成查询条件
        this.arguments.forEach(argument -> argument.resolveWrapper(env, wrapper));
        return wrapper;
    }

    /**
     * 返回单条记录
     *
     * @param env 运行参数
     * @param record 数据记录
     */
    private JsonNode loadRow(final Environment env, final Row record) {
        ObjectNode data = env.createObjectNode();
        this.fields.forEach(field -> {
            data.set(field.getName(), field.getData(env, record));
        });
        return data;
    }

    /**
     * 返回单个值集合
     *
     * @param env 运行参数
     * @param rows 数据记录
     */
    private Object[] loadValues(final Environment env, final List<Row> rows) {
        Object[] data = new Object[rows.size()];
        for (int i = 0; i < rows.size(); i++) {
            String value = this.fields.get(0).getData(env, rows.get(i)).asText();
            data[i] = value;
        }
        return data;
    }
}
