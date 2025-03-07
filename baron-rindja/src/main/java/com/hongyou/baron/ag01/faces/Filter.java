/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.ag01.faces;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.ag01.Scheme;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器
 *
 * @author Hong Bo Lin
 */
public class Filter implements Scheme {

    /**
     * 过滤器选项
     */
    private final List<FilterOpt> opts = new ArrayList<>();

    /**
     * 加载过滤器定义
     *
     * @param element 过滤器定义元素
     */
    public Filter(final Element element) {
        List<Element> opts = XmlUtil.getChildElements(element, "opts");
        opts.forEach(opt -> this.opts.add(new FilterOpt(opt)));
    }

    /**
     * 生成过滤器定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(Environment env) {
        ArrayNode optsNode = env.createArrayNode();
        this.opts.forEach(opt -> optsNode.add(opt.generate(env)));
        return optsNode;
    }

    /**
     * 根据名称获取过滤定义
     *
     * @param name 过滤器定义的名称
     */
    public FilterOpt getOptByName(final String name) {
        return this.opts.stream().filter(i -> i.getName().equals(name)).
                findFirst().orElse(null);
    }
}
