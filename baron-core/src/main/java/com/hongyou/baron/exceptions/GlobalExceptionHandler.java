/*
 * Copyright 2024, Hongyou Software Development Studio.
 */
package com.hongyou.baron.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局处理HTTP运行时异常类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获异常并将异常信息返回
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
