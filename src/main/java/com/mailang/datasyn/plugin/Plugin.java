package com.mailang.datasyn.plugin;

import com.mailang.bean.URLBean;

import java.util.Map;

public interface Plugin
{
    public void excute(URLBean urlBean, Map<String, Object> paramMap);
}
