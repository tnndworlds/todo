package com.mailang.taskmgt.plugin;

import com.mailang.jdbc.entity.JOBEntity;

import java.util.Map;

public interface ParamPlugin
{
    public void execute(JOBEntity jobEntity, Map<String, Object> paramMap);
}
