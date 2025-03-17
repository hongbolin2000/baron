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
package com.hongyou.baron.ag01.faces.widgets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.ag01.faces.AbstractWidget;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

/**
 * 日期输入控件
 *
 * @author Hong Bo Lin
 */
public class DateWidget extends AbstractWidget {

    /**
     * 输入模式（date,datetime,daterange,month,year,quarter,week）
     */
    private final String mode;

    /**
     * 加载日期输入控件定义
     *
     * @param element 日期输入控件元素定义
     */
    protected DateWidget(final Element element) {
        super(element);
        this.mode = XmlUtil.getAttribute(element, "mode", "date");
    }

    /**
     * 生成日期输入控件定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        root.put("mode", mode);
        return root;
    }
}
