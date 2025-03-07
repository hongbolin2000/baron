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

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 通用界面中的全局变量，变量名一定是要唯一的
 *
 * @author Hong Bo Lin
 */
public class Variables extends HashMap<String, Object> {

    /**
     * 加入简单的json格式数据
     *
     * @param content 需加入全局变量的内容
     */
    protected void addSimpleJson(final JsonNode content) {
        if (content == null) {
            return;
        }
        Iterator<String> fieldNames = content.fieldNames();
        while (fieldNames.hasNext()) {
            String key = fieldNames.next();
            this.isTextNode(key, content);
            this.isArrayNode(key, content);
        }
    }

    /**
     * 解析文本属性
     *
     * @param key 键
     * @param content 待解析的内容
     */
    private void isTextNode(final String key, final JsonNode content) {
        if (content.get(key) instanceof TextNode) {
            this.put(key, content.get(key).asText());
        }
    }

    /**
     * 解析数组属性
     *
     * @param key 键
     * @param content 待解析的内容
     */
    private void isArrayNode(final String key, final JsonNode content) {
        if (content.get(key) instanceof ArrayNode nodes) {
            List<Object> values = new ArrayList<>();
            for (int i = 0; i < nodes.size(); i++) {
                if (nodes.get(i) instanceof LongNode node) {
                    values.add(node.asLong());
                } else {
                    values.add(nodes.get(i).asText());
                }
            }
            this.put(key, values);
        }
    }

    /**
     * 从变量中获取字符型数据，如果为空串则返回空
     *
     * @param key 变量key
     */
    protected String getVariableAsString(final String key) {
        if (this.containsKey(key)) {
            String value = String.valueOf(this.get(key));
            return StrUtil.isBlank(value) ? null : value;
        }
        return null;
    }

    /**
     * 从变量中获取集合数据
     *
     * @param key 变量key
     */
    @SuppressWarnings("unchecked")
    protected List<Object> getVariableAsList(final String key) {
        if (this.containsKey(key)) {
            return (List<Object>) this.get(key);
        }
        return null;
    }
}
