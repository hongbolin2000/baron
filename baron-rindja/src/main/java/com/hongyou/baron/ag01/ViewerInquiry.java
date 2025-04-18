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
 * 通用浏览表单界面
 *
 * @author Berlin
 */
@RestController
@RequestMapping("/ag01/viewer")
public class ViewerInquiry extends AbstractInquiry {

    /**
     * logger
     */
    private static final Log logger = LogFactory.getLog(ViewerInquiry.class);

    /**
     * 传入参数
     */
    @Data
    public static class ViewerParam {

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
     * 加载浏览表单界面定义
     *
     * @param param 前端传入参数
     */
    @PostMapping("/loadDefine")
    public JsonNode loadDefine(@RequestBody final ViewerParam param) {

        try {
            Environment env = this.createEnvironment(param.getLocal(), param.getParams());
            this.loadUserPermission(env, param.getModule());
            Viewer viewer = this.getViewer(env, param.getModule(), param.getName());

            return viewer.generate(env);
        } catch (Exception e) {
            logger.error("加载浏览表单界面定义失败", e);
            throw new RindjaException("加载浏览表单界面定义失败", e);
        }
    }

    /**
     * 加载浏览表单数据
     *
     * @param param 前端传入参数
     */
    @PostMapping("/loadData")
    public JsonNode loadData(@RequestBody final ViewerParam param) {

        try {
            Environment env = this.createEnvironment(param.getLocal(), param.getParams());
            Viewer viewer = this.getViewer(env, param.getModule(), param.getName());

            return viewer.getData(env, param.getSorter());
        } catch (Exception e) {
            logger.error("加载浏览表单数据失败", e);
            throw new RindjaException("加载浏览表单数据失败", e);
        }
    }

    /**
     * 获取通用浏览表单界面定义
     *
     * @param module 模块名
     * @param name 界面名
     */
    private Viewer getViewer(final Environment env, final String module, final String name) {
        Descriptor descriptor = this.getDescriptor(env, module);
        Viewer viewer = descriptor != null ? descriptor.getViewer(name) : null;
        if (viewer == null) {
            throw new RindjaException("未加载到模块[{}]定义的浏览表单[{}]", module, name);
        }
        return viewer;
    }
}
