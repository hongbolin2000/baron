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
package com.hongyou.baron.web;

import lombok.Builder;
import lombok.Data;

/**
 * Rest（HTTP）请求返回结果
 *
 * @author Berlin
 */
@Data
@Builder
public class ResponseEntry {

    /**
     * 成功/错误编号
     */
    @Builder.Default
    private int code = SUCCESS_CODE;

    /**
     * 成功编号
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 函数执行时捕捉到异常出错
     */
    public static final int UNKNOWN_CODE = Integer.MAX_VALUE;

    /**
     * 成功/错误消息
     */
    @Builder.Default
    private String message = "Success";

    /**
     * 返回数据内容
     */
    private Object body;

    /**
     * 成功
     */
    public static final ResponseEntry SUCCESS = ResponseEntry.builder().build();
}
