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
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 浏览表格容器
 *
 * @author Berlin
 */
public class Datatables implements Scheme {

    /**
     * 表格
     */
    List<Datatable> datatables = new ArrayList<>();

    /**
     * 加载动作栏定义
     *
     * @param element 动作栏定义元素
     */
    public Datatables(final Element element) {
        List<Element> datatables = XmlUtil.getChildElements(element, "datatable");
//        datatables.forEach(datatable -> this.datatables.add(new Datatable(datatable)));
    }

    /**
     * 生成动作按钮栏界面定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ArrayNode tableNodes = env.createArrayNode();

        this.datatables.forEach(datatable -> {
            tableNodes.add(datatable.generate(env));
        });
        return tableNodes;
    }

    /**
     * 查询表格数据
     *
     * @param env 运行参数
     */
    public JsonNode getData(final Environment env) {
        ObjectNode result = env.createObjectNode();
        this.datatables.forEach(datatable -> {
            JsonNode data = datatable.getData(env, null);
            result.set(datatable.getName(), data);
        });
        return result;
    }
}
