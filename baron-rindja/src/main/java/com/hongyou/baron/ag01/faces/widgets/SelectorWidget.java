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
package com.hongyou.baron.ag01.faces.widgets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.ag01.faces.AbstractWidget;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

/**
 * 查询选择器输入控件
 *
 * @author Berlin
 */
public class SelectorWidget extends AbstractWidget {

    /**
     * 模块号
     */
    private final String module;

    /**
     * 选择器名称
     */
    private final String selector;

    /**
     * 选择器选择时的脚本
     */
    private final String script;

    /**
     * 查询时的输入参数
     */
    private final String input;

    /**
     * 显示标签列
     */
    private final String labelColumn;

    /**
     * 加载控件定义
     *
     * @param element 控件元素定义
     */
    protected SelectorWidget(final Element element) {
        super(element);
        this.module = XmlUtil.getAttribute(element, "module");
        this.selector = XmlUtil.getAttribute(element, "selector");
        this.script = XmlUtil.getAttributeOrChild(element, "script");
        this.input = XmlUtil.getAttribute(element, "input");
        this.labelColumn = XmlUtil.getAttribute(element, "labelColumn");
    }

    /**
     * 生成控件定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        root.put("module", module);
        root.put("selector", selector);
        root.put("script", script);
        root.put("input", input);
        root.put("labelColumn", labelColumn);
        return root;
    }
}
