///*
// * Copyright 2024, Hongyou Software Development Studio.
// */
//package com.hongyou.baron.security;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.stereotype.Service;
//
///**
// * 登录认证
// *
// * @author Berlin
// */
//@Service
//public class AuthenticatorManager implements Authenticator {
//
//    /**
//     * 认证管理器
//     */
//    private final AuthenticationManager authentication;
//
//    /**
//     * Token生成器
//     */
//    private final SecretTokenManager tokenManager;
//
//    /**
//     * HTTP请求
//     */
//    private final HttpServletRequest request;
//
//    /**
//     * HTTP响应
//     */
//    private final HttpServletResponse response;
//
//    /**
//     * 构造函数
//     */
//    public AuthenticatorManager(
//            final AuthenticationManager authentication, final SecretTokenManager tokenManager,
//            final HttpServletResponse response, final HttpServletRequest request
//    ) {
//        this.authentication = authentication;
//        this.tokenManager = tokenManager;
//        this.response = response;
//        this.request = request;
//    }
//
//    /**
//     * 登录认证
//     *
//     * @param username 用户名
//     * @param password 密码（明文）
//     */
//    public boolean login(final String username, final String password) {
//
//        // 调用Spring Security的认证
//        Authentication authenticate;
//        try {
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                    username, password
//            );
//            authenticate = this.authentication.authenticate(authenticationToken);
//        } catch (AuthenticationException e) {
//            return false;
//        }
//
//        if (authenticate == null || !authenticate.isAuthenticated()) {
//            return false;
//        }
//        AuthorizeUserDetail principal = (AuthorizeUserDetail) authenticate.getPrincipal();
//
//        // 认证通过，根据用户名生成Token
//        String token = this.tokenManager.generate(principal.user());
//        this.tokenManager.build(token, this.response);
//        return true;
//    }
//
//    /**
//     * 退出登录
//     */
//    @Override
//    public boolean logout() {
//        Authentication holder = SecurityContextHolder.getContext().getAuthentication();
//        if (holder != null) {
//            new SecurityContextLogoutHandler().logout(this.request, this.response, holder);
//            return true;
//        }
//        return false;
//    }
//}
