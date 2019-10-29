package com.mailang.taskmgt.plugin;

import com.mailang.jdbc.entity.JOBEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PreCurrentTimePlugin implements ParamPlugin
{
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JOBEntity jobEntity, Map<String, Object> paramMap)
    {
        paramMap.put("EndTime", simpleDateFormat.format(new Date()));
    }
}