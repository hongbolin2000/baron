/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.web.navigation;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 导航族属性定义
 *
 * @author Berlin
 */
@Data
@Builder
public class Family {

    /**
     * 导航族名
     */
    private String familyName;

    /**
     * 导航族标签
     */
    private String familyLabel;

    /**
     * 导航族菜单
     */
    private List<Navigate> navigates;
}
