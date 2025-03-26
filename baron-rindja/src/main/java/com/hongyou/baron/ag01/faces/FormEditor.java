/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.ag01.faces;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.ag01.Scheme;
import com.hongyou.baron.util.XmlUtil;
import lombok.Getter;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 编辑表单
 *
 * @author Berlin
 */
public class FormEditor extends AbstractComponent implements Scheme {

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
     * 输入控件
     */
    private final List<Widget> widgets = new ArrayList<>();

    /**
     * 加载表单定义
     *
     * @param element 控件元素定义
     */
    public FormEditor(final Element element) {
        super(element);
        this.spans = XmlUtil.getAttributeAsInt(element, "spans", 4);
        this.row = XmlUtil.getAttribute(element, "row", UUID.randomUUID().toString());
        this.tab = XmlUtil.getAttributeAsBool(element, "tab", false);
        this.width = XmlUtil.getAttribute(element, "width");
        this.formWidth = XmlUtil.getAttribute(element, "formWidth", "100%");
        this.placement = XmlUtil.getAttribute(element, "placement", "left");

        List<Element> inputNodes = XmlUtil.getChildElements(element, "input");
        inputNodes.forEach(inputNode -> this.widgets.add(WidgetFactories.getInstance().create(inputNode)));
    }

    /**
     * 生成编辑表单界面定义
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

        ArrayNode widgetsNode = env.createArrayNode();
        this.widgets.forEach(widget -> {
            JsonNode generated = widget.generate(env);
            widgetsNode.add(generated);
        });
        result.set("widgets", widgetsNode);
        return result;
    }
}
