package com.mailang.taskmgt;

import com.mailang.jdbc.dao.JOBDao;
import com.mailang.jdbc.entity.JOBEntity;
import com.mailang.jdbc.mybatis.bean.QCondition;
import com.mailang.taskmgt.bean.StatusEnum;
import com.mailang.utils.SpringUtils;
import com.mailang.xsexception.XSException;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.*;

import static org.quartz.JobBuilder.newJob;

/**
 * task/job做如下区分
 * task：实际执行体
 * job: 抽象出的执行
 * 任务有running/stoped状态-running：任务在执行，stoped任务执行结束
 * job有准备/就绪状态：新增任务进入准备状态, 使能开关进入就绪
 */
public class TaskMgr
{
    private SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    private JOBDao jobDao = SpringUtils.getBean(JOBDao.class);

    private Scheduler scheduler;

    private Map<String, JOBEntity> jobEntity = new HashMap<>();

    /**
     * key: jobId
     */
    private Map<String, JobDetail> jobs = new HashMap<>();

    private Map<String, Trigger> triggers = new HashMap<>();

    private static TaskMgr instance;

    private TaskMgr()
    {
        init();
    }

    private void init()
    {
        try
        {
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();
            final String temp = "css";
            List<JOBEntity> jobList = jobDao.queryRetEntity(new ArrayList<QCondition>());


            /*jobList.stream().filter(item->{
                String aa = "b" + temp;
                return true;
            });*/


            for (JOBEntity entity : jobList)
            {
                jobs.put(entity.getId(), getJobDetail(entity));
                jobEntity.put(entity.getId(), entity);
                triggers.put(entity.getId(), TASKTrigger.getTrigger(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    public static TaskMgr getInstance()
    {
        if (null == instance)
        {
            instance = new TaskMgr();
        }
        return instance;
    }

    public TaskMgr executeJob(String jobId)
    {
        try
        {
            scheduler.triggerJob(JobKey.jobKey(jobId));
        }
        catch (SchedulerException e)
        {
            e.printStackTrace();
        }
        return instance;
    }

    public void persistJob()
    {
        for (Map.Entry<String, JOBEntity> entry : jobEntity.entrySet())
        {
           this.persistJob(entry.getKey());
        }
    }

    public void persistJob(String jobId)
    {
        try
        {
            jobDao.saveOrUpdate(jobEntity.get(jobId));
        }
        catch (Exception e)
        {

        }

    }

    /**
     * 添加任务，执行策略由Quartz策略控制
     * 新增任务初始状态为准备状态//需改变任务状态为就绪
     * @param entity
     * @return
     */
    public String addJob(JOBEntity entity)
    {
        Object idValue = jobDao.save(entity);
        String jobId = String.valueOf(idValue);
        entity.setId(jobId);
        try
        {
            triggers.put(jobId, TASKTrigger.getTrigger(entity));
            jobEntity.put(jobId, entity);
            jobs.put(jobId, getJobDetail(entity));
            persistJob(jobId);
        }
        catch (Exception e)
        {
            rmJob(jobId);
            throw new XSException("500004");
        }

        return jobId;
    }

    /**
     * 任务删除//停止任务并清除相关任务信息
     * @param jobId
     * @return
     */
    public TaskMgr rmJob(String jobId)
    {
        try
        {
            scheduler.deleteJob(JobKey.jobKey(jobId));
            jobs.remove(jobId);
            triggers.remove(jobId);
            jobDao.deleteById(jobId);
            jobEntity.remove(jobId);
        }
        catch (Exception e)
        {

        }
        return instance;
    }

    /**
     * 更新任务相关信息//任务状态为执行时不能执行
     * @param entity
     * @return
     */
    public TaskMgr updateJob(JOBEntity entity)
    {
        if (entity.getJobStatus() == StatusEnum.running.getStatus())
        {
            return instance;
        }
        jobs.put(entity.getId(), getJobDetail(entity));
        triggers.put(entity.getId(), TASKTrigger.getTrigger(entity));
        return instance;
    }

    /**
     * 系统启动调用//初始化Quartz
     */
    public void start()
    {
        Set<Map.Entry<String, JOBEntity>> set = jobEntity.entrySet();
        for (Map.Entry<String, JOBEntity> entry: set)
        {
            if (entry.getValue().getJobStatus() == StatusEnum.running.getStatus())
            {
                runJob(entry.getKey());
            }
        }
    }

    /**
     * 独立运行任务
     * @param jobId
     */
    public TaskMgr runJob(String jobId)
    {
        try
        {
            scheduler.scheduleJob(jobs.get(jobId), triggers.get(jobId));
            jobEntity.get(jobId).setJobStatus(StatusEnum.running.getStatus());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return instance;
    }

    public TaskMgr stopJob(String jobId)
    {
        try
        {
            scheduler.deleteJob(JobKey.jobKey(jobId));
            jobEntity.get(jobId).setJobStatus(StatusEnum.stoped.getStatus());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return instance;
    }

    private JobDetail getJobDetail(JOBEntity jobEntity)
    {
        //获取JobDetail
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobEntity", jobEntity);
        JobDetail jobDetail = newJob(TASKJob.class)
                .withIdentity(JobKey.jobKey(jobEntity.getId()))
                .usingJobData(jobDataMap)
                .build();
        return  jobDetail;
    }

    public Map<String, JOBEntity> getJobEntity()
    {
        return jobEntity;
    }
}
