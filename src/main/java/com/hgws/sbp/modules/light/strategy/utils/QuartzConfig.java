package com.hgws.sbp.modules.light.strategy.utils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public Scheduler stdScheduler() throws SchedulerException {
        return StdSchedulerFactory.getDefaultScheduler();
    }
}
