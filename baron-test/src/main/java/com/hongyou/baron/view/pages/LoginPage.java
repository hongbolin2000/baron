/*
 * Copyright 2024, Hongyou Software Development Studio.
 */
package com.hongyou.baron.view.pages;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hongyou.baron.util.JsonUtil;
import org.springframework.web.bind.annotation.*;

/**
 * 用户登录认证
 *
 * @author Berlin
 */
@RestController
@RequestMapping("/auth")
public class LoginPage {

    /**
     * 查询平台配置参数
     */
    @GetMapping("/getPlatformConfigure")
    public ObjectNode getProperties() {
        ObjectNode result = JsonUtil.createObjectNode();
        result.put("companyName", "测试公司");
        result.put("platformTitle", "测试框架");
        result.put("platformSubtitle", "Test Framework");
        result.put("platformSimpleTitle", "测试框架");
        result.put("captchaVerify", false);
        result.put("autoLogin", false);
        result.put("rememberAccount", false);
        result.put("rememberPassword", false);
        result.put("nikeName", "测试框架");
        result.put("avatar", "");
        return result;
    }

    /**
     * 检查是否已经登录
     */
    @PostMapping("/isLogin")
    public ObjectNode isLogin() {
        ObjectNode result = JsonUtil.createObjectNode();
        result.put("login", true);
        return result;
    }
}
