/*
 * Copyright 2024, Hongyou Software Development Studio.
 */
package com.hongyou.baron.view.pages;

import com.hongyou.baron.logging.Log;
import com.hongyou.baron.logging.LogFactory;
import com.hongyou.baron.web.navigation.Navigate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航菜单
 *
 * @author Berlin
 */
@RestController
@RequestMapping("/menu")
public class MenuPage {

    /**
     * logger
     */
    private static final Log logger = LogFactory.getLog(MenuPage.class);

    /**
     * 加载菜单
     *
     * @param familyName 需加载的导航族
     */
    @GetMapping("/load/{familyName}")
    public List<Navigate> load(@PathVariable final String familyName) {

        try {
            List<Navigate> menuOptions = new ArrayList<>();
            menuOptions.add(Navigate.builder().
                    id("sy99").
                    url("/graces/grider/sy99/storeGrider").
                    fullUrl("/graces/grider/sy99/storeGrider").
                    label("通用界面示例").build()
            );

            return menuOptions;
        } catch (Exception e) {
            logger.error("菜单加载失败", e);
            return new ArrayList<>();
        }
    }
}
