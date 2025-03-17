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

import com.hongyou.baron.ag01.faces.widgets.*;
import com.hongyou.baron.util.XmlUtil;
import lombok.Getter;
import org.w3c.dom.Element;

import java.util.HashMap;

/**
 * 输入控件注册工厂
 *
 * @author Hong Bo Lin
 */
public class WidgetFactories {

    /**
     * 实例
     */
    @Getter
    private static final WidgetFactories instance = new WidgetFactories();

    /**
     * 所有注册的输入控件工厂
     */
    private final HashMap<String, WidgetFactory> factories = new HashMap<>();

    /**
     * 输入控件工厂
     */
    private WidgetFactories() {
        this.registry(new TextWidgetFactory());
        this.registry(new NumberWidgetFactory());
        this.registry(new DateWidgetFactory());
        this.registry(new TimeWidgetFactory());
        this.registry(new EnumWidgetFactory());
    }

    /**
     * 注册输入控件工厂
     *
     * @param factory 输入控件生成工厂
     */
    private void registry(final WidgetFactory factory) {
        this.factories.put(factory.getType(), factory);
    }

    /**
     * 获取输入控件工厂
     *
     * @param type 输入控件类型
     */
    private WidgetFactory get(final String type) {
        return this.factories.get(type);
    }

    /**
     * 加载输入控件定义
     *
     * @param element 输入控件定义元素
     */
    public Widget create(final Element element) {
        return this.get(XmlUtil.getAttribute(element, "type")).create(element);
    }
}
