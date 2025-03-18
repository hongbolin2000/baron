/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.cache;

/**
 * 超时缓存
 *
 * @author Hong Bo Lin
 */
public class FIFOCache<T, V> extends cn.hutool.cache.impl.FIFOCache<T, V> {

    /**
     * constructor
     */
    public FIFOCache(final int capacity) {
        super(capacity);
    }
}
