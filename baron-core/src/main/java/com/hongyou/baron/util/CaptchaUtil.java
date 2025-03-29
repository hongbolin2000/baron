/*
 * Copyright 2024, Hongyou Software Development Studio.
 */
package com.hongyou.baron.util;

import cn.hutool.captcha.LineCaptcha;
import com.hongyou.baron.cache.CacheUtil;
import com.hongyou.baron.cache.TimedCache;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 验证码工具栏
 *
 * @author Berlin
 */
public class CaptchaUtil {

    private CaptchaUtil() {}

    /**
     * 缓存服务器端生成的验证码(key: 生成的验证码ID, value: 验证码图片)
     */
    private static final TimedCache<String, LineCaptcha> captchaCaches = CacheUtil.newTimedCache(60 * 1000L);

    /**
     * 验证码失效
     */
    public static final String EXPIRE = "Expire";

    /**
     * 验证码错误
     */
    public static final String ERROR = "Error";

    /**
     * 验证成功
     */
    public static final String SUCCESS = "Success";

    /**
     * 生成验证码
     */
    public static void createCaptcha(final HttpServletResponse response) throws IOException {
        // 生成图形验证码
        LineCaptcha lineCaptcha = cn.hutool.captcha.CaptchaUtil.createLineCaptcha(100, 30, 5, 30);
        lineCaptcha.write(response.getOutputStream());

        // 将验证码存入内存中
        String captchaAgentId = SUID.simpleUUID();
        captchaCaches.put(captchaAgentId, lineCaptcha);

        // 将验证码ID放入headers中
        response.setContentType("image/jpeg");
        response.addHeader("Captcha-Id", captchaAgentId);
        response.addHeader("Access-Control-Expose-Headers", "Captcha-Id");
    }

    /**
     * 检验验证码
     */
    public static String verify(final String captchaId, final String captchaValue) {
        LineCaptcha lineCaptcha = captchaCaches.get(captchaId, false);
        if (ObjectUtil.isNull(lineCaptcha)) {
            return EXPIRE;
        }
        boolean verified = lineCaptcha.verify(captchaValue);
        if (!verified) {
            return ERROR;
        }
        captchaCaches.remove(captchaId);
        return SUCCESS;
    }
}
