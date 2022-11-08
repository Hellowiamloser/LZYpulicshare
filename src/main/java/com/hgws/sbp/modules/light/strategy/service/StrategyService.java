package com.hgws.sbp.modules.light.strategy.service;
import com.hgws.sbp.commons.base.result.Result;
import com.hgws.sbp.commons.base.service.BaseService;
import com.hgws.sbp.modules.light.strategy.utils.MyTrigger;
import com.hgws.sbp.modules.light.strategy.utils.OpenLight;
import com.hgws.sbp.modules.light.strategy.utils.StringToCron;
import com.hgws.sbp.modules.light.strategy.dao.StrategyDao;
import com.hgws.sbp.modules.light.strategy.entity.Scheduled;
import com.hgws.sbp.modules.light.strategy.entity.Strategy;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StrategyService extends BaseService<StrategyDao, Strategy> {
//week:1,2,3;month:16,17,6,13,20,19,7;myself:2022-11-15;
// * * * ? * 1,2,3;
// * * * 16,17,6,13,20,19,7 * ?;
// * * * 15 11 ? 2022
//18:50 至 08:50
//0 50 18 ? * *
//0 50 8 ? * *
    @Autowired
    Scheduler stdScheduler;

    @Transactional
    public Result save(Strategy entity){
        int insert = dao.insert(entity);
        String turnLightOnTime = entity.getTurnLightOnTime();//开灯时间字符串
        List<String> timeList = StringToCron.TimeToCron(turnLightOnTime);
        String turnOnTheLightCycle = entity.getTurnOnTheLightCycle();//开灯周期字符串
        List<String> dateList = StringToCron.CycleToCron(turnOnTheLightCycle);
        Scheduled scheduled = new Scheduled();
        scheduled.setStrategyCode(entity.getStrategyId());//设置策略编号
        scheduled.setOpenTime(turnLightOnTime.split(" 至 ")[0]);//开灯时间
        scheduled.setCloseTime(turnLightOnTime.split(" 至 ")[1]);//关灯时间
        scheduled.setLightCycle(turnOnTheLightCycle);//开灯周期
        scheduled.setOpenTimeCron(timeList.get(0));//开灯时间cron
        scheduled.setCloseTimeCron(timeList.get(1));//关灯时间cron
        scheduled.setOpenCycleCron(dateList.get(0)+";"+dateList.get(1)+";"+dateList.get(2));//开灯周期cron
        scheduled.setCloseCycleCron("null");//关灯周期cron
        dao.insertScheduled(scheduled);
        //添加调度器执行的任务
        List<Scheduled> scheduleds = dao.queryScheduled(entity.getStrategyId());
        for (Scheduled scheduled1 : scheduleds) {
            String openTimeCron = scheduled1.getOpenTimeCron();
            //新建开灯任务
            JobDetail jobDetail = JobBuilder.newJob(OpenLight.class)
                    .withIdentity(entity.getStrategyId()+"1", entity.getStrategyId())// 参数 1：任务的名称；参数 2：任务组的名称
                    .build();
            //新建触发器
            CronTrigger cronTrigger = MyTrigger.creatCronTrigger(openTimeCron,entity.getStrategyId(),entity.getStrategyId());
            try {
                stdScheduler.scheduleJob(jobDetail,cronTrigger);//绑定
                stdScheduler.start();//执行调度器
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }

        return Result.success();
    }




    @Transactional(readOnly = true)
    public List<Strategy> queryPage(Strategy entity) {
        return dao.queryPage(entity);
    }
}
