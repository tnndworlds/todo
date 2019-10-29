package com.mailang.jdbc.entity;


import com.mailang.jdbc.persist.annotation.Column;
import com.mailang.jdbc.persist.annotation.Id;
import com.mailang.jdbc.persist.annotation.SequenceGenerator;
import com.mailang.jdbc.persist.annotation.Table;

@Table(name="TBL_XS_JOB")
public class JOBEntity
{
    @Id
    @SequenceGenerator
    @Column(colName="ID")
    private String id;

    @Column(colName="NAME")
    private String name;

    @Column(colName="TASK")
    private String task;

    @Column(colName="PRE_PLUGIN")
    private String prePlugin;

    @Column(colName="POST_PLUGIN")
    private String postPlugin;

    @Column(colName="EXE_STATUS")
    private Integer exeStatus;

    @Column(colName="POLICY_TYPE")
    private Integer policyType;

    @Column(colName="POLICY_PARAM")
    private String policyParam;

    @Column(colName="LAST_RESULT")
    private Integer lastResult;

    @Column(colName="JOB_STATUS")
    private Integer jobStatus;

    @Column(colName="DESCRIPTION")
    private String description;

    @Column(colName="JOB_TYPE")
    private String jobType;

    @Column(colName="IS_SYSTEM")
    private Integer isSystem;

    @Column(colName="DEPARTMENT")
    private String department;

    @Column(colName="EXTRA_PARAM")
    private String extraParam;

    @Column(colName="LAST_UPDATE_TIME")
    private String lastUpdateTime;

    @Column(colName="START_TIME")
    private String startTime;

    @Column(colName="END_TIME")
    private String endTime;

    public String getId()
    {
        return id;
    }

    public String getStartTime()
    {
        return startTime;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTask()
    {
        return task;
    }

    public void setTask(String task)
    {
        this.task = task;
    }

    public String getPrePlugin()
    {
        return prePlugin;
    }

    public void setPrePlugin(String prePlugin)
    {
        this.prePlugin = prePlugin;
    }

    public String getPostPlugin()
    {
        return postPlugin;
    }

    public void setPostPlugin(String postPlugin)
    {
        this.postPlugin = postPlugin;
    }

    public Integer getExeStatus()
    {
        return exeStatus;
    }

    public void setExeStatus(Integer exeStatus)
    {
        this.exeStatus = exeStatus;
    }

    public Integer getPolicyType()
    {
        return policyType;
    }

    public void setPolicyType(Integer policyType)
    {
        this.policyType = policyType;
    }

    public String getPolicyParam()
    {
        return policyParam;
    }

    public void setPolicyParam(String policyParam)
    {
        this.policyParam = policyParam;
    }

    public Integer getLastResult()
    {
        return lastResult;
    }

    public void setLastResult(Integer lastResult)
    {
        this.lastResult = lastResult;
    }

    public Integer getJobStatus()
    {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus)
    {
        this.jobStatus = jobStatus;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getJobType()
    {
        return jobType;
    }

    public void setJobType(String jobType)
    {
        this.jobType = jobType;
    }

    public Integer getIsSystem()
    {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem)
    {
        this.isSystem = isSystem;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getExtraParam()
    {
        return extraParam;
    }

    public void setExtraParam(String extraParam)
    {
        this.extraParam = extraParam;
    }

    public String getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }
}

