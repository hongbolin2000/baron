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
import com.hongyou.baron.ProjectProperties;
import com.hongyou.baron.RindjaUserDetail;
import com.hongyou.baron.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * 通用界面运行参数
 *
 * @author Hong Bo Lin
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
    public String local = "zh-CN";

    /**
     * 全局查询语句
     */
    @Setter
    public HashMap<String, Statement> supportStatements = new HashMap<>();

    /**
     * @param properties 项目配置
     * @param userDetail 登录用户
     */
    protected Environment(
            final ProjectProperties properties, final RindjaUserDetail userDetail
    ) {
        this.basePath = properties.getBasePath();
        this.isDebug = properties.isDebug();
        this.addUser(userDetail);
    }

    /**
     * 加入用户信息到全局变量
     */
    private void addUser(final RindjaUserDetail userDetail) {
        this.variables.put("_username", userDetail.getUsername());
        this.variables.put("_companyId", userDetail.getCompanyId());
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
     * 获取国际化语言枚举值
     *
     * @param key 定义的国际化语言可以
     */
    public String getLocalResourceValue(final String key, final String value) {
        return this.international.getValue(this.local, key, value);
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
