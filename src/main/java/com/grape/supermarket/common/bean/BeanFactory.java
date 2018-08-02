package com.grape.supermarket.common.bean;


import com.grape.supermarket.common.util.PropertiesLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * bean工厂
 *
 * @author xunpengliu
 * @version 创建时间：2017年5月22日 下午3:40:29
 */
@ComponentScan
public class BeanFactory {

    @Bean(name = "properties")
    public PropertiesLoader propertiesLoader() {
        return new PropertiesLoader("config.properties");
    }

    @Bean(name = "wechatProperties")
    public PropertiesLoader wechatPropertiesLoader() {
        return new PropertiesLoader("wechat.properties");
    }

    @Bean(name="taskScheduler")
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler tpts = new ThreadPoolTaskScheduler();
        tpts.setPoolSize(10);
        tpts.setDaemon(true);
        return tpts;
    }
}
