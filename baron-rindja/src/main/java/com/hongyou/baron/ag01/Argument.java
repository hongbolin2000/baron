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

import com.hongyou.baron.util.XmlUtil;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.Getter;
import org.w3c.dom.Element;

import java.sql.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * 通用界面定义的查询参数
 *
 * @author Berlin
 */
@Getter
public class Argument {

    /**
     * 参数表达式(对应前端传入的字段)
     */
    private final String expr;

    /**
     * 字段(数据库字段)
     */
    private final String column;

    /**
     * 查询类型，对应数据库搜索类型(like)等
     */
    private final String type;

    /**
     * 连接条件(or等)
     */
    private String condition;

    /**
     * 加载查询参数定义
     *
     * @param root 查询参数定义元素
     */
    protected Argument(final Element root) {
        this.expr = XmlUtil.getAttribute(root, "expr");
        this.column = XmlUtil.getAttribute(root,"column");
        this.type = XmlUtil.getAttribute(root, "type", ArgumentType.LIKE);
        this.condition = XmlUtil.getAttribute(root, "condition");
    }

    /**
     * 添加查询参数
     *
     * @param expr 参数名对应前端传入的过滤参数
     * @param column 字段名对应数据库字段
     */
    protected Argument(final String expr, final String column, final String type) {
        this.expr = expr;
        this.column = column;
        this.type = type;
    }

    /**
     * 生成字段查询条件
     *
     * @param env 运行参数
     * @param wrapper 查询条件构造器
     */
    protected void resolveWrapper(final Environment env, final QueryWrapper wrapper) {
        // or
        if ("or".equals(this.condition)) {
            Consumer<QueryWrapper> consumer = (qw) -> this.conditionWrapper(env, qw);
            wrapper.or(consumer);
            return;
        }
        this.conditionWrapper(env, wrapper);
    }

    /**
     * 条件构造
     *
     * @param env 运行参数
     * @param wrapper 查询条件构造器
     */
    private void conditionWrapper(final Environment env, final QueryWrapper wrapper) {
        // like
        if (ArgumentType.LIKE.equals(this.type)) {
            wrapper.like(this.column, env.getVariables().getVariableAsString(this.expr));
        }
        // =
        if (ArgumentType.EQ.equals(this.type)) {
            wrapper.eq(this.column, env.getVariables().getVariableAsString(this.expr));
        }
        // in
        if (ArgumentType.IN.equals(this.type)) {
            this.inQuery(env, wrapper);
        }
        // date
        if (ArgumentType.DATE.equals(this.type)) {
            List<Object> values = env.getVariables().getVariableAsList(this.expr);
            if (values != null) {
                wrapper.between(this.column, new Date((Long) values.get(0)), new Date((Long) values.get(1)));
            }
        }
    }

    /**
     * in查询
     *
     * @param wrapper 查询条件构造器
     */
    private void inQuery(final Environment env, final QueryWrapper wrapper) {
        Statement statement = env.getSupportStatement(this.expr);
        if (statement != null) {
            Object[] values = (Object[]) statement.getData(env);
            wrapper.in(this.column, values);
        }
    }
}
