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
package com.hongyou.baron.ag01.faces.columns;

import com.hongyou.baron.ag01.faces.Column;
import com.hongyou.baron.ag01.faces.ColumnFactory;
import org.w3c.dom.Element;

/**
 * 标签列工厂
 *
 * @author Hong Bo Lin
 */
public class LabelColumnFactory implements ColumnFactory {

    /**
     * 标签列类型
     */
    private static final String TYPE = "label";

    /**
     * 获取标签列类型
     *
     * @return 标签列类型
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * 加载标签列定义
     *
     * @param element 标签列元素定义
     */
    @Override
    public Column create(final Element element) {
        return new LabelColumn(element);
    }
}
