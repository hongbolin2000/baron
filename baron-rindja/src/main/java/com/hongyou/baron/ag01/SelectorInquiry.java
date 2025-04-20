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
import com.hongyou.baron.RindjaException;
import com.hongyou.baron.logging.Log;
import com.hongyou.baron.logging.LogFactory;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用查询选择器界面
 *
 * @author Berlin
 */
@RestController
@RequestMapping("/ag01/selector")
public class SelectorInquiry extends AbstractInquiry {

    /**
     * logger
     */
    private static final Log logger = LogFactory.getLog(SelectorInquiry.class);

    /**
     * 传入参数
     */
    @Data
    public static class SelectorParam {

        /**
         * 功能模块号
         */
        private String module;

        /**
         * 界面名称
         */
        private String name;

        /**
         * 本地语言
         */
        private String local;

        /**
         * 查询参数
         */
        private JsonNode params;

        /**
         * 字段排序
         */
        private Sorter sorter;
    }

    /**
     * 加载查询选择器界面定义
     *
     * @param param 前端传入参数
     */
    @PostMapping("/loadDefine")
    public JsonNode loadDefine(@RequestBody final SelectorParam param) {

        try {
            Environment env = this.createEnvironment(param.getLocal(), param.getParams());
            Selector selector = this.getSelector(env, param.getModule(), param.getName());

            return selector.generate(env);
        } catch (Exception e) {
            logger.error("加载查询选择器界面定义失败", e);
            throw new RindjaException("加载查询选择器界面定义失败", e);
        }
    }

    /**
     * 查询选择器数据
     *
     * @param param 前端传入参数
     */
    @PostMapping("/loadData")
    public JsonNode loadData(@RequestBody final SelectorParam param) {

        try {
            Environment env = this.createEnvironment(param.getLocal(), param.getParams());
            Selector selector = this.getSelector(env, param.getModule(), param.getName());

            return selector.getData(env, param.getSorter());
        } catch (Exception e) {
            logger.error("加载查询选择器界面数据失败", e);
            throw new RindjaException("加载查询选择器界面数据失败", e);
        }
    }

    /**
     * 获取通用查询选择器界面定义
     *
     * @param module 模块名
     * @param name 界面名
     */
    private Selector getSelector(final Environment env, final String module, final String name) {
        Descriptor descriptor = this.getDescriptor(env, module);
        Selector selector = descriptor != null ? descriptor.getSelector(name) : null;
        if (selector == null) {
            throw new RindjaException("未加载到模块[{}]定义的查询选择器[{}]", module, name);
        }
        return selector;
    }
}
