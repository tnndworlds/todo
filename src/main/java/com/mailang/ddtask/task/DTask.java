package com.mailang.ddtask.task;

import com.mailang.bean.qmodel.PunchModel;

import java.util.List;
import java.util.Map;

public interface DTask
{
    void createTask(List<Map<String, Object>> taskList, Map<String, Object> paramMap);

    boolean punch(PunchModel data);

}
