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
 * Rindja模块用户认证信息
 *
 * @author Hong Bo Lin
 */
@Data
@Builder
public class RindjaUserDetail {

    /**
     * 所属公司ID
     */
    private long companyId;

    /**
     * 用户名
     */
    private String username;
}
