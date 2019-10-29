package com.mailang.taskmgt.plugin;

import com.mailang.cons.XSCons;
import com.mailang.jdbc.entity.JOBEntity;
import com.mailang.tquery.TemplateDataProvider;
import com.mailang.utils.JSONUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import java.util.Map;

public class PreModuleParamPlugin implements ParamPlugin
{
    @Override
    public void execute(JOBEntity jobEntity, Map<String, Object> paramMap)
    {
        String moduleIds = String.valueOf(paramMap.get("paramModules"));
        if (StringUtils.isBlank(moduleIds))
        {
            return;
        }

        String []modules = moduleIds.split(XSCons.COMMA);
        for (String module : modules)
        {
            if (StringUtils.isBlank(module))
            {
                continue;
            }
            try
            {
                Object tObject = TemplateDataProvider.getResult(module, paramMap);
                JSONObject params = JSONUtils.getJSONByKeyPath(JSONObject.fromObject(tObject), "data");
                paramMap.putAll(params);
            }
            catch (Exception e)
            {

            }
        }
    }
}
