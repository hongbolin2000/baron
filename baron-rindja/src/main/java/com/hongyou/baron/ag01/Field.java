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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hongyou.baron.util.*;
import com.mybatisflex.core.row.Row;
import lombok.Data;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 通用界面中的查询字段（用来格式化数据）
 *
 * @author Berlin
 */
@Data
public class Field {

    /**
     * 字段名(格式化后传出前端)
     */
    private final String name;

    /**
     * 表达式(数据库字段)
     */
    private final String expr;

    /**
     * 缺省值
     */
    private final String defaultValue;

    /**
     * 格式化
     */
    private final String format;

    /**
     * 脱敏
     */
    private final String tm;

    /**
     * 加载字段定义
     *
     * @param element 字段定义的元素
     */
    public Field(final Element element) {
        this.name = XmlUtil.getAttribute(element, "name");
        this.expr = XmlUtil.getAttribute(element, "expr");
        this.format = XmlUtil.getAttribute(element, "format");
        this.defaultValue = XmlUtil.getAttribute(element, "default");
        this.tm = XmlUtil.getAttribute(element, "tm");
    }

    /**
     * 格式化当前字段的数据
     *
     * @param env 运行参数
     * @param row 数据行
     */
    public JsonNode getData(final Environment env, final Row row) {
        if (StringUtil.isBlank(this.expr)) {
            return JsonUtil.convertValue(this.defaultValue);
        }

        Object value = row.getString(this.expr);
        boolean formated = false;

        // 日期格式化
        if (format.startsWith("!")) {
            String[] exprs = this.expr.split(",");
            if (exprs.length == 2) {
                ArrayNode values = env.createArrayNode();
                values.add(DateUtil.format(row.getDate(exprs[0]), this.format.substring(1)));
                values.add(DateUtil.format(row.getDate(exprs[1]), this.format.substring(1)));
                value = values;
            } else {
                value = DateUtil.format(row.getDate(this.expr), this.format.substring(1));
            }
            formated = true;
        }

        // 枚举格式化
        if (format.startsWith("@@")) {
            value = env.getInternational().getValue(
                env.getLocal(), format.substring(2), row.getString(this.expr)
            );
            formated = true;
        }

        // 数字格式化
        if (format.startsWith("%")) {
            DecimalFormat format = new DecimalFormat(this.format.substring(1));
            value = new BigDecimal(format.format(row.getBigDecimal(this.expr)));
            formated = true;
        }

        // 表达式
        if (StringUtil.isNotBlank(this.format) && !formated) {
            value = ExpressionUtil.eval(this.format, row);
        }

        // 数据脱敏
        if (StringUtil.isNotBlank(this.tm)) {
            String[] split = this.tm.split(":");
            if (split.length == 2) {
                int start = Integer.parseInt(split[0]);
                int end = Integer.parseInt(split[1]);
                value = StringUtil.replace(String.valueOf(value), start, end, "*");
            }
        }
        return env.convertValue(value);
    }
}
