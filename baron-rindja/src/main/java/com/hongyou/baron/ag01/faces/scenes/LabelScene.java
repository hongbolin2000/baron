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
package com.hongyou.baron.ag01.faces.scenes;

import com.hongyou.baron.ag01.faces.AbstractScene;
import org.w3c.dom.Element;

/**
 * 标签显示控件
 *
 * @author Berlin
 */
public class LabelScene extends AbstractScene {

    /**
     * 加载表格标签列定义
     *
     * @param element 表格标签列元素定义
     */
    protected LabelScene(final Element element) {
        super(element);
    }
}
