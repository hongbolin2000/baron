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

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.expression.ExpressionUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.ag01.Scheme;
import com.hongyou.baron.ag01.Statement;
import com.hongyou.baron.util.XmlUtil;
import lombok.Getter;
import org.w3c.dom.Element;

/**
 * 控件定义，组件的父类，描述共同的特点
 *
 * @author Hong Bo Lin
 */
public abstract class AbstractComponent implements Scheme {

    /**
     * 控件类型
     */
    private final String type;

    /**
     * 控件名称
     */
    @Getter
    private final String name;

    /**
     * 控件是否隐藏
     */
    private final String hiddenExpr;

    /**
     * 是否隐藏控件
     */
    @Getter
    private boolean hidden = false;

    /**
     * 加载定义
     *
     * @param element 控件元素定义
     */
    protected AbstractComponent(final Element element) {
        this.type = XmlUtil.getAttribute(element, "type");
        this.name = XmlUtil.getAttribute(element, "name");
        this.hiddenExpr = XmlUtil.getAttribute(element, "hidden");
    }

    /**
     * 生成控件定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        if (StrUtil.isNotBlank(this.hiddenExpr)) {
            // 匹配查询语句
            this.matchStatement(env);

            if ((boolean) ExpressionUtil.eval(this.hiddenExpr, env.getVariables())) {
                this.hidden = true;
            }
        }

        ObjectNode root = env.createObjectNode();
        root.put("type", type);
        root.put("name", name);
        root.put("hidden", hidden);
        return root;
    }

    /**
     * 匹配hidden表达式查询语句
     *
     * @param env 运行参数
     */
    private void matchStatement(final Environment env) {
        String name = this.hiddenExpr.split("\\s")[0];
        Statement statement = env.getSupportStatement(name);
        if (statement != null) {
            env.getVariables().put(name, env.getSupportStatement(name).getData(env));
        }
    }
}
