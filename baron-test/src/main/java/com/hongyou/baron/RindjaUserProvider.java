/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提供给Rindja模块用户信息
 *
 * @author Berlin
 */
@Component
public class RindjaUserProvider implements RindjaUserLoader {

    /**
     * 加载用户信息
     */
    @Override
    public void loadUserVariables(final Map<String, Object> variables) {
        variables.put("_companyId", 1L);
    }

    /**
     * 加载用户权限
     *
     * @param module 模块号
     */
    @Override
    public List<String> loadUserPermissions(String module) {
        return new ArrayList<>();
    }
}
