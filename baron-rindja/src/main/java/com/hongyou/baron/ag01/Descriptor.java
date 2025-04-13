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

import com.hongyou.baron.util.ListUtil;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;

/**
 * 通用界面注册描述文件
 *
 * @author Berlin
 */
public class Descriptor extends AbstractDescriptor {

    /**
     * 通用浏览表格界面配置节点名称
     */
    private static final String GRIDER = "generic-grider-descriptor";

    /**
     * 通用浏览表单界面配置节点名称
     */
    private static final String VIEWER = "generic-viewer-descriptor";

    /**
     * 通用编辑界面配置节点名称
     */
    private static final String EDITOR = "generic-editor-descriptor";

    /**
     * 通用查询建议器配置节点名称
     */
    private static final String SUGGESTOR = "generic-suggestor-descriptor";

    /**
     * 缓存定义的浏览表格界面
     */
    private final HashMap<String, Grider> griders = new HashMap<>();

    /**
     * 缓存定义的浏览表单界面
     */
    private final HashMap<String, Viewer> viewers = new HashMap<>();

    /**
     * 缓存定义的编辑界面
     */
    private final HashMap<String, Editor> editors = new HashMap<>();

    /**
     * 缓存定义的查询建议器
     */
    private final HashMap<String, Suggestor> suggestors = new HashMap<>();

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
        this.loadViewers(setting);
        this.loadEditors(setting);
        this.loadSuggestors(setting);
    }

    /**
     * 加载浏览表格界面定义
     *
     * @param setting 注册文件配置节点
     */
    private void loadGriders(final Element setting) {

        // 解析通用浏览表格界面定义节点
        List<Element> descriptors = XmlUtil.getChildElements(setting, GRIDER);
        if (ListUtil.isEmpty(descriptors)) {
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
     * 加载浏览表单界面定义
     *
     * @param setting 注册文件配置节点
     */
    private void loadViewers(final Element setting) {

        // 解析通用浏览表单界面定义节点
        List<Element> descriptors = XmlUtil.getChildElements(setting, VIEWER);
        if (ListUtil.isEmpty(descriptors)) {
            return;
        }

        for (Element descriptor : descriptors) {
            String name = XmlUtil.getAttribute(descriptor, "name");
            Element root = this.getRootElement(XmlUtil.getTextContent(descriptor));

            Viewer viewer = new Viewer(root);
            this.viewers.put(name, viewer);
        }
    }

    /**
     * 加载编辑界面定义
     *
     * @param setting 注册文件配置节点
     */
    private void loadEditors(final Element setting) {

        // 解析通用编辑界面定义节点
        List<Element> descriptors = XmlUtil.getChildElements(setting, EDITOR);
        if (ListUtil.isEmpty(descriptors)) {
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
     * 加载建议器定义
     */
    private void loadSuggestors(final Element setting) {

        // 解析通用查询建议器定义节点
        List<Element> descriptors = XmlUtil.getChildElements(setting, SUGGESTOR);
        if (ListUtil.isEmpty(descriptors)) {
            return;
        }

        for (Element descriptor : descriptors) {
            String name = XmlUtil.getAttribute(descriptor, "name");
            Element root = this.getRootElement(XmlUtil.getTextContent(descriptor));

            Suggestor suggestor = new Suggestor(root);
            this.suggestors.put(name, suggestor);
        }
    }

    /**
     * 获取浏览表格界面定义
     *
     * @param name 浏览表格界面名称
     */
    protected Grider getGrider(final String name) {
        return this.griders.get(name);
    }

    /**
     * 获取浏览表单界面定义
     *
     * @param name 浏览表格界面名称
     */
    protected Viewer getViewer(final String name) {
        return this.viewers.get(name);
    }

    /**
     * 获取编辑界面定义
     *
     * @param name 浏览表格界面名称
     */
    protected Editor getEditor(final String name) {
        return this.editors.get(name);
    }

    /**
     * 获取建议器定义
     *
     * @param name 浏览表格界面名称
     */
    protected Suggestor getSuggestor(final String name) {
        return this.suggestors.get(name);
    }
}
