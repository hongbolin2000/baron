/*
 * Copyright 2024, Hongyou Software Development Studio.
 */
package com.hongyou.baron.util;

import cn.hutool.core.util.StrUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Xml工具类
 *
 * @author Berlin
 */
public class XmlUtil extends cn.hutool.core.util.XmlUtil {

    private XmlUtil() {}

    /**
     * 解析子元素
     */
    public static List<Element> getChildElements(final Element parent, final String tagName) {
        List<Element> elements = new ArrayList<>();
        if (parent.hasChildNodes()) {
            NodeList nodes = parent.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && tagName.equals(node.getNodeName())) {
                    elements.add((Element) nodes.item(i));
                }
            }
        }
        return elements;
    }

    /**
     * 解析属性
     */
    public static String getAttribute(final Element element, final String name) {
        String value = element.getAttribute(name);
        return StrUtil.isBlank(value) ? "" : value;
    }
}