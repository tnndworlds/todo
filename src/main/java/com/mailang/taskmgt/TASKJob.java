package com.mailang.taskmgt;
import com.mailang.jdbc.entity.JOBEntity;
import com.mailang.taskmgt.bean.StatusEnum;
import com.mailang.taskmgt.plugin.ParamPlugin;
import com.mailang.taskmgt.task.XSTask;
import com.mailang.utils.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.*;


public class TASKJob implements Job
{
    private JOBEntity jobEntity;

    @Override
    public void execute(JobExecutionContext jobExecutionContext)
    {
        try
        {
            if(jobEntity.getExeStatus() == StatusEnum.running.getStatus())
            {
                return;
            }
            //开始执行任务
            jobEntity.setExeStatus(StatusEnum.running.getStatus());
            jobEntity.setStartTime(DateTime.getCurrentTime());

            //前置插件生成参数Map
            Map<String, Object> paramMap = new HashMap<>();
            runPlugin(jobEntity.getPrePlugin(), paramMap);

            XSTask xsTask = (XSTask) Class.forName(jobEntity.getTask().trim()).newInstance();
            xsTask.execute(paramMap);

            //后置插件写入任务状态信息及参数
            runPlugin(jobEntity.getPostPlugin(), paramMap);
            jobEntity.setLastResult(StatusEnum.success.getStatus());
        }
        catch (Exception e)
        {
            jobEntity.setLastResult(StatusEnum.failed.getStatus());
        }
        jobEntity.setEndTime(DateTime.getCurrentTime());
        jobEntity.setExeStatus(StatusEnum.stoped.getStatus());
    }

    private void runPlugin(String pluginStr, Map<String, Object> paramMap)
    {
        if (null == pluginStr || pluginStr.trim().isEmpty())
        {
            return;
        }
        List<String> paramPlugins = getArrayFromStr(pluginStr);
        for (String plugin : paramPlugins)
        {
            try
            {
                ParamPlugin paramPlugin = (ParamPlugin)Class.forName(plugin.trim()).newInstance();
                paramPlugin.execute(jobEntity, paramMap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private List<String> getArrayFromStr(String mutiplyPluginStr)
    {
        List<String> retList = new ArrayList<>();
        if (null == mutiplyPluginStr || mutiplyPluginStr.trim().isEmpty())
        {
            return retList;
        }
        if (mutiplyPluginStr.trim().indexOf("[") != -1)
        {
            mutiplyPluginStr = mutiplyPluginStr.substring(1, mutiplyPluginStr.length() - 1);
        }
        for (String plugin : mutiplyPluginStr.split(","))
        {
            if (plugin.trim().isEmpty())
            {
                continue;
            }
            retList.add(plugin.trim());
        }
        return retList;
    }

    public JOBEntity getJobEntity()
    {
        return jobEntity;
    }

    public void setJobEntity(JOBEntity jobEntity)
    {
        this.jobEntity = jobEntity;
    }
}
