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
package com.hongyou.baron.ag01.faces.widgets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.ag01.faces.AbstractWidget;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

/**
 * 文件上传输入控件
 *
 * @author Berlin
 */
public class UploadWidget extends AbstractWidget {

    /**
     * 文件存储分组路径
     */
    private final String group;

    /**
     * 展现模式(text,image,card,dragger)
     */
    private final String mode;

    /**
     * 接受的文件类型
     */
    private final String accept;

    /**
     * 加载文件上传输入控件定义
     *
     * @param element 文件上传输入控件元素定义
     */
    protected UploadWidget(final Element element) {
        super(element);
        this.group = XmlUtil.getAttribute(element, "group", "files");
        this.mode = XmlUtil.getAttribute(element, "mode", "card");
        this.accept = XmlUtil.getAttribute(element, "accept");
    }

    /**
     * 生成文件上传输入控件定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode result = (ObjectNode) super.generate(env);
        result.put("group", group);
        result.put("mode", mode);
        result.put("accept", accept);
        return result;
    }
}
