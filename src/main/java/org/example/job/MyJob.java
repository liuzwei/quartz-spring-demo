package org.example.job;

import org.example.service.OtherService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhaowei
 * @date 2022/7/11 16:29
 */
@Slf4j
@Component
public class MyJob implements Job {

    @Autowired
    private OtherService otherService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("My job is working....");
        otherService.doSomething();
    }
}
