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

import lombok.Builder;
import lombok.Data;

/**
 * 字段枚举值
 *
 * @author Berlin
 */
@Data
@Builder
public class Enumeration {

    /**
     * 枚举值
     */
    private String code;

    /**
     * 枚举变量
     */
    private String field;

    /**
     * 英文标签
     */
    private String eLabel;

    /**
     * 中文标签
     */
    private String cLabel;
}
