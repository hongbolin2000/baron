///*
// * Copyright 2024, Hongyou Software Development Studio.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.hongyou.baron.security;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
///**
// * 提供给Spring Security调用，通过用户名来获取到用户的认证信息
// *
// * @author Berlin
// */
//@Service
//public class AuthorizeUserLoader implements UserDetailsService {
//
//    /**
//     * 用户信息加载器
//     */
//    private final UserLoader userLoader;
//
//    /**
//     * 构造函数
//     */
//    public AuthorizeUserLoader(final UserLoader userLoader) {
//        this.userLoader = userLoader;
//    }
//
//    /**
//     * 通过用户名加载用户认证信息
//     *
//     * @param username 用户名
//     * @return 用户认证信息
//     * @throws UsernameNotFoundException 找不到用户名抛出的异常
//     */
//    @Override
//    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
//        AuthorizeUser user = this.userLoader.loadByUserName(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User " + username + " not found");
//        }
//        return new AuthorizeUserDetail(user);
//    }
//}
