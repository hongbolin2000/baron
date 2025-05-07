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

import com.hongyou.baron.ag01.Scheme;

/**
 * 表格列
 */
public interface Column extends Scheme {

    /**
     * 获取列定义的name
     */
    String getName();

    /**
     * 获取参数表达式
     */
    String getExpr();

    /**
     * 获取列过滤定义
     */
    String getFilter();

    /**
     * 是否隐藏控件
     */
    boolean isHidden();

    /**
     * 获取列标题
     */
    String getTitle();

    /**
     * 是否可导出
     */
    boolean isExportable();
}
