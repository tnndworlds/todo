package com.mailang.datasyn.dataadapter;

import com.mailang.bean.URLBean;
import net.sf.json.JSONObject;

import java.util.Map;

public interface DataAdapter
{
    public void parse(URLBean urlBean, Map<String, Object> paramMap, JSONObject resData);
}
