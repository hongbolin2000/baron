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

import com.hongyou.baron.ag01.faces.columns.*;
import com.hongyou.baron.util.XmlUtil;
import lombok.Getter;
import org.w3c.dom.Element;

import java.util.HashMap;

/**
 * 表格列注册工厂
 *
 * @author Hong Bo Lin
 */
public class ColumnFactories {

    /**
     * 实例
     */
    @Getter
    private static final ColumnFactories instance = new ColumnFactories();

    /**
     * 所有注册的表格列工厂
     */
    private final HashMap<String, ColumnFactory> factories = new HashMap<>();

    /**
     * 表格列工厂
     */
    private ColumnFactories() {
        this.registry(new LabelColumnFactory());
        this.registry(new LinkColumnFactory());
        this.registry(new CheckColumnFactory());
        this.registry(new ScriptLinkColumnFactory());
        this.registry(new TagColumnFactory());
    }

    /**
     * 注册表格列工厂
     *
     * @param factory 表格列生产工厂
     */
    private void registry(final ColumnFactory factory) {
        this.factories.put(factory.getType(), factory);
    }

    /**
     * 获取表格列工厂
     *
     * @param type 表格列类型
     */
    private ColumnFactory get(final String type) {
        return this.factories.get(type);
    }

    /**
     * 加载表格列定义
     *
     * @param element 表格列定义元素
     */
    public Column create(final Element element) {
        return this.get(XmlUtil.getAttribute(element, "type")).create(element);
    }
}
