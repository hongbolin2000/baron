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
package com.hongyou.baron.ag01.faces.columns;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.ag01.faces.AbstractColumn;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

/**
 * 表格选择列
 *
 * @author Berlin
 */
public class SelectionColumn extends AbstractColumn {

    /**
     * 是否单选
     */
    private final boolean single;

    /**
     * 禁用表达式
     */
    private final String disabled;

    /**
     * 加载表格选择列定义
     *
     * @param element 表格标签列元素定义
     */
    protected SelectionColumn(final Element element) {
        super(element);
        this.single = XmlUtil.getAttributeAsBool(element, "single", false);
        this.disabled = XmlUtil.getAttribute(element, "disabled");
    }

    /**
     * 生成表格选择列定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        root.put("single", this.single);
        root.put("disabled", this.disabled);
        return root;
    }

    /**
     * 不可导出
     */
    @Override
    public boolean isExportable() {
        return false;
    }
}
