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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.ag01.Scheme;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 动作按钮栏定义
 *
 * @author Hong Bo Lin
 */
public class ActionBar implements Scheme {

    /**
     * 动作按钮
     */
    List<Action> actions = new ArrayList<>();

    /**
     * 加载动作栏定义
     *
     * @param element 动作栏定义元素
     */
    public ActionBar(final Element element) {
        List<Element> actionNodes = XmlUtil.getChildElements(element, "action");
        actionNodes.forEach(actionNode -> this.actions.add(ActionFactories.getInstance().create(actionNode)));
    }

    /**
     * 生成动作按钮栏界面定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = env.createObjectNode();
        ArrayNode actionsNode = env.createArrayNode();

        this.actions.forEach(action -> {
            JsonNode generated = action.generate(env);
            if (!action.isHidden()) {
                actionsNode.add(generated);
            }
        });
        root.set("actions", actionsNode);
        return root;
    }
}
