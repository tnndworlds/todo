package com.mailang.taskmgt;

import com.mailang.jdbc.entity.JOBEntity;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobKey;
import org.quartz.Trigger;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.TriggerBuilder.newTrigger;

public class TASKTrigger
{
    public static Trigger getTrigger(JOBEntity jobEntity)
    {
        switch (jobEntity.getPolicyType())
        {
            case 0:   //cronTab 表达式
                return newTrigger()
                        .withSchedule(cronSchedule(jobEntity.getPolicyParam()))
                        .forJob(JobKey.jobKey(jobEntity.getId()))
                        .build();
            case 1: //定点执行
                return newTrigger()
                        .withSchedule(dailyAtHourAndMinute(getTime(jobEntity.getPolicyParam(), 0), getTime(jobEntity.getPolicyParam(), 1)))
                        .forJob(JobKey.jobKey(jobEntity.getId()))
                        .build();
            default:
                return newTrigger()
                        .withSchedule(dailyAtHourAndMinute(getTime(jobEntity.getPolicyParam(), 0), getTime(jobEntity.getPolicyParam(), 1)))
                        .forJob(JobKey.jobKey(jobEntity.getId()))
                        .build();
        }
    }

    public static Trigger getOnceTrigger()
    {
        return newTrigger()
                .startAt(new Date())
                .build();
    }

    private static Integer getTime(String timeStr, int pos)
    {
        if (StringUtils.isBlank(timeStr))
        {
            return 0;
        }
        String[]times = timeStr.split(":");
        if (pos > times.length)
        {
            return 0;
        }
        return Integer.parseInt(times[pos-1]);
    }
}
