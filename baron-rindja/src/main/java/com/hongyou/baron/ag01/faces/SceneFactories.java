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

import com.hongyou.baron.ag01.faces.scenes.LabelSceneFactory;
import com.hongyou.baron.util.XmlUtil;
import lombok.Getter;
import org.w3c.dom.Element;

import java.util.HashMap;

/**
 * 浏览表单控件注册工厂
 *
 * @author Berlin
 */
public class SceneFactories {

    /**
     * 实例
     */
    @Getter
    private static final SceneFactories instance = new SceneFactories();

    /**
     * 所有注册的控件工厂
     */
    private final HashMap<String, SceneFactory> factories = new HashMap<>();

    /**
     * 输入控件工厂
     */
    private SceneFactories() {
        this.registry(new LabelSceneFactory());
    }

    /**
     * 注册控件工厂
     *
     * @param factory 控件工厂
     */
    private void registry(final SceneFactory factory) {
        this.factories.put(factory.getType(), factory);
    }

    /**
     * 获取控件工厂
     *
     * @param type 输入控件类型
     */
    private SceneFactory get(final String type) {
        return this.factories.get(type);
    }

    /**
     * 加载控件定义
     *
     * @param element 控件定义元素
     */
    public Scene create(final Element element) {
        return this.get(XmlUtil.getAttribute(element, "type")).create(element);
    }
}
