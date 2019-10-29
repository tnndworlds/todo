package com.mailang.ddtask;

import com.mailang.bean.qmodel.DTaskModel;
import com.mailang.bean.qmodel.PunchModel;
import com.mailang.cons.XSCons;
import com.mailang.ddtask.task.CycleTask;
import com.mailang.ddtask.task.DTask;
import com.mailang.ddtask.task.QuickTask;
import com.mailang.ddtask.task.TagsTask;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DTaskMgr
{
    private DTask cycleTask = new CycleTask();
    private DTask tagsTask = new TagsTask();
    private DTask quickTask = new QuickTask();

    public List<Map<String, Object>> getTaskList(String userId)
    {
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(XSCons.USER_ID, userId);
        cycleTask.createTask(retList, paramMap);
        tagsTask.createTask(retList, paramMap);
        //quickTask.createTask(retList, paramMap);
        return  retList;
    }

    public boolean punch(PunchModel punchModel)
    {
        switch (TaskTypeEnum.getTaskTypeEnum(punchModel.getPunchType()))
        {
            case CYCLE:
                return cycleTask.punch(punchModel);
            case TAGS:
                return tagsTask.punch(punchModel);
            case QUICK:
                return quickTask.punch(punchModel);
        }
        return false;
    }

    public boolean addTask(Map<String, Object> taskData, String taskType)
    {
        return true;
    }

    public boolean deleteTask(String id, String taskType)
    {
        return true;
    }

    public boolean updateTask(Map<String, Object> taskData, String taskType)
    {
        return true;
    }
}
