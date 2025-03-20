/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.ag01;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.util.XmlUtil;
import org.w3c.dom.Element;

/**
 * 通用查询建议器定义
 *
 * @author Hong Bo Lin
 */
public class Suggestor {

    /**
     * 查询语句
     */
    private final Statement statement;

    /**
     * 最多查询记录调试(-1表示不限制)
     */
    private final int maxRows;

    /**
     * 加载界面定义
     *
     * @param root 界面定义配置节点
     */
    public Suggestor(final Element root) {
        Element maxRowsNode = XmlUtil.getChildElement(root, "maxRows");
        if (maxRowsNode != null) {
            maxRows = Integer.parseInt(XmlUtil.getTextContent(maxRowsNode));
        } else {
            maxRows = -1;
        }

        // 加载定义的查询语句
        Element statement = XmlUtil.getChildElement(root, "statement");
        if (statement != null) {
            this.statement = new Statement(statement, false);
            this.statement.addFields(root, "fields", "field");
        } else {
            this.statement = null;
        }
    }

    /**
     * 查询建议器数据
     */
    public JsonNode getData(final Environment env) {
        ObjectNode result = env.createObjectNode();
        result.set("data", (ArrayNode) this.statement.getData(env, null, this.maxRows));
        result.put("maxRows", this.maxRows);
        return result;
    }
}
