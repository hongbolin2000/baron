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
package com.hongyou.baron.ag01;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.faces.Datatable;
import com.hongyou.baron.ag01.faces.Filter;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

/**
 * 通用查询选择器定义
 *
 * @author Berlin
 */
public class Selector implements Scheme {

    /**
     * 界面国际化语言
     */
    private final International international;

    /**
     * 最多查询记录条数
     */
    private final int maxRows;

    /**
     * 数据表格
     */
    private final Datatable datatable;

    /**
     * 加载界面定义
     *
     * @param root 界面定义配置节点
     */
    public Selector(final Element root) {

        // 国际化语言
        this.international = new International(root);

        Element maxRowsNode = XmlUtil.getChildElement(root, "maxRows");
        if (maxRowsNode != null) {
            maxRows = Integer.parseInt(XmlUtil.getTextContent(maxRowsNode));
        } else {
            maxRows = 10;
        }

        // 过滤器
        Filter filter;
        Element filters = XmlUtil.getChildElement(root, "filters");
        if (filters != null) {
            filter = new Filter(filters);
        } else {
            filter = null;
        }

        // 数据表
        Element datatableNode = XmlUtil.getChildElement(root, "datatable");
        this.datatable = new Datatable(datatableNode, filter);
    }

    /**
     * 生成界面定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = env.createObjectNode();

        // 传入当前界面国际化语言
        env.setInternational(this.international);

        // 数据表格
        root.set("datatable", this.datatable.generate(env));
        return root;
    }

    /**
     * 查询选择器数据
     */
    public JsonNode getData(final Environment env, final Sorter sorter) {
        // 传入当前界面国际化语言
        env.setInternational(this.international);

        ObjectNode result = env.createObjectNode();
        result.set("data", this.datatable.paginate(env, sorter, 1, this.maxRows));
        return result;
    }
}
