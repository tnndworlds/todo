package com.mailang.tquery.dataadapter;

import com.mailang.tquery.bean.ContentBean;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

public class RecordToMap implements DataAdapter
{
    @Override
    public Object adapter(ContentBean contentBean, List<Map<String, Object>> dataList)
    {
        JSONObject retObj = new JSONObject();
        if (null == dataList || dataList.isEmpty())
        {
            return new JSONObject();
        }
        return JSONObject.fromObject(dataList.get(0));
    }
}
