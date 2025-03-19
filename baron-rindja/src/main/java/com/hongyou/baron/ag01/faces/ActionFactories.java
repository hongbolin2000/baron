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

import com.hongyou.baron.ag01.faces.actions.CheckLinkActionFactory;
import com.hongyou.baron.ag01.faces.actions.LinkActionFactory;
import com.hongyou.baron.ag01.faces.actions.ScriptLinkActionFactory;
import com.hongyou.baron.ag01.faces.actions.UploadActionFactory;
import com.hongyou.baron.util.XmlUtil;
import lombok.Getter;
import org.w3c.dom.Element;

import java.util.HashMap;

/**
 * 动作按钮注册工厂
 *
 * @author Hong Bo Lin
 */
public class ActionFactories {

    /**
     * 实例
     */
    @Getter
    private static final ActionFactories instance = new ActionFactories();

    /**
     * 所有注册的动作按钮工厂
     */
    private final HashMap<String, ActionFactory> factories = new HashMap<>();

    /**
     * 动作按钮工厂
     */
    private ActionFactories() {
        this.registry(new LinkActionFactory());
        this.registry(new ScriptLinkActionFactory());
        this.registry(new CheckLinkActionFactory());
        this.registry(new UploadActionFactory());
    }

    /**
     * 注册动作按钮工厂
     *
     * @param factory 动作按钮生产工厂
     */
    private void registry(final ActionFactory factory) {
        this.factories.put(factory.getType(), factory);
    }

    /**
     * 获取动作按钮工厂
     *
     * @param type 动作按钮类型
     */
    private ActionFactory get(final String type) {
        return this.factories.get(type);
    }

    /**
     * 加载动作按钮定义
     *
     * @param element 动作按钮定义元素
     */
    public Action create(final Element element) {
        return this.get(XmlUtil.getAttribute(element, "type")).create(element);
    }
}
