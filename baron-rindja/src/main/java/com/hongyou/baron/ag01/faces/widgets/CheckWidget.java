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
import com.hongyou.baron.ag01.ValueModel;
import com.hongyou.baron.ag01.faces.AbstractWidget;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择输入控件
 *
 * @author Berlin
 */
public class CheckWidget extends AbstractWidget {

    /**
     * 展现模式(check,switch)
     */
    private final String mode;

    /**
     * 选中时的值
     */
    private final String checked;

    /**
     * 未选中时的值
     */
    private final String unchecked;

    /**
     * 加载选择输入控件定义
     *
     * @param element 选择输入控件元素定义
     */
    protected CheckWidget(final Element element) {
        super(element);
        this.checked = XmlUtil.getAttribute(element, "checked");
        this.unchecked = XmlUtil.getAttribute(element, "unchecked");
        this.mode = XmlUtil.getAttribute(element, "mode", "check");
    }

    /**
     * 生成选择输入控件定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        root.put("checked", checked);
        root.put("unchecked", unchecked);
        root.put("mode", mode);
        return root;
    }
}
