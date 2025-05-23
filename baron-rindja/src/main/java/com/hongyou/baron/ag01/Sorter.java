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
package com.hongyou.baron.ag01;

import lombok.Data;

/**
 * 字段排序
 *
 * @author Berlin
 */
@Data
public class Sorter {

    /**
     * 字段名(前端传入)
     */
    private String name;

    /**
     * 排序方式
     */
    private Order order;

    /**
     * 排序方式
     */
    public enum Order {

        /**
         * 正序
         */
        asc,

        /**
         * 倒序
         */
        desc;
    }
}
