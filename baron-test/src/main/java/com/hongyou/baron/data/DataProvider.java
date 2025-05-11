/*
 * Copyright 2024, Hongyou Software Development Studio.
 */
package com.hongyou.baron.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 注入数据库组件，只需要集成此组件即可操作数据库对象
 *
 * @author Berlin
 */
@Component
public class DataProvider {

    /**
     * 数据库组件
     */
    private DB db;

    /**
     * 注入数据库组件
     */
    @Autowired
    public void setDB(final DB db) {
        this.db = db;
    }

    /**
     * 获取数据库组件
     */
    public DB db() {
        return db;
    }
}
