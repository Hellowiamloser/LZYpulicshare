package com.hgws.sbp.modules.light.strategy.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class CloseLight implements Job {//任务类
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        for (int i=0;i<10;i++){
            System.out.println("系统关灯");
        }
    }
}