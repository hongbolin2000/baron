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
