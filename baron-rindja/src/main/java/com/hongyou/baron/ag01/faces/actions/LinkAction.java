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
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;
import org.w3c.dom.Element;

/**
 * 路由动作按钮
 *
 * @author Hong Bo Lin
 */
public class LinkAction extends AbstractAction {

    /**
     * 路由地址
     */
    private final CompiledTemplate link;

    /**
     * 按钮执行模式（router，dialog，drawer）
     */
    private final String mode;

    /**
     * dialog弹框宽度
     */
    private final String dialogWidth;

    /**
     * drawer宽度
     */
    private final String drawerWidth;

    /**
     * 加载路由动作按钮定义
     *
     * @param element 动作按钮元素定义
     */
    protected LinkAction(final Element element) {
        super(element);

        // 动态表达式
        String link = XmlUtil.getAttribute(element, "link", "");
        this.link = TemplateCompiler.compileTemplate(link);
        this.mode = XmlUtil.getAttribute(element, "mode", "router");
        this.dialogWidth = XmlUtil.getAttribute(element, "dialogWidth", "60%");
        this.drawerWidth = XmlUtil.getAttribute(element, "drawerWidth", "400");
    }

    /**
     * 生成路由动作按钮定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        Object result = TemplateRuntime.execute(this.link, env.getVariables());
        root.set("link", env.convertValue(result));
        root.put("mode", this.mode);
        root.put("dialogWidth", this.dialogWidth);
        root.put("drawerWidth", this.drawerWidth);
        return root;
    }
}
