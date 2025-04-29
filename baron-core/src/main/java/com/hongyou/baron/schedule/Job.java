/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.schedule;

import java.util.Map;

/**
 * 系统任务接口，所有的任务实现此接口，由框架调度器调用
 *
 * @author berlin
 */
public interface Job {

    /**
     * 执行任务
     *
     * @param context 任务运行参数
     */
    void execute(Map<String, Object> context) throws JobExecutionException;
}
