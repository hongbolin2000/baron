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

import java.util.HashMap;
import java.util.Iterator;

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
            String value = content.get(key).asText();
            this.put(key, value);
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
}
