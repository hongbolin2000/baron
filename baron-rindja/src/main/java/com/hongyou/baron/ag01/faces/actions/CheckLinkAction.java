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
package com.hongyou.baron.ag01.faces.actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.ag01.faces.AbstractAction;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

/**
 * 选择项动作按钮
 *
 * @author Berlin
 */
public class CheckLinkAction extends AbstractAction {

    /**
     * 选择项执行模式(script，remote)
     */
    private final String mode;

    /**
     * 执行链接(mode为remote则是服务器地址，为Script则是JavaScript函数)
     */
    private final String link;

    /**
     * 确认弹框提示表格字段内容
     */
    private final String labelColumn;

    /**
     * 加载选择项动作按钮定义
     *
     * @param element 动作按钮元素定义
     */
    protected CheckLinkAction(final Element element) {
        super(element);
        this.link = XmlUtil.getAttribute(element, "link");
        this.mode = XmlUtil.getAttribute(element, "mode", "remote");
        this.labelColumn = XmlUtil.getAttribute(element, "labelColumn");
    }

    /**
     * 生成选择项动作按钮定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        root.put("link", this.link);
        root.put("mode", this.mode);
        root.put("labelColumn", this.labelColumn);
        return root;
    }
}
