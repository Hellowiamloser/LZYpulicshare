package com.hgws.sbp.modules.light.strategy.utils;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyTrigger {
    public static CronTrigger creatCronTrigger(String cron,String TirggerName,String TirggerGroup){
        return TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "triggerGroup1")// 参数 1：触发器的名称；参数 2：触发器组的名称
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))//根据cron表达式触发
                .build();
    }
}
