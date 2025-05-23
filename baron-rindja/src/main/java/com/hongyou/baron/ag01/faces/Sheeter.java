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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.ag01.Scheme;
import com.hongyou.baron.ag01.Statement;
import com.hongyou.baron.util.XmlUtil;
import lombok.Getter;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 编辑表格
 *
 * @author Berlin
 */
public class Sheeter extends AbstractComponent implements Scheme {

    /**
     * 输入控件
     */
    private final List<Widget> widgets = new ArrayList<>();

    /**
     * 编辑表格宽度
     */
    private final String width;

    /**
     * 编辑表格最大高度
     */
    private final String maxHeight;

    /**
     * 显示的行（一行可以显示多个表单）
     */
    @Getter
    private final String row;

    /**
     * 是否选项卡显示
     */
    private final boolean tab;

    /**
     * 是否可添加数据
     */
    private final boolean added;

    /**
     * 是否可修改数据
     */
    private final boolean updated;

    /**
     * 是否必填
     */
    private final boolean required;

    /**
     * 查询语句
     */
    private Statement statement;

    /**
     * 唯一键
     */
    private final String unique;

    /**
     * 确认操作提示表格字段内容
     */
    private final String labelColumn;

    /**
     * 加载编辑表格定义
     *
     * @param element 控件元素定义
     */
    public Sheeter(final Element element) {
        super(element);
        this.width = XmlUtil.getAttribute(element, "width");
        this.maxHeight = XmlUtil.getAttribute(element, "maxHeight", "400");
        this.tab = XmlUtil.getAttributeAsBool(element, "tab", false);
        this.added = XmlUtil.getAttributeAsBool(element, "added", true);
        this.updated = XmlUtil.getAttributeAsBool(element, "updated", true);
        this.row = XmlUtil.getAttribute(element, "row", UUID.randomUUID().toString());
        this.required = XmlUtil.getAttributeAsBool(element, "required", false);
        this.unique = XmlUtil.getAttribute(element, "unique");
        this.labelColumn = XmlUtil.getAttribute(element, "labelColumn");

        // 查询语句
        Element statement = XmlUtil.getChildElement(element, "statement");
        if (statement != null) {
            this.statement = new Statement(statement, false);
            this.statement.addFields(element, "inputs", "input");
        }

        List<Element> inputNodes = XmlUtil.getGrandChildElements(element, "inputs", "input");
        inputNodes.forEach(inputNode -> this.widgets.add(WidgetFactories.getInstance().create(inputNode)));
    }

    /**
     * 生成编辑表格界面定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode result = (ObjectNode) super.generate(env);

        ArrayNode widgetsNode = env.createArrayNode();
        this.widgets.forEach(widget -> {
            JsonNode generated = widget.generate(env);
            if (!widget.isHidden()) {
                widgetsNode.add(generated);
            }
        });
        result.put("width", this.width);
        result.set("widgets", widgetsNode);
        result.put("tab", this.tab);
        result.put("added", this.added);
        result.put("updated", this.updated);
        result.put("row", this.row);
        result.put("maxHeight", this.maxHeight);
        result.put("required", this.required);
        result.put("unique", this.unique);
        result.put("labelColumn", this.labelColumn);
        return result;
    }

    /**
     * 查询编辑表格数据
     *
     * @param env 运行参数
     */
    public JsonNode getData(final Environment env) {
        return (JsonNode) this.statement.getData(env);
    }
}
