/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.web.navigation;

import java.util.List;

/**
 * 导航菜单
 *
 * @author Berlin
 */
public interface NavigationManager {

    /**
     * 加载导航族菜单
     *
     * @param family 导航族
     */
    List<Navigate> load(final String family);

    /**
     * 加载导航族
     */
    List<Family> getFamilies();
}