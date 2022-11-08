package com.hgws.sbp.modules.light.strategy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hgws.sbp.commons.base.entity.Base;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Setter
@Getter
@TableName("scheduled")
@Component
public class Scheduled {
    /** 策略编号 */
    private String strategyCode ;
    /** 开灯时间 */
    private String openTime ;
    /** 关灯时间 */
    private String closeTime ;
    /** 开关灯周期 */
    private String lightCycle ;
    /** 开灯时间cron */
    private String openTimeCron ;
    /** 关灯时间cron */
    private String closeTimeCron ;
    /** 开灯周期cron */
    private String openCycleCron ;
    /** 关灯周期cron */
    private String closeCycleCron ;

    @Override
    public String toString() {
        return "Scheduled{" +
                "strategyCode='" + strategyCode + '\'' +
                ", openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", lightCycle='" + lightCycle + '\'' +
                ", openTimeCron='" + openTimeCron + '\'' +
                ", closeTimeCron='" + closeTimeCron + '\'' +
                ", openCycleCron='" + openCycleCron + '\'' +
                ", closeCycleCron='" + closeCycleCron + '\'' +
                '}';
    }
}
