///*
// * Copyright 2024, Hongyou Software Development Studio.
// */
//package com.hongyou.baron.security;
//
//import com.hongyou.baron.util.DateUtil;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.io.Serial;
//import java.util.Collection;
//import java.util.List;
//
///**
// * 提供给Spring Security的认证用户信息
// *
// * @param user 登录用户
// *             -- GETTER --
// *             获取认证用户信息
// * @author Berlin
// */
//public record AuthorizeUserDetail(AuthorizeUser user) implements UserDetails {
//
//    /**
//     * serialVersionUID
//     */
//    @Serial
//    private static final long serialVersionUID = -7945483905707090856L;
//
//    /**
//     * <p>返回授予用户的权限集合</p>
//     * <p>这些权限通常以角色和具体权限的形式存在，用于控制用户对系统资源的访问</p>
//     */
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//
//    /**
//     * 返回用户名
//     */
//    @Override
//    public String getUsername() {
//        return this.user.getUsername();
//    }
//
//    /**
//     * 返回用户的密码，确保安全性密码必须是加密的
//     */
//    @Override
//    public String getPassword() {
//        return this.user.getPassword();
//    }
//
//    /**
//     * 判断用户账号是否过期，未过期返回true
//     */
//    @Override
//    public boolean isAccountNonExpired() {
//        if (this.user.getAccountExpireTime() == null) {
//            return true;
//        }
//        return this.user.getAccountExpireTime().isBefore(DateUtil.getDateTime());
//    }
//
//    /**
//     * 判断账号是否被锁定，未锁定返回true
//     */
//    @Override
//    public boolean isAccountNonLocked() {
//        return this.user.isAccountUnlock();
//    }
//
//    /**
//     * 判断用户凭证（密码）是否过期，未过期返回true
//     */
//    @Override
//    public boolean isCredentialsNonExpired() {
//        if (this.user.getCredentialsExpireTime() == null) {
//            return true;
//        }
//        return this.user.getCredentialsExpireTime().isBefore(DateUtil.getDateTime());
//    }
//
//    /**
//     * 判断用户是否启用，启用返回true
//     */
//    @Override
//    public boolean isEnabled() {
//        return this.user.isEnabled();
//    }
//}
