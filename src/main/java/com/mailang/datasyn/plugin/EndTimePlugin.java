package com.mailang.datasyn.plugin;

import com.mailang.bean.URLBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class EndTimePlugin implements Plugin
{
    private String formatStr = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void excute(URLBean urlBean, Map<String, Object> paramMap)
    {
        String timeStr = new SimpleDateFormat(formatStr).format(new Date());
        paramMap.put("EndTime", timeStr);
        paramMap.put("StartTime", timeStr);
    }
}
