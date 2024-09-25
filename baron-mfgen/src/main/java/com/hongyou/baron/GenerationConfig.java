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
package com.hongyou.baron;

import lombok.Builder;
import lombok.Data;

/**
 * 代码生成配置
 *
 * @author Berlin
 */
@Data
@Builder
public class GenerationConfig {

    /**
     * 项目根路径
     */
    private String baseDirectory;

    /**
     * 生成的数据库类型
     */
    private DatabaseType type;

    /**
     * class文件输出包
     */
    private String classPackage;
}
