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
package com.hongyou.baron.exceptions;

import com.hongyou.baron.exceptions.basic.BaseRuntimeException;

/**
 * 用户未认证异常类
 * 
 * @author Hong Bo Lin
 */
public class NotAuthorizedException extends BaseRuntimeException {

    /**
     * 捕获文本异常
     *
     * @param pattern 异常消息
     * @param args 异常消息参数
     */
    public NotAuthorizedException(String pattern, Object... args) {
        super(pattern, args);
    }
}
