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
package com.hongyou.baron.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Json工具类
 *
 * @author Hong Bo Lin
 */
public class JsonUtil {

    /**
     * JSON转换器
     */
    private static final ObjectMapper mapper = createDefaultObjectMapper();

    /**
     * @return 生成缺省的（Jackson ObjectMapper）
     */
    private static ObjectMapper createDefaultObjectMapper() {

        ObjectMapper result = new ObjectMapper();
        result.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        return result;
    }

    /**
     * @return JSON序列和反序列器
     */
    public static ObjectMapper getObjectMapper() {
        return mapper;
    }

    /**
     * @return 生成一个对象形式的JSON节点
     */
    public static ObjectNode createObjectNode() {
        return mapper.createObjectNode();
    }

    /**
     * @return 生成一个对象形式的JSON节点
     */
    public static ArrayNode createArrayNode() {
        return mapper.createArrayNode();
    }

    /**
     * 将一个Java对象，转换为JSON对象
     *
     * @param value Java对象
     * @return JSON对象
     */
    public static JsonNode convertValue(final Object value) {

        if (value != null) {
            return mapper.convertValue(value, JsonNode.class);
        } else {
            return NullNode.getInstance();
        }
    }

    /**
     * 将一个Java POJO对象实例转换为JSON字符串，如果对象是空（null），将返回空（null）。
     *
     * @param obj Java POJO对象
     * @return 序列化后字符串
     */
    public static String writeObject(final Object obj) throws JsonProcessingException {
        return (obj != null) ? mapper.writeValueAsString(obj) : null;
    }

    /**
     * 将字符串转换为一个Java POJO对象，如果字符串是空（null），将返回空（null）
     *
     * @param str 待转换为对象的字符串
     * @param type 对象的数据类型
     * @param <T> 对象的类型
     * @return 转换后的对象实例，或空
     */
    public static <T> T readObject(final String str, final Class<T> type) throws JsonProcessingException {
        return (str != null) ? mapper.readValue(str, type) : null;
    }
}
