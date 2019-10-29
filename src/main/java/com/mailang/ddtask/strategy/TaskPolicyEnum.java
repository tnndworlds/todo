package com.mailang.ddtask.strategy;

import com.mailang.utils.DateTime;

import java.util.Calendar;

public enum TaskPolicyEnum
{
    NEVER_NOT_REPEAT("1"),
    MONDAY_TO_FRIDAY("2"),
    WORK_DAY("3"),
    EVERY_DAY("4"),
    CUS_DEFINE("5");

    private String value;

    private TaskPolicyEnum(String value)
    {
        this.value = value;
    }

    public static boolean todayIsEffect(String policType, String param)
    {
        TaskPolicyEnum taskPolicyEnum = getPolicyEnum(policType);
        switch (taskPolicyEnum)
        {
            case NEVER_NOT_REPEAT:
                return DateTime.isToday(param);
            case MONDAY_TO_FRIDAY:
            case WORK_DAY:
                return DateTime.todayOfWeek() <= Calendar.FRIDAY && DateTime.todayOfWeek() >= Calendar.MONDAY;
            case EVERY_DAY:
                return true;
            case CUS_DEFINE:
                int dayOfWeek = DateTime.todayOfWeek();
                return param.contains(String.valueOf(dayOfWeek));
        }
        return false;
    }

    public static TaskPolicyEnum getPolicyEnum(String policType)
    {
        for (TaskPolicyEnum taskPolicyEnum : TaskPolicyEnum.values())
        {
            if (taskPolicyEnum.value == policType)
            {
                return taskPolicyEnum;
            }
        }
        return null;
    }

}
