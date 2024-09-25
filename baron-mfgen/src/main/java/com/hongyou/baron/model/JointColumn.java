/*
 * Copyright 2024, Hongyou Software Development Studio.
 */
package com.hongyou.baron.model;

import lombok.Builder;
import lombok.Data;

/**
 * 试图外联接字段
 *
 * @author Berlin
 */
@Data
@Builder
public class JointColumn {

    /**
     * 数据库字段名
     */
    private String name;

    /**
     * JSON标签
     */
    private String jlabel;

    /**
     * 英文标签
     */
    private String elabel;

    /**
     * Java类型
     */
    private String javaType;
}
