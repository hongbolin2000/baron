/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.web.navigation;

import com.hongyou.baron.ProjectProperties;
import com.hongyou.baron.cache.CacheUtil;
import com.hongyou.baron.cache.FIFOCache;
import com.hongyou.baron.util.ResourceUtil;
import com.hongyou.baron.util.StringUtil;
import com.hongyou.baron.util.XmlUtil;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 导航菜单管理器
 *
 * @author Berlin
 */
@Service
public class NavigationManager {

    /**
     * 缓存菜单
     */
    private final FIFOCache<String, Family> menuCaches = CacheUtil.newFIFOCache(5);

    /**
     * 项目配置参数
     */
    private final ProjectProperties properties;

    /**
     * 注入依赖
     */
    public NavigationManager(ProjectProperties properties) {
        this.properties = properties;
    }

    /**
     * 加载菜单
     *
     * @param family 导航祖
     */
    public List<Navigate> load(final String family) {
        // 从缓存中读取
        if (!properties.isDebug() && this.menuCaches.containsKey(family)) {
            return this.menuCaches.get(family).getNavigates();
        }

        // 加载XML文件
        InputStream stream = ResourceUtil.getStream("static/menus.xml");
        Document document = XmlUtil.readXML(stream);
        Element root = XmlUtil.getRootElement(document);

        // 解析导航族
        List<Element> familyNodes = XmlUtil.getChildElements(root, "family");
        for (Element familyNode : familyNodes) {
            String familyName = XmlUtil.getAttribute(familyNode, "name");
            String familyLabel = XmlUtil.getAttribute(familyNode, "label");

            // 解析菜单
            List<Element> menus = XmlUtil.getChildElements(familyNode, "menu");
            List<Navigate> menuOptions = this.loadMenu(menus, "", "");

            Family build = Family.builder().
                    familyName(familyName).
                    familyLabel(familyLabel).
                    navigates(menuOptions).
                    build();
            menuCaches.put(familyName, build);
        }
        if (!menuCaches.containsKey(family)) {
            return null;
        }
        return menuCaches.get(family).getNavigates();
    }

    /**
     * 从xml中解析菜单
     */
    private List<Navigate> loadMenu(final List<Element> menus, final String parentIcon, final String parentUrl) {
        List<Navigate> menuOptions = new ArrayList<>();
        for (Element menu: menus) {
            Navigate.NavigateBuilder menuOption = Navigate.builder();

            // 解析菜单属性
            String id = XmlUtil.getAttribute(menu, "id");
            String url = XmlUtil.getAttribute(menu, "url");
            String label = XmlUtil.getAttribute(menu, "label");
            String icon = XmlUtil.getAttribute(menu, "icon");

            // 递归检查是否有子菜单
            if (menu.hasChildNodes()) {
                String parentPaths = parentUrl + url;
                String parentIcons = StringUtil.isBlank(icon) ? parentIcon : icon;
                List<Element> childMenus = XmlUtil.getChildElements(menu, "menu");
                menuOption.children(this.loadMenu(childMenus, parentIcons, parentPaths));
            }

            boolean fixed = XmlUtil.getAttributeAsBool(menu, "fixed", false);
            menuOption.id(id).
                    url(url).
                    fullUrl(parentUrl + url).
                    label(label).
                    icons(icon).
                    parentIcon(parentIcon).
                    fixed(fixed);
            menuOptions.add(menuOption.build());
        }
        return menuOptions;
    }

    /**
     * 获取所有导航族
     */
    public List<Family> getFamilies() {
        List<Family> families = new ArrayList<>();
        this.menuCaches.forEach(families::add);
        return families;
    }
}
