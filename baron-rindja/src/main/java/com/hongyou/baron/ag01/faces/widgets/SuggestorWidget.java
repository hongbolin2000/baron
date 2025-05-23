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
 * 查询建议器输入控件
 *
 * @author Berlin
 */
public class SuggestorWidget extends AbstractWidget {

    /**
     * 执行模式(select/input)
     */
    private final String mode;

    /**
     * 最大长度
     */
    private final int maxLength;

    /**
     * 模块号
     */
    private final String module;

    /**
     * 建议器名称
     */
    private final String suggestor;

    /**
     * 建议器选择时的脚本
     */
    private final String script;

    /**
     * 查询时的输入参数
     */
    private final String input;

    /**
     * 加载选择输入控件定义
     *
     * @param element 选择输入控件元素定义
     */
    protected SuggestorWidget(final Element element) {
        super(element);
        this.mode = XmlUtil.getAttribute(element, "mode", "select");
        this.maxLength = XmlUtil.getAttributeAsInt(element, "length", -1);
        this.module = XmlUtil.getAttribute(element, "module");
        this.suggestor = XmlUtil.getAttribute(element, "suggestor");
        this.script = XmlUtil.getAttributeOrChild(element, "script");
        this.input = XmlUtil.getAttribute(element, "input");
    }

    /**
     * 生成选择输入控件定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        root.put("mode", mode);
        root.put("maxLength", maxLength);
        root.put("module", module);
        root.put("suggestor", suggestor);
        root.put("script", script);
        root.put("input", input);
        return root;
    }
}
