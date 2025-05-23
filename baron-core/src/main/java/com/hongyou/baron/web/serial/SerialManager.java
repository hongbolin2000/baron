/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.web.serial;

import java.util.List;

/**
 * 序列号生成
 *
 * @author Berlin
 */
public interface SerialManager {

    /**
     * 生成单个序列号
     *
     * @param serialType 生成类型
     * @param applicationKey 应用键值
     */
    String get(final String serialType, final String applicationKey);

    /**
     * 批量生成序列号
     *
     * @param serialType 生成类型
     * @param applicationKey 应用键值
     */
    List<String> get(final String serialType, final String applicationKey, final int count);
}
