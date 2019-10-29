package com.mailang.ddtask.filter;

import java.util.Map;

public class AlwaysFilter implements DataFilter
{
    @Override
    public boolean dataFilter(Map<String, Object> data)
    {
        return true;
    }
}
