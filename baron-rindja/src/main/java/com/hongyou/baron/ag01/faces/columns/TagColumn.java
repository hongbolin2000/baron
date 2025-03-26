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
 * 表格tag签列
 *
 * @author Berlin
 */
public class TagColumn extends AbstractColumn {

    /**
     * 成功标签展示表达式
     */
    private final String success;

    /**
     * 警告标签展示表达式
     */
    private final String warning;

    /**
     * 异常标签展示表达式
     */
    private final String error;

    /**
     * 加载表格tag标签列定义
     *
     * @param element 表格tag标签列元素定义
     */
    protected TagColumn(final Element element) {
        super(element);
        this.success = XmlUtil.getAttribute(element, "success");
        this.warning = XmlUtil.getAttribute(element, "warning");
        this.error = XmlUtil.getAttribute(element, "error");
    }

    /**
     * 生成表格tag标签列定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        root.put("success", success);
        root.put("warning", warning);
        root.put("error", error);
        return root;
    }
}
