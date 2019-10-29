package com.mailang.bean.qmodel;

import com.mailang.bean.pojo.DTaskBean;

import java.util.Map;

public class DTaskModel
{
    private String taskType;

    private DTaskBean data;

    private String taskId;

    public String getTaskType()
    {
        return taskType;
    }

    public void setTaskType(String taskType)
    {
        this.taskType = taskType;
    }

    public DTaskBean getData()
    {
        return data;
    }

    public void setData(DTaskBean data)
    {
        this.data = data;
    }

    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
}
