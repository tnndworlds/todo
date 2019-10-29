package com.mailang.ddtask.task;

import com.mailang.bean.qmodel.PunchModel;
import com.mailang.cons.XSCons;
import com.mailang.ddtask.TaskTypeEnum;
import com.mailang.ddtask.filter.CycleFilter;
import com.mailang.ddtask.filter.DataFilter;
import com.mailang.log.XSLogger;
import com.mailang.tquery.TemplateDataProvider;
import com.mailang.utils.JSONUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

public class CycleTask implements DTask
{
    private static String MODULE_ID = "CycleTasks";

    private DataFilter dataFilter = new CycleFilter();

    private static XSLogger LOG = XSLogger.getLogger(CycleTask.class);

    @Override
    public void createTask(List<Map<String, Object>> taskList, Map<String, Object> paramMap)
    {
        Object retData = TemplateDataProvider.getResult(MODULE_ID, paramMap);
        try
        {
            JSONObject jsonData = JSONObject.fromObject(retData);
            JSONArray dataList = JSONUtils.getArrayByKeyPath(jsonData, "data");
            //数据适配器
            for (int i = 0; i < dataList.size(); i++)
            {
                JSONObject data = dataList.getJSONObject(i);
                if (!dataFilter.dataFilter(data))
                {
                    continue;
                }
                //过滤器
                data.put(XSCons.TYPE, TaskTypeEnum.CYCLE.getTaskType());
                taskList.add(data);
            }
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public boolean punch(PunchModel data)
    {

        return true;
    }
}
