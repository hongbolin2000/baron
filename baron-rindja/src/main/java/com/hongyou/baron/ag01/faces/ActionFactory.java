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
 * 动作按钮工厂定义
 *
 * @author Hong Bo Lin
 */
public interface ActionFactory {

    /**
     * 动作按钮类型
     */
    String getType();

    /**
     * 加载动作按钮定义
     *
     * @param element 动作按钮定义元素
     */
    Action create(Element element);
}