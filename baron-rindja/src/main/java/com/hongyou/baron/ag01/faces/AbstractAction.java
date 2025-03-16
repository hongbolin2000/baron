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
 * 动作按钮定义
 *
 * @author Hong Bo Lin
 */
public abstract class AbstractAction extends AbstractComponent implements Action {

    /**
     * 图标
     */
    private final String icon;

    /**
     * 是否显示在更多选项中
     */
    private final boolean option;

    /**
     * 危险操作
     */
    private final boolean danger;

    /**
     * 加载定义
     *
     * @param element 动作按钮定义元素
     */
    protected AbstractAction(final Element element) {
        super(element);
        this.icon = XmlUtil.getAttribute(element, "icon", "");
        this.option = XmlUtil.getAttributeAsBool(element, "option", false);
        this.danger = XmlUtil.getAttributeAsBool(element, "danger", false);
    }

    /**
     * 生成动作按钮定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        root.put("icon", icon);
        root.put("option", option);
        root.put("danger", danger);
        return root;
    }
}
