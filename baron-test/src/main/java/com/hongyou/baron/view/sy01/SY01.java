/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.view.sy01;

import com.hongyou.baron.logging.Log;
import com.hongyou.baron.logging.LogFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.web.bind.annotation.*;

/**
 * Camunda BPMN
 *
 * @author Berlin
 */
@RestController
@RequestMapping("/sy01")
public class SY01 {

    /**
     * logger
     */
    private static final Log logger = LogFactory.getLog(SY01.class);

    /**
     * Camunda运行服务
     */
    private final RuntimeService runtimeService;

    /**
     * Camunda任务服务
     */
    private final TaskService taskService;

    /**
     * 注入依赖
     */
    public SY01(final RuntimeService runtimeService, final TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    /**
     * 启动流程
     */
    @PostMapping("/startProcess")
    public void startProcess(@RequestBody final ProcessArgument process) {

        try {
            this.runtimeService.startProcessInstanceByKey(
                    process.getProcessDefinitionKey(), process.getBusinessKey(), process.getVariables()
            );
        } catch (Exception e) {
            logger.error("业务流程启动失败", e);
        }
    }

    /**
     * 删除流程
     */
    @PostMapping("/deleteProcess")
    public void deleteProcess(@RequestBody final ProcessArgument process) {

        try {
            Task task = this.taskService.createTaskQuery().
                    processInstanceBusinessKey(process.getBusinessKey()).
                    singleResult();
            this.runtimeService.deleteProcessInstance(task.getProcessInstanceId(), "撤销流程");
        } catch (Exception e) {
            logger.error("业务流程删除失败", e);
        }
    }

    /**
     * 完成流程
     */
    @PostMapping("completeTask")
    public void completeTask(@RequestBody final ProcessArgument process) {

        try {
            Task task = this.taskService.createTaskQuery().
                    processInstanceBusinessKey(process.getBusinessKey()).
                    singleResult();
            this.taskService.complete(task.getId(), process.getVariables());
        } catch (Exception e) {
            logger.error("业务流程执行失败", e);
        }
    }
}