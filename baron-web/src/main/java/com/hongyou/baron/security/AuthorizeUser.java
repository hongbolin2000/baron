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
package com.hongyou.baron.security;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 认证用户信息
 *
 * @author Berlin
 */
@Data
@Builder
public class AuthorizeUser implements Serializable {

    /**
     * serialVersionUID
     */
    @Serial
    private static final long serialVersionUID = -2229936814509271056L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码，必须是加密的密码
     */
    private String password;

    /**
     * 账号过期时间，设置null为不过期，否则根据当前时间比较是否过期了
     */
    private LocalDateTime accountExpireTime;

    /**
     * 账号是否被锁定，true为未锁定，false为锁定
     */
    private boolean accountUnlock;

    /**
     * 用户凭证过期时间（密码）,设置为null为不过期，否则根据当前时间比较是否过期了
     */
    private LocalDateTime credentialsExpireTime;

    /**
     * 用户是否启用
     */
    private boolean enabled;
}
