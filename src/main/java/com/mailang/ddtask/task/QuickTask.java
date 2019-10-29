package com.mailang.ddtask.task;

import com.mailang.bean.pojo.DTaskBean;
import com.mailang.bean.qmodel.PunchModel;

import java.util.List;
import java.util.Map;

public class QuickTask implements DTask
{
    @Override
    public void createTask(List<Map<String, Object>> taskList, Map<String, Object> paramMap)
    {

    }

    @Override
    public boolean punch(PunchModel data)
    {
        return true;
    }
}
