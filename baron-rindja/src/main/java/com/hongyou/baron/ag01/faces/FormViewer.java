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
 * 浏览表单
 *
 * @author Berlin
 */
public class FormViewer extends AbstractComponent implements Scheme {

    /**
     * 显示的行（一行可以显示多个表单）
     */
    @Getter
    private final String row;

    /**
     * 每行显示控件数
     */
    private final int spans;

    /**
     * 是否选项卡显示
     */
    private final boolean tab;

    /**
     * 表单面板宽度
     */
    private final String width;

    /**
     * 表单宽度
     */
    private final String formWidth;

    /**
     * 标签显示位置left/top
     */
    private final String placement;

    /**
     * 显示控件
     */
    private final List<Scene> scenes = new ArrayList<>();

    /**
     * 友好提示的列
     */
    private final String labelColumn;

    /**
     * 查询语句
     */
    @Getter
    private final Statement statement;

    /**
     * 加载表单定义
     *
     * @param element 控件元素定义
     */
    public FormViewer(final Element element) {
        super(element);
        this.spans = XmlUtil.getAttributeAsInt(element, "spans", 6);
        this.row = XmlUtil.getAttribute(element, "row", UUID.randomUUID().toString());
        this.tab = XmlUtil.getAttributeAsBool(element, "tab", false);
        this.width = XmlUtil.getAttribute(element, "width");
        this.formWidth = XmlUtil.getAttribute(element, "formWidth", "100%");
        this.placement = XmlUtil.getAttribute(element, "placement", "left");
        this.labelColumn = XmlUtil.getAttribute(element, "labelColumn");

        List<Element> sceneNodes = XmlUtil.getChildElements(element, "scene");
        sceneNodes.forEach(inputNode -> this.scenes.add(SceneFactories.getInstance().create(inputNode)));

        // 查询语句
        Element statement = XmlUtil.getChildElement(element, "statement");
        if (statement != null) {
            this.statement = new Statement(statement, false);
            this.statement.setResult(Statement.ResultType.SINGLE);
            this.statement.addFields(element, null, "scene");
        } else {
            this.statement = null;
        }
    }

    /**
     * 生成表单界面定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode result = (ObjectNode) super.generate(env);
        result.put("spans", this.spans);
        result.put("tab", this.tab);
        result.put("width", this.width);
        result.put("formWidth", this.formWidth);
        result.put("placement", this.placement);
        result.put("labelColumn", this.labelColumn);

        ArrayNode scenesNode = env.createArrayNode();
        this.scenes.forEach(scene -> {
            JsonNode generated = scene.generate(env);
            if (!scene.isHidden()) {
                scenesNode.add(generated);
            }
        });
        result.set("scenes", scenesNode);
        return result;
    }

    /**
     * 查询浏览表单数据
     *
     * @param env 运行参数
     */
    public JsonNode getData(final Environment env) {
        return (ObjectNode) this.statement.getData(env);
    }
}
