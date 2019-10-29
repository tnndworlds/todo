package com.mailang.taskmgt.plugin;

import com.mailang.cons.XSCons;
import com.mailang.jdbc.entity.JOBEntity;
import com.mailang.jdbc.mybatis.SQLDao;
import com.mailang.tquery.TemplateDataProvider;
import com.mailang.utils.JSONUtils;
import com.mailang.utils.SpringUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 执行SQL插件，参数中携带ModuleId,多个SQL使用多个ModuleId
 */
public class PostModuleSqlPlugin implements ParamPlugin
{
    private static SQLDao sqlExector = (SQLDao) SpringUtils.getBean("sqlDao");

    @Override
    public void execute(JOBEntity jobEntity, Map<String, Object> paramMap)
    {
        String moduleIds = String.valueOf(paramMap.get("postModules"));
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
                String sqlStr = JSONUtils.getValueByKeyPath(JSONObject.fromObject(tObject), "data").toString();
                sqlExector.executeSql(sqlStr);
            }
            catch (Exception e)
            {

            }
        }
    }
}
