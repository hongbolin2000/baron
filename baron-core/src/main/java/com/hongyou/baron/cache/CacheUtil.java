/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.cache;

/**
 * 緩存工具类
 *
 * @author Berlin
 */
public class CacheUtil extends cn.hutool.cache.CacheUtil {

    /**
     * 超时缓存
     *
     * @param timeout 超时失效
     * @param <K> key
     * @param <V> value
     */
    public static <K, V> TimedCache<K, V> newTimedCache(final long timeout) {
        return new TimedCache<>(timeout);
    }

    /**
     * 超时缓存
     *
     * @param capacity 缓存个数
     * @param <K> key
     * @param <V> value
     */
    public static <K, V> FIFOCache<K, V> newFIFOCache(final int capacity) {
        return new FIFOCache<>(capacity);
    }
}
