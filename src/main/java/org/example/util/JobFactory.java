package org.example.util;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * @author liuzhaowei
 * @date 2022/7/11 16:48
 */
@Component
public class JobFactory extends AdaptableJobFactory {

    /**
     * AutowireCapableBeanFactory接口是BeanFactory的子类
     * 可以连接和填充那些生命周期不被Spring管理的已存在的bean实例
     */
    private AutowireCapableBeanFactory factory;

    /**
     * @author jinhaoxun
     * @description 构造器
     * @param factory
     */
    public JobFactory(AutowireCapableBeanFactory factory) {
        this.factory = factory;
    }

    /**
     * @author  jinhaoxun
     * @description 创建Job实例
     * @param bundle
     * @return Object
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object job = super.createJobInstance(bundle);// 实例化对象
        factory.autowireBean(job);// 进行注入（Spring管理该Bean）
        return job;//返回对象
    }
}
