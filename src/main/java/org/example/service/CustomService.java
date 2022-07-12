package org.example.service;

import java.text.ParseException;

import org.example.job.MyJob;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhaowei
 * @date 2022/7/11 16:31
 */
@Slf4j
@Service
public class CustomService {

    @Autowired
    private Scheduler scheduler;

    public void addJob() throws Exception {
        refreshJob(
            "myJobKey",
            "MytriggerKy",
            new CronExpression("0/10 * * * * ?"),
            null,
            MyJob.class
        );
    }

    private void refreshJob(String jobKeyStr, String triggerKeyStr, CronExpression cronExpression, JobDataMap dataMap, Class clazz){
        try {
            JobKey jobKey = JobKey.jobKey(jobKeyStr);
            //查看job是否存在，如果不存在则先创建
            if (!scheduler.checkExists(jobKey)) {
                JobDetail job = JobBuilder.newJob(clazz)
                    .withIdentity(jobKey)
                    .storeDurably()
                    .build();
                scheduler.addJob(job, true);
            }

            //Job触发器
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerKeyStr);
            if (scheduler.checkExists(triggerKey)) {
                // 删除原先的Trigger
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
            }
            //重新设置触发器
            Trigger schedulerTrigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .forJob(jobKey)
//                .usingJobData(dataMap)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
            scheduler.scheduleJob(schedulerTrigger);
        } catch (SchedulerException e) {
            log.error("调度异常", e);
        }
    }
}
