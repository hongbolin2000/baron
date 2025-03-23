/*
 * Copyright 2024, Hongyou Software Development Studio.
 */
package com.hongyou.baron.web.token;

import java.util.List;

/**
 * Token配置加载器
 */
public interface TokenConfigureLoader {

    /**
     * 加载不需要登录认证的路径
     */
    List<String> loadExcludePathPatterns();
}
