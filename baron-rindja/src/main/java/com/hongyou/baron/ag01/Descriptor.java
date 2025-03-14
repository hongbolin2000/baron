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

import cn.hutool.core.collection.CollUtil;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;

/**
 * 通用界面注册描述文件
 *
 * @author Hong Bo Lin
 */
public class Descriptor extends AbstractDescriptor {

    /**
     * 通用浏览界面的配置节点名称
     */
    private static final String GRIDER = "generic-grider-descriptor";

    /**
     * 通用编辑界面的配置节点名称
     */
    private static final String EDITOR = "generic-editor-descriptor";

    /**
     * 缓存定义的浏览界面
     */
    private final HashMap<String, Grider> griders = new HashMap<>();

    /**
     * 缓存定义的编辑界面
     */
    private final HashMap<String, Editor> editors = new HashMap<>();

    /**
     * 解析模块界面定义文件
     *
     * @param basePath 项目资源路径
     * @param module 模块号
     */
    protected Descriptor(final String basePath, final String module) {
        super(basePath, module);

        Element setting = this.loadDescriptor();
        if (setting == null) {
            return;
        }
        this.loadGriders(setting);
        this.loadEditors(setting);
    }

    /**
     * 加载浏览界面定义
     *
     * @param setting 注册文件配置节点
     */
    private void loadGriders(final Element setting) {

        // 解析通用表格界面定义节点
        List<Element> descriptors = XmlUtil.getChildElements(setting, GRIDER);
        if (CollUtil.isEmpty(descriptors)) {
            return;
        }

        for (Element descriptor : descriptors) {
            String name = XmlUtil.getAttribute(descriptor, "name");
            Element root = this.getRootElement(XmlUtil.getTextContent(descriptor));

            Grider grider = new Grider(root);
            this.griders.put(name, grider);
        }
    }

    /**
     * 加载编辑界面定义
     *
     * @param setting 注册文件配置节点
     */
    private void loadEditors(final Element setting) {

        // 解析通用表格界面定义节点
        List<Element> descriptors = XmlUtil.getChildElements(setting, EDITOR);
        if (CollUtil.isEmpty(descriptors)) {
            return;
        }

        for (Element descriptor : descriptors) {
            String name = XmlUtil.getAttribute(descriptor, "name");
            Element root = this.getRootElement(XmlUtil.getTextContent(descriptor));

            Editor editor = new Editor(root);
            this.editors.put(name, editor);
        }
    }

    /**
     * 加载浏览界面定义
     *
     * @param name 浏览界面名称
     */
    protected Grider getGrider(final String name) {
        return this.griders.get(name);
    }

    /**
     * 加载编辑界面定义
     *
     * @param name 浏览界面名称
     */
    protected Editor getEditor(final String name) {
        return this.editors.get(name);
    }
}
