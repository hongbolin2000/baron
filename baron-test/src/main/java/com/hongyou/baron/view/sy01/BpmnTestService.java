/*
 * Copyright 2014, Chengyou Software Development Studio.
 */
package com.hongyou.baron.view.sy01;

import com.hongyou.baron.data.DataProvider;
import com.hongyou.baron.data.model.Strems;
import com.hongyou.baron.exceptions.FlowExecutionException;
import com.hongyou.baron.logging.Log;
import com.hongyou.baron.logging.LogFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * BPMN测试服务
 *
 * @author Berlin
 */
@Service("TestService")
public class BpmnTestService extends DataProvider implements JavaDelegate {

    /**
     * logger
     */
    private static final Log logger = LogFactory.getLog(BpmnTestService.class);

    /**
     * 执行
     */
    @Override
    public void execute(DelegateExecution delegateExecution) {

        try {
            Strems strems = this.db().strems().getByStoreCode("AAA");
            strems.ordqty(strems.getOrdqty().add(BigDecimal.ONE));
            this.db().strems().save(strems);

            strems.ordqty(strems.getOrdqty().add(BigDecimal.ONE));
            this.db().strems().save(strems);

            logger.info("TestService执行成功。。。");
        } catch (Exception e) {
            logger.error("TestService执行失败。。。", e);
            throw new FlowExecutionException("TestService执行失败。。。");
        }
    }
}
