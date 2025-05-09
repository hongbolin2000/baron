/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.view.sy01;

import com.hongyou.baron.logging.Log;
import com.hongyou.baron.logging.LogFactory;
import org.camunda.bpm.client.ExternalTaskClient;

import java.awt.*;
import java.net.URI;

/**
 * BPMN外部任务测试
 *
 * @author Berlin
 */
public class ChargeCardWorker {

    /**
     * logger
     */
    private static final Log logger = LogFactory.getLog(ChargeCardWorker.class);

    public static void main(String[] args) {
        ExternalTaskClient client = ExternalTaskClient.create().
                baseUrl("http://localhost:8081/engine-rest").
                asyncResponseTimeout(10 * 1000).
                build();

        client.subscribe("charge-card").lockDuration(1000).handler((externalTask, externalTaskService) -> {
            String item = externalTask.getVariable("item");
            Integer amount = externalTask.getVariable("amount");

            logger.info("Charging credit card with an amount of '" + amount + "'€ for the item '" + item + "'...");

            try {
                Desktop.getDesktop().browse(new URI("https://docs.camunda.org/get-started/quick-start/complete"));
            } catch (Exception e) {
                logger.error("Error while browsing charge card", e);
            }

            externalTaskService.complete(externalTask);
        }).open();

    }
}
