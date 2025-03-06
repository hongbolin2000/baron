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

import cn.hutool.core.io.resource.ResourceUtil;
import com.hongyou.baron.util.XmlUtil;
import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.InputStream;

/**
 * 通用界面描述文件
 *
 * @author Hong Bo Lin
 */
@Getter
public abstract class AbstractDescriptor {

    /**
     * METADATA
     */
    private static final String METADATA = "/metadata/baron-rindja-config.xml";

    /**
     * 项目路径
     */
    private final String basePath;

    /**
     * 模块号
     */
    private  final String module;

    /**
     * @param basePath 项目资源路径
     * @param module 模块号
     */
    public AbstractDescriptor(final String basePath, final String module) {
        this.basePath = basePath;
        this.module = module;
    }

    /**
     * 加载模块定义的注册文件
     */
    protected Element loadDescriptor() {
        InputStream stream = ResourceUtil.getStream(this.basePath + this.module + METADATA);
        Document document = XmlUtil.readXML(stream);
        return XmlUtil.getRootElement(document);
    }

    /**
     * 加载模块定义的界面描述文件
     *
     * @param source 界面定义文件名
     */
    protected Element getRootElement(final String source) {
        InputStream stream = ResourceUtil.getStream(basePath + module + "/" + source);
        Document document = XmlUtil.readXML(stream);
        return XmlUtil.getRootElement(document);
    }
}
