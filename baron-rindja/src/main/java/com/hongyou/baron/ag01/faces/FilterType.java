/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.ag01.faces;

/**
 * 过滤类型
 *
 * @author Hong Bo Lin
 */
public class FilterType {

    /**
     * 文本
     */
    private static final String TEXT = "text";

    /**
     * 日期
     */
    private static final String DATE = "date";

    /**
     * 过滤选项
     */
    private static final String OPT = "@";

    /**
     * 检查是否文本类型的过滤
     */
    public static boolean isText(final String filter) {
        return FilterType.TEXT.equals(filter);
    }

    /**
     * 检查是覅日期类型的过滤
     */
    public static boolean isDate(final String filter) {
        return FilterType.DATE.equals(filter);
    }

    /**
     * 检查是否选项类型的过滤
     */
    public static boolean isOpt(final String filter) {
        return filter.startsWith(FilterType.OPT);
    }
}
