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

    /**
     * 私有构造函数
     */
    private XmlUtil() {}

    /**
     * 解析子元素
     *
     * @param parent 父元素
     * @param tagName 标签名
     * @return 子元素集合
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
     * 解析第一个子元素
     *
     * @param parent 父元素
     * @param tagName 标签名
     * @return 第一个子元素
     */
    public static Element getChildElement(final Element parent, final String tagName) {
        if (parent.hasChildNodes()) {
            NodeList nodes = parent.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE && tagName.equals(node.getNodeName())) {
                    return (Element) nodes.item(i);
                }
            }
        }
        return null;
    }

    /**
     * 解析字符属性
     *
     * @param element 待解析的元素
     * @param name 属性名
     * @return 有值时直接返回，无值返回空串
     */
    public static String getAttribute(final Element element, final String name) {
        String value = element.getAttribute(name);
        return StrUtil.isBlank(value) ? StrUtil.EMPTY : value;
    }

    /**
     * 解析字符属性
     *
     * @param element 待解析的元素
     * @param name 属性名
     * @return 有值时直接返回，无值返回传入的默认值
     */
    public static String getAttribute(final Element element, final String name, final String defaultValue) {
        String value = element.getAttribute(name);
        return StrUtil.isBlank(value) ? defaultValue : value;
    }

    /**
     * 解析bool属性
     */
    public static boolean getAttributeAsBool(final Element element, final String name) {
        String value = element.getAttribute(name);
        return !StrUtil.isBlank(value) && Boolean.parseBoolean(value);
    }
}