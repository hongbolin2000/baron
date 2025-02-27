/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Hong Bo Lin
 */
@Component
public class ProjectProperties {

    /**
     * 项目资源路径
     */
    @Value("${baron.resources.package}")
    private String basePath;

    /**
     * 是否开发模式
     */
    @Getter
    @Value("${baron.debug}")
    private boolean debug;

    /**
     * 项目资源路径
     */
    public String getBasePath() {
        if (basePath.contains(".")) {
            basePath = basePath.replace(".", "/");
            if (!basePath.endsWith("/")) {
                basePath = basePath + "/";
            }
        }
        return basePath;
    }
}
