package com.mailang.ddtask;

public enum TaskTypeEnum
{
    CYCLE("cycle"),
    TAGS("tags"),
    QUICK("quick");

    private String taskType;
    private TaskTypeEnum(String taskType)
    {
        this.taskType = taskType;
    }

    public String getTaskType()
    {
        return this.taskType;
    }

    public static TaskTypeEnum getTaskTypeEnum(String taskType)
    {
        for (TaskTypeEnum taskTypeEnum : TaskTypeEnum.values())
        {
            if (taskTypeEnum.taskType.equals(taskType))
            {
                return taskTypeEnum;
            }
        }
        return CYCLE;
    }
}
