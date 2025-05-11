/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.view.sy01;

import lombok.Data;

import java.util.Map;

/**
 * @author Berlin
 */
@Data
public class ProcessArgument {

    /**
     * 流程定义的ID
     */
    private String processDefinitionKey;

    /**
     * 流程执行的业务主键
     */
    private String businessKey;

    /**
     * 流程执行的变量
     */
    private Map<String, Object> variables;
}
