/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.cache;

/**
 * 超时缓存
 *
 * @author Berlin
 */
public class TimedCache<T, V> extends cn.hutool.cache.impl.TimedCache<T, V> {

    /**
     * constructor
     */
    public TimedCache(final long timeout) {
        super(timeout);
    }
}
