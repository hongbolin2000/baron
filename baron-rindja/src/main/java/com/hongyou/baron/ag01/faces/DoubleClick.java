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
import com.hongyou.baron.ag01.Scheme;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

/**
 * 控件双击事件
 *
 * @author Hong Bo Lin
 */
public class DoubleClick implements Scheme {

    /**
     * 双击执行事件类型
     */
    private final String type;

    /**
     * 查询的数据表格(多个使用逗号分隔)
     */
    private String datatables;

    /**
     * 输入参数
     */
    private String input;

    /**
     * 双击执行事件类型
     */
    private interface Type {

        /**
         * 查询
         */
        String Query = "query";
    }

    /**
     * 加载浏览表格双击事件
     *
     * @param element 浏览表格定义元素
     */
    public DoubleClick(final Element element) {
        this.type = XmlUtil.getAttribute(element, "type", "query");

        if (Type.Query.equals(this.type)) {
            this.datatables = XmlUtil.getAttribute(element, "datatables");
            this.input = XmlUtil.getAttribute(element, "input");
        }
    }

    /**
     * 生成浏览表格双击事件定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = env.createObjectNode();
        root.put("type", this.type);

        if (Type.Query.equals(this.type)) {
            root.put("datatables", this.datatables);
            root.put("input", this.input);
        }
        return root;
    }
}
