package com.mailang.ddtask.filter;

import com.mailang.utils.DateTime;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CycleFilter implements DataFilter
{
    @Override
    public boolean dataFilter(Map<String, Object> data)
    {
        String policy = String.valueOf(data.get("POLICY"));
        String policyParam = String.valueOf(data.get("POLICY_PARAM"));
        String todayStr = String.valueOf(DateTime.todayOfWeek());
        switch (policy)
        {
            case "1":  //不重复  | policyParam=2019-01-29
                return DateTime.isToday(policyParam);
            case "2":  //周一到周五  | policyParam=''
                return DateTime.todayOfWeek() > 1 && DateTime.todayOfWeek() < 7;
            case "3":  //每天  | policyParam=''
                return true;
            case "4":  //自定义  | policyParam='2, 3, 4'
                return /*Arrays.stream(policyParam.split(",")).anyMatch(item->item.equals(todayStr))*/ true;
            default:
                return false;
        }
    }
}
