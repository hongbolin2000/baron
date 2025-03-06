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
import lombok.Getter;
import org.w3c.dom.Element;

/**
 * 表格列定义
 *
 * @author Hong Bo Lin
 */
public class AbstractColumn extends AbstractComponent implements Column {

    /**
     * 列标题
     */
    private final String title;

    /**
     * 列宽度
     */
    private final String width;

    /**
     * 是否可过滤
     */
    @Getter
    private final boolean filterable;

    /**
     * 参数表达式(对应数据库字段)
     */
    @Getter
    private final String expr;

    /**
     * 加载定义
     *
     * @param element 控件元素定义
     */
    protected AbstractColumn(final Element element) {
        super(element);
        this.title = XmlUtil.getAttribute(element, "title");
        this.width = XmlUtil.getAttribute(element, "width");
        this.filterable = XmlUtil.getAttributeAsBool(element, "filterable");
        this.expr = XmlUtil.getAttribute(element, "expr");
    }

    /**
     * 生成列定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        root.put("title", env.getLocalResource(this.title));
        root.put("width", width);
        return root;
    }

    /**
     * 获取列定义的name
     */
    @Override
    public String getName() {
        return super.getName();
    }
}
