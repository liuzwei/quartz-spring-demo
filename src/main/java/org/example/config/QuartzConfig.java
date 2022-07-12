package org.example.config;

import java.io.IOException;
import java.util.Properties;

import org.example.util.JobFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author liuzhaowei
 * @date 2022/7/11 16:14
 */
@Configuration
public class QuartzConfig implements SchedulerFactoryBeanCustomizer {

    @Autowired
    private JobFactory jobFactory;

    @Bean
    public Properties properties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        // 对quartz.properties文件进行读取
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        // 在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setQuartzProperties(properties());
        schedulerFactoryBean.setJobFactory(jobFactory);
        return schedulerFactoryBean;
    }
    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean
    public Scheduler scheduler() throws IOException, SchedulerException {
        Scheduler scheduler = schedulerFactoryBean().getScheduler();
        scheduler.start();
        return scheduler;
    }
    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setStartupDelay(2);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
    }
}
