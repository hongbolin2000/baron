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
 * 文本输入控件
 *
 * @author Berlin
 */
public class TextWidget extends AbstractWidget {

    /**
     * 输入模式(text,textarea,password)
     */
    private final String mode;

    /**
     * 最大长度
     */
    private final int maxLength;

    /**
     * 前缀
     */
    private final String prefix;

    /**
     * 后缀
     */
    private final String suffix;

    /**
     * 加载文本输入控件定义
     *
     * @param element 文本输入控件元素定义
     */
    protected TextWidget(final Element element) {
        super(element);
        this.maxLength = XmlUtil.getAttributeAsInt(element, "length", -1);
        this.mode = XmlUtil.getAttribute(element, "mode", "text");
        this.prefix = XmlUtil.getAttribute(element, "prefix");
        this.suffix = XmlUtil.getAttribute(element, "suffix");
    }

    /**
     * 生成文本输入控件定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        root.put("maxLength", maxLength);
        root.put("mode", mode);
        root.put("prefix", prefix);
        root.put("suffix", suffix);
        return root;
    }
}
