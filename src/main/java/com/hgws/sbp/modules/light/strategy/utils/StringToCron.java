package com.hgws.sbp.modules.light.strategy.utils;

import java.util.ArrayList;
import java.util.List;

public class StringToCron {
    public static List<String> CycleToCron(String turnOnTheLightCycle){
        String[] split = turnOnTheLightCycle.split(";");
        String week=split[0];
        String month=split[1];
        String myself=split[2];
        List<String> result=new ArrayList<>();
        if(turnOnTheLightCycle.contains("week")){
            String[] oriCron=new String[]{"*"," ","*"," ","*"," ","?"," ","*"," ","*"};
            week = week.substring(week.indexOf(":")+1);
            oriCron[10]=week;
            StringBuilder stringBuffer = new StringBuilder();
            for (String s : oriCron) {
                stringBuffer.append(s);
            }
            result.add(String.valueOf(stringBuffer));
        }
        if(turnOnTheLightCycle.contains("month")){
            String[] oriCron=new String[]{"*"," ","*"," ","*"," ","?"," ","*"," ","?"};
            month = month.substring(month.indexOf(":")+1);
            oriCron[6]=month;
            StringBuilder stringBuffer = new StringBuilder();
            for (String s : oriCron) {
                stringBuffer.append(s);
            }
            result.add(String.valueOf(stringBuffer));
        }
        if(turnOnTheLightCycle.contains("myself")){
            String[] oriCron=new String[]{"*"," ","*"," ","*"," ","*"," ","*"," ","?"," ","*"};
            String[] split1 = myself.substring(myself.lastIndexOf(":") + 1).split("-");
            oriCron[12] = split1[0];
            oriCron[8]= split1[1];
            oriCron[6]= split1[2];
            StringBuilder stringBuffer = new StringBuilder();
            for (String s : oriCron) {
                stringBuffer.append(s);
            }
            result.add(String.valueOf(stringBuffer));
        }

        return result;
    }

    public static List<String> TimeToCron(String turnLightOnTime){
        String[] oriCron=new String[]{"0"," ","*"," ","*"," ","?"," ","*"," ","*"};
        String[] minuteHour = turnLightOnTime.split(" 至 ");
        List<String> timeString=new ArrayList<>();
        List<String> result=new ArrayList<>();
        for (String s : minuteHour) {
            String[] split = s.split(":");
            String minute=split[0];
            String hour=split[1];
            if(minute.startsWith("0")){
                minute=minute.substring(1);
            }
            if(hour.startsWith("0")){
                hour=hour.substring(1);
            }
            timeString.add(minute);
            timeString.add(hour);
        }
        oriCron[2]=timeString.get(1);
        oriCron[4]=timeString.get(0);
        StringBuffer cron = new StringBuffer();
        for (String s : oriCron) {
            cron.append(s);
        }
        result.add(String.valueOf(cron));
        oriCron[2]=timeString.get(3);
        oriCron[4]=timeString.get(2);
        cron.delete(0,cron.capacity());
        for (String s : oriCron) {
            cron.append(s);
        }
        result.add(String.valueOf(cron));
        return result;
    }

}
