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
package com.hongyou.baron.ag01.faces.actions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.ag01.faces.AbstractAction;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

/**
 * 文件上传动作按钮
 *
 * @author Hong Bo Lin
 */
public class UploadAction extends AbstractAction {

    /**
     * 接受的文件类型
     */
    private final String accept;

    /**
     * 文件上传地址
     */
    private final String link;

    /**
     * 加载文件上传动作按钮定义
     *
     * @param element 动作按钮元素定义
     */
    protected UploadAction(final Element element) {
        super(element);
        this.accept = XmlUtil.getAttribute(element, "accept");
        this.link = XmlUtil.getAttribute(element, "link");
    }

    /**
     * 生成文件上传动作按钮定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode root = (ObjectNode) super.generate(env);
        root.put("accept", this.accept);
        root.put("link", this.link);
        return root;
    }
}
