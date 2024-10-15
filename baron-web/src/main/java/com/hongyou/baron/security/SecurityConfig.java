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

import com.hongyou.baron.security.filters.AuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security安全认证配置类
 *
 * @author Berlin
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 用户认证实现类
     */
    private final AuthorizeUserLoader userLoader;

    /**
     * 登录认证拦截器
     */
    private final AuthenticationFilter tokenInterceptor;

    /**
     * 构造函数
     */
    public SecurityConfig(final AuthorizeUserLoader userLoader, final AuthenticationFilter tokenInterceptor) {
        this.userLoader = userLoader;
        this.tokenInterceptor = tokenInterceptor;
    }

    /**
     * 密码加密器
     * <p>采用随机盐的方式加密，即使知道密码值也无法破解出原始密码</p>
     * <p>BCryptPasswordEncoder加密器是一种单向加密算法，加密过程是不可逆的</p>
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置认证过滤器，放行不需要认证鉴权的接口
     *
     * @param http http安全请求
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        // 禁用CSRF
        http.csrf(AbstractHttpConfigurer::disable);

        // 不通过session获取SecurityContext
        http.sessionManagement(m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 放行不需要认证的接口
        http.authorizeHttpRequests(a-> a.requestMatchers("/login").
                anonymous().
                anyRequest().
                authenticated()
        );

        // 配置认证过滤器，指定 自定义过滤器 以及 添加在哪个过滤器之前
        http.addFilterBefore(this.tokenInterceptor, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(e -> e.accessDeniedHandler((request, response, accessDeniedException) -> {
            // 访问没有权限的资源时触发
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }).authenticationEntryPoint((request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }));
        return http.build();
    }

    /**
     * 配置认证管理器，供登陆接口使用
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
        dap.setUserDetailsService(this.userLoader);
        dap.setPasswordEncoder(this.passwordEncoder());
        return new ProviderManager(dap);
    }
}
