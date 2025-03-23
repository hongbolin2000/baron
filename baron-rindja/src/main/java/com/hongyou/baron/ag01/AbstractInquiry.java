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
package com.hongyou.baron.ag01;

import com.fasterxml.jackson.databind.JsonNode;
import com.hongyou.baron.ProjectProperties;
import com.hongyou.baron.RindjaUserDetail;
import com.hongyou.baron.RindjaUserLoader;
import com.hongyou.baron.cache.CacheUtil;
import com.hongyou.baron.cache.TimedCache;
import lombok.Getter;

/**
 * 通用界面
 *
 * @author Hong Bo Lin
 */
public abstract class AbstractInquiry {

    /**
     * 缓存每个模块定义的界面(7天未使用自动清除)
     */
    private final TimedCache<Object, Descriptor> descriptorCaches = CacheUtil.newTimedCache(1000 * 60 * 60 * 24 * 7);

    /**
     * 项目配置参数
     */
    private final ProjectProperties properties;

    /**
     * 用户加载器
     */
    private final RindjaUserLoader userLoader;

    /**
     * 运行参数
     */
    @Getter
    private Environment environment;

    /**
     * @param properties 项目配置参数
     * @param userLoader 用户加载器
     */
    public AbstractInquiry(final ProjectProperties properties, final RindjaUserLoader userLoader) {
        this.properties = properties;
        this.userLoader = userLoader;
    }

    /**
     * 创建运行参数对象
     *
     * @param local 多语言
     * @param params 前端传入的执行参数
     */
    protected void createEnvironment(final String local, final JsonNode params) {

        // 加载登录用户
        RindjaUserDetail userDetail = this.userLoader.load();

        this.environment = new Environment(this.properties, userDetail);
        this.environment.setLocal(local);

        // 将查询参数加入全局变量
        this.environment.getVariables().addSimpleJson(params);
    }

    /**
     * 加载界面定义
     *
     * @param module 模块号
     */
    protected Descriptor getDescriptor(final String module) {
        Descriptor descriptor;
        if (!this.getEnvironment().isDebug()) {
            if (this.descriptorCaches.containsKey(module)) {
                return this.descriptorCaches.get(module);
            }
        }
        descriptor = new Descriptor(this.getEnvironment().getBasePath(), module);
        this.descriptorCaches.put(module, descriptor);
        return descriptor;
    }
}