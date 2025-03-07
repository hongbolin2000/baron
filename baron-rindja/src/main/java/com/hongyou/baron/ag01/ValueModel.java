/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.ag01;

import lombok.Builder;
import lombok.Data;

/**
 * Label Value
 *
 * @author Hong Bo Lin
 */
@Data
@Builder
public class ValueModel {

    /**
     * 显示值
     */
    private String label;

    /**
     * 枚举值
     */
    private String value;
}
