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

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Hong Bo Lin
 */
@Component
public class ProjectProperties {

    /**
     * 项目资源路径
     */
    @Value("${baron.resources.package}")
    private String basePath;

    /**
     * 是否开发模式
     */
    @Getter
    @Value("${baron.debug}")
    private boolean debug;

    /**
     * 项目资源路径
     */
    public String getBasePath() {
        if (basePath.contains(".")) {
            basePath = basePath.replace(".", "/");
            if (!basePath.endsWith("/")) {
                basePath = basePath + "/";
            }
        }
        return basePath;
    }
}
