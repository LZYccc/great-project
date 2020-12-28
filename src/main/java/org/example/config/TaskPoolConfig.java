package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class TaskPoolConfig {

    @Value("${async.name1.corePoolSize}")
    private int name1CorePoolSize;
    @Value("${async.name1.maxPoolSize}")
    private int name1MaxPoolSize;
    @Value("${async.name1.queueCapacity}")
    private int name1QueueCapacity;


    @Bean(name = "name1")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(name1CorePoolSize);//核心池大小
        executor.setMaxPoolSize(name1MaxPoolSize);//最大线程数
        executor.setQueueCapacity(name1QueueCapacity);//队列程度
        executor.setKeepAliveSeconds(1000*60*3);//线程空闲时间
        executor.setThreadNamePrefix("tsak-asyn");//线程前缀名称
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());//配置拒绝策略
        return executor;
    }

    @Bean(name = "name2")
    public TaskExecutor taskExecutor2() {
        return new ThreadPoolTaskExecutor();
    }
}
