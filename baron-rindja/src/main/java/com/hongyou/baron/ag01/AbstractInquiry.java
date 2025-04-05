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
import com.hongyou.baron.Application;
import com.hongyou.baron.RindjaUserLoader;
import com.hongyou.baron.cache.CacheUtil;
import com.hongyou.baron.cache.TimedCache;
import lombok.Getter;

import java.util.List;

/**
 * 通用界面
 *
 * @author Berlin
 */
public abstract class AbstractInquiry {

    /**
     * 缓存每个模块定义的界面(7天未使用自动清除, key: 模块编号, value: 模块所有的通用界面定义)
     */
    private final TimedCache<Object, Descriptor> descriptorCaches = CacheUtil.newTimedCache(1000 * 60 * 60 * 24 * 7);

    /**
     * 项目配置参数
     */
    private final Application application;

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
     * @param application 应用配置
     * @param userLoader 用户加载器
     */
    public AbstractInquiry(final Application application, final RindjaUserLoader userLoader) {
        this.application = application;
        this.userLoader = userLoader;
    }

    /**
     * 创建运行参数对象
     *
     * @param local 多语言
     * @param params 前端传入的执行参数
     */
    protected void createEnvironment(final String local, final JsonNode params) {
        this.environment = new Environment(this.application);
        this.environment.setLocal(local);

        // 加入用户变量、全局查询参数
        this.userLoader.loadUserVariables(this.environment.getVariables());
        this.environment.getVariables().addSimpleJson(params);
    }

    /**
     * 加载用户权限
     *
     * @param module 模块号
     */
    protected void loadUserPermission(final String module) {
        List<String> permissions = this.userLoader.loadUserPermissions(module);
        this.environment.setPermissions(permissions);
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