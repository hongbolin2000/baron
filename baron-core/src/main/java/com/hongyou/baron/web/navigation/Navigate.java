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
package com.hongyou.baron.web.navigation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 导航菜单属性定义
 *
 * @author Berlin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Navigate {

    /**
     * 菜单ID
     */
    private String id;

    /**
     * 权限
     */
    private String permission;

    /**
     * 动作
     */
    private String action;

    /**
     * 菜单访问地址
     */
    private String url;

    /**
     * 菜单全路径(从父菜单到子菜单拼接)
     */
    private String fullUrl;

    /**
     * 菜单显示标签
     */
    private String label;

    /**
     * 菜单图标
     */
    private String icons;

    /**
     * 父菜单图标
     */
    private String parentIcon;

    /**
     * 是否固定菜单在选项卡中
     */
    private boolean fixed;

    /**
     * 子菜单
     */
    private List<Navigate> children;
}