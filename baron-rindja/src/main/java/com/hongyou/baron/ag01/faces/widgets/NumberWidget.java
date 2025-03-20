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
 * 数字输入控件
 *
 * @author Hong Bo Lin
 */
public class NumberWidget extends AbstractWidget {

    /**
     * 小数精度
     */
    private final int scale;

    /**
     * 最小值
     */
    private final int min;

    /**
     * 最大值
     */
    private final int max;

    /**
     * 前缀
     */
    private final String prefix;

    /**
     * 后缀
     */
    private final String suffix;

    /**
     * 数字输入时的脚步
     */
    private final String script;

    /**
     * 加载数字输入控件定义
     *
     * @param element 数字输入控件元素定义
     */
    protected NumberWidget(final Element element) {
        super(element);
        this.scale = XmlUtil.getAttributeAsInt(element, "scale", 0);
        this.min = XmlUtil.getAttributeAsInt(element, "min", Integer.MIN_VALUE);
        this.max = XmlUtil.getAttributeAsInt(element, "max", Integer.MAX_VALUE);
        this.prefix = XmlUtil.getAttribute(element, "prefix");
        this.suffix = XmlUtil.getAttribute(element, "suffix");
        this.script = XmlUtil.getAttributeOrChild(element, "script");
    }

    /**
     * 生成数字输入控件定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        root.put("scale", scale);
        root.put("min", min);
        root.put("max", max);
        root.put("prefix", prefix);
        root.put("suffix", suffix);
        root.put("script", script);
        return root;
    }
}
