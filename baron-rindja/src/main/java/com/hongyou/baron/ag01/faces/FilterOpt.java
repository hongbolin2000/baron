/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.ag01.faces;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.ag01.Environment;
import com.hongyou.baron.ag01.ValueModel;
import com.hongyou.baron.util.XmlUtil;
import lombok.Getter;
import org.w3c.dom.Element;

import java.util.List;

/**
 * 过滤器选项
 *
 * @author Hong Bo Lin
 */
public class FilterOpt extends AbstractComponent {

    /**
     * 枚举表
     */
    private final String table;

    /**
     * 枚举字段
     */
    private final String column;

    /**
     * 过滤选项值（枚举）
     */
    @Getter
    public List<ValueModel> filterOptions;

    /**
     * 加载过滤器选项定义
     *
     * @param element 过滤器选项定义元素
     */
    public FilterOpt(final Element element) {
        super(element);
        this.table = XmlUtil.getAttribute(element, "table");
        this.column = XmlUtil.getAttribute(element, "column");
    }

    /**
     * 生成过滤器选项定义
     *
     * @param env 运行参数
     */
    @Override
    public JsonNode generate(final Environment env) {
        ObjectNode result = (ObjectNode) super.generate(env);

        // 枚举字段
        if ("enum".equals(this.getType())) {
            this.filterOptions = env.getInternational().getValues(
                env.getLocal(), table + "." + column
            );
            result.set("filterOptions", env.convertValue(this.filterOptions));
        }
        return result;
    }
}
