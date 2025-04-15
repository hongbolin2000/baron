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
 * 表单显示列定义
 *
 * @author Berlin
 */
public class AbstractScene extends AbstractComponent implements Scene {

    /**
     * 控件占用列数
     */
    private final int spans;

    /**
     * 加载定义
     *
     * @param element 控件元素定义
     */
    protected AbstractScene(final Element element) {
        super(element);
        this.spans = XmlUtil.getAttributeAsInt(element, "spans", 1);
    }

    /**
     * 生成列定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode result = (ObjectNode) super.generate(env);
        result.put("spans", spans);
        return result;
    }
}
