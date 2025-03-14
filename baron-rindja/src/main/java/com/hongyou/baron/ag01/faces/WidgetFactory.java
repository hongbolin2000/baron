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

import org.w3c.dom.Element;

/**
 * 输入控件工厂定义
 *
 * @author Hong Bo Lin
 */
public interface WidgetFactory {

    /**
     * 输入控件类型
     */
    String getType();

    /**
     * 加载输入控件定义
     *
     * @param element 输入控件定义元素
     */
    Widget create(Element element);
}