package com.mailang.datasyn;

import com.mailang.bean.URLBean;
import com.mailang.datasyn.plugin.Plugin;
import com.mailang.xsexception.XSException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 *
 */
public abstract class DataSynchronized
{
    private static Logger LOG = LoggerFactory.getLogger(DataSynchronized.class);

    public void request(String rName, Map<String, Object> paramMap)
    {
        URLBean urlBean = CFG.getURLBean(rName);
        if (null == urlBean)
        {
            return;
        }

        //pre plugin execute//
        executePlugin(urlBean.getPrePlugin(), urlBean, paramMap);

        while (true)
        {
            JSONObject resData = this.getResponse(urlBean, paramMap);
            if (null == resData)
            {
                throw new XSException("1001");
            }

            //data adapter
            CFG.getDataAdapter(urlBean.getDataAdapter()).parse(urlBean, paramMap, resData);

            //condition to break
            if (CFG.getConditionPlugin(urlBean.getConditionPlugin()).isBreak(urlBean, paramMap, resData))
            {
                break;
            }
        }

        //post plugin execute
        executePlugin(urlBean.getPostPlugin(), urlBean, paramMap);
    }

    protected abstract JSONObject getResponse(URLBean urlBean, Map<String, Object> paramMap);

    private void executePlugin(String plugins, URLBean urlBean, Map<String, Object> paramMap)
    {
        if (StringUtils.isBlank(plugins))
        {
            return;
        }

        try
        {
            JSONArray pluginArray = JSONArray.fromObject(plugins);
            if (null == pluginArray)
            {
                return;
            }

            for (int i = 0; i < pluginArray.size(); i++)
            {
                String pluginKey = pluginArray.getString(i);
                Plugin plugin = CFG.getPlugin(pluginKey);
                plugin.excute(urlBean, paramMap);
            }
        }
        catch (Exception e)
        {
            LOG.error("Plugin execute failed. plugins: {}. error: {}.", plugins, e);
        }
    }
}
