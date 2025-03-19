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
 * 表格脚本列按钮
 *
 * @author Hong Bo Lin
 */
public class ScriptLinkColumn extends AbstractColumn {

    /**
     * 执行链接(mode为remote则是服务器地址，为Script则是JavaScript函数)
     */
    private final String link;

    /**
     * 按钮点击执行模式(script，remote)
     */
    private final String mode;

    /**
     * 按钮图标
     */
    private final String icon;

    /**
     * 确认弹框提示表格字段内容
     */
    private final String labelColumn;

    /**
     * 禁用表达式
     */
    private final String disabled;

    /**
     * 危险操作
     */
    private final boolean danger;

    /**
     * 是否显示在右键选项中
     */
    private final boolean option;

    /**
     * 加载控件定义
     *
     * @param element 控件元素定义
     */
    protected ScriptLinkColumn(final Element element) {
        super(element);
        this.link = XmlUtil.getAttribute(element, "link");
        this.mode = XmlUtil.getAttribute(element, "mode", "remote");
        this.icon = XmlUtil.getAttribute(element, "icon");
        this.labelColumn = XmlUtil.getAttribute(element, "labelColumn");
        this.disabled = XmlUtil.getAttribute(element, "disabled");
        this.danger = XmlUtil.getAttributeAsBool(element, "danger", false);
        this.option = XmlUtil.getAttributeAsBool(element, "option", true);
    }

    /**
     * 生成表格脚本列按钮定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        root.put("link", this.link);
        root.put("mode", this.mode);
        root.put("icon", this.icon);
        root.put("labelColumn", this.labelColumn);
        root.put("disabled", this.disabled);
        root.put("danger", this.danger);
        root.put("option", this.option);
        return root;
    }
}
