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
package com.hongyou.baron.model;

import com.hongyou.baron.GenerationException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库键索引对象（唯一键、索引、外键等）
 *
 * @author Berlin
 */
@Data
public class AbstractKey {

    /**
     * 索引编号
     */
    protected String name;

    /**
     * 索引方法名
     */
    protected String method;

    /**
     * 索引字段
     */
    protected List<Column> columns = new ArrayList<>();

    /**
     * 检查是否重复的字段
     *
     * @param column 字段名
     * @throws GenerationException 检查时抛出的异常
     */
    protected void duplicated(final String column) throws GenerationException {
        if (this.columns.stream().anyMatch(i -> column.trim().equals(i.getName()))) {
            throw new GenerationException("重复的字段: {0}", name);
        }
    }
}
