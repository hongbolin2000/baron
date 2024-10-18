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
//package com.hongyou.baron.security.filters;
//
//import com.hongyou.baron.security.AuthorizeUserDetail;
//import com.hongyou.baron.security.SecretTokenManager;
//import com.hongyou.baron.util.StringUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.NonNull;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
///**
// * 登录认证拦截器
// *
// * @author Berlin
// */
//@Component
//public class AuthenticationFilter extends OncePerRequestFilter {
//
//    /**
//     * Token生成器
//     */
//    private final SecretTokenManager tokenManager;
//
//    /**
//     * 构造函数
//     */
//    public AuthenticationFilter(final SecretTokenManager tokenManager) {
//        this.tokenManager = tokenManager;
//    }
//
//    /**
//     * Token拦截
//     *
//     * @param request http请求
//     * @param response http详情
//     * @param filterChain 过滤器链
//     */
//    @Override
//    protected void doFilterInternal(
//            final @NonNull HttpServletRequest request, final @NonNull HttpServletResponse response,
//            final @NonNull FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        // 未设置Token则直接放行
//        String token = this.tokenManager.get(request);
//        if (StringUtil.isBlank(token)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // 获取Token中存放的用户信息
//        AuthorizeUserDetail user = this.tokenManager.getUser(token);
//        if (user == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // token认证成功
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, null);
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//        filterChain.doFilter(request,response);
//    }
//}
