package com.mailang.taskmgt.plugin;

import com.mailang.jdbc.entity.JOBEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.Map;

/**
 * 加载Job中ExtraParam参数
 */
public class PreJOBExtraParamPlugin implements ParamPlugin
{
    @Override
    public void execute(JOBEntity jobEntity, Map<String, Object> paramMap)
    {
        String params = jobEntity.getExtraParam();
        if (null == params || params.trim().isEmpty())
        {
            return;
        }

        try
        {
            JSONArray paramArray = JSONArray.fromObject(params);
            for (int i = 0; i < paramArray.size(); i++)
            {
                JSONObject param = paramArray.getJSONObject(i);
                paramMap.put(param.getString("key"), param.getString("value"));
            }
        }
        catch (Exception e)
        {
            return;
        }
    }
}
