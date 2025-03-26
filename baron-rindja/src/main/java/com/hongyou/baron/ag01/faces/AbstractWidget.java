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
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

/**
 * 输入控件定义
 *
 * @author Berlin
 */
public class AbstractWidget extends AbstractComponent implements Widget {

    /**
     * 控件占用列数
     */
    private final int spans;

    /**
     * 是否必填
     */
    private final boolean required;

    /**
     * 缺省值
     */
    private final String defaultValue;

    /**
     * 禁用表达式
     */
    private final String disabled;

    /**
     * 控件宽度
     */
    private final String width;

    /**
     * 加载定义
     *
     * @param element 控件元素定义
     */
    protected AbstractWidget(Element element) {
        super(element);
        this.spans = XmlUtil.getAttributeAsInt(element, "spans", 1);
        this.required = XmlUtil.getAttributeAsBool(element, "required", false);
        this.defaultValue = XmlUtil.getAttribute(element, "default");
        this.disabled = XmlUtil.getAttribute(element, "disabled");
        this.width = XmlUtil.getAttribute(element, "width");
    }

    /**
     * 生成控件定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode result = (ObjectNode) super.generate(env);
        result.put("spans", spans);
        result.put("required", required);
        result.put("default", defaultValue);
        result.put("disabled", disabled);
        result.put("width", width);
        return result;
    }
}
