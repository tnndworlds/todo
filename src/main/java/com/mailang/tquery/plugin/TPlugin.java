package com.mailang.tquery.plugin;

import com.mailang.tquery.bean.ContentBean;

import java.util.Map;

public interface TPlugin
{
    void todo(ContentBean contentBean, Map<String, Object> paramMap);
}
