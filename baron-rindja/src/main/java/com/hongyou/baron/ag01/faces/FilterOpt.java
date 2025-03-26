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
import com.hongyou.baron.ag01.ValueModel;
import com.hongyou.baron.util.XmlUtil;
import lombok.Getter;
import org.w3c.dom.Element;

import java.util.List;

/**
 * 过滤器选项
 *
 * @author Berlin
 */
public class FilterOpt extends AbstractComponent {

    /**
     * 枚举表
     */
    private final String table;

    /**
     * 枚举字段
     */
    private final String column;

    /**
     * 过滤选项值（枚举）
     */
    @Getter
    public List<ValueModel> filterOptions;

    /**
     * 加载过滤器选项定义
     *
     * @param element 过滤器选项定义元素
     */
    public FilterOpt(final Element element) {
        super(element);
        this.table = XmlUtil.getAttribute(element, "table");
        this.column = XmlUtil.getAttribute(element, "column");
    }

    /**
     * 生成过滤器选项定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode result = (ObjectNode) super.generate(env);

        // 枚举字段
        if ("enum".equals(this.getType())) {
            this.filterOptions = env.getInternational().getValues(
                env.getLocal(), table + "." + column
            );
            result.set("filterOptions", env.convertValue(this.filterOptions));
        }
        return result;
    }
}
