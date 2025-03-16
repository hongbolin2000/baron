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
package com.hongyou.baron.ag01.faces.actions;

import com.hongyou.baron.ag01.faces.Action;
import com.hongyou.baron.ag01.faces.ActionFactory;
import org.w3c.dom.Element;

/**
 * 选择项动作按钮工厂
 *
 * @author Hong Bo Lin
 */
public class CheckLinkActionFactory implements ActionFactory {

    /**
     * 动作按钮类型
     */
    private static final String TYPE = "checkLink";

    /**
     * 获取动作按钮类型
     *
     * @return 动作按钮类型
     */
    @Override
    public String getType() {
        return TYPE;
    }

    /**
     * 加载选择项动作按钮定义
     *
     * @param element 动作按钮元素定义
     */
    @Override
    public Action create(final Element element) {
        return new CheckLinkAction(element);
    }
}
