package com.mailang.datasyn.plugin;

import com.mailang.bean.URLBean;
import net.sf.json.JSONObject;

import java.util.Map;

public class InitParamPlugin implements Plugin
{
    @Override
    public void excute(URLBean urlBean, Map<String, Object> paramMap)
    {
        JSONObject paramObj = JSONObject.fromObject(urlBean.getInitParam());
        if (null != paramObj)
        {
            paramMap.putAll(paramObj);
        }
    }
}
