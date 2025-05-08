/*
 * Copyright 2024, Hongyou Software Development Studio.
 */
package com.hongyou.baron;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 后台应用程序启动入口
 *
 * @author berlin
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.hongyou.baron.data.mapper")
public class TestMainApplication {

    /**
     * 应用程序启动
     */
    public static void main(String[] args) {
        SpringApplication.run(TestMainApplication.class, args);
    }
}
