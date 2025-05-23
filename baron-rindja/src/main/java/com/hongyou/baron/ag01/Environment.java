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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.Application;
import com.hongyou.baron.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 通用界面运行参数
 *
 * @author Berlin
 */
public class Environment {

    /**
     * 项目资源路径
     */
    @Getter
    private final String basePath;

    /**
     * 是否开发模式
     */
    @Getter
    private final boolean isDebug;

    /**
     * 国际化语言
     */
    @Setter
    @Getter
    public International international;

    /**
     * 通用界面中的全局变量
     */
    @Getter
    public Variables variables = new Variables();

    /**
     * 语言（缺省zh-CN）
     */
    @Setter
    @Getter
    public String local = "zh-CN";

    /**
     * 全局查询语句
     */
    @Setter
    public HashMap<String, Statement> supportStatements = new HashMap<>();

    /**
     * 用户权限
     */
    @Setter
    public List<String> permissions = new ArrayList<>();

    /**
     * @param application 应用配置
     */
    protected Environment(final Application application) {
        this.basePath = application.getBasePath();
        this.isDebug = application.isDebug();
    }

    /**
     * 检查是否有权限
     */
    public boolean hasPermission(final String action) {
        return this.permissions.contains(action);
    }

    /**
     * 获取国际化语言资源
     *
     * @param key 定义的国际化语言可以
     */
    public String getLocalResource(final String key) {
        return this.international.get(this.local, key);
    }

    /**
     * 获取全局查询语句
     */
    public Statement getSupportStatement(final String name) {
        return this.supportStatements.get(name);
    }

    /**
     * 创建json对象
     */
    public ObjectNode createObjectNode() {
        return JsonUtil.createObjectNode();
    }

    /**
     * 创建json数组对象
     */
    public ArrayNode createArrayNode() {
        return JsonUtil.createArrayNode();
    }

    /**
     * 转换JSON对象
     *
     * @param value 转换的来源数据
     */
    public JsonNode convertValue(final Object value) {
        return JsonUtil.convertValue(value);
    }
}
