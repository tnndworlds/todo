package com.mailang.jdbc.entity;

import com.mailang.jdbc.persist.annotation.Column;
import com.mailang.jdbc.persist.annotation.Id;
import com.mailang.jdbc.persist.annotation.SequenceGenerator;
import com.mailang.jdbc.persist.annotation.Table;

@Table(name="XS_DD_PUNCH", dataSource = "xsservice")
public class PunchEntity
{
    @Id
    @SequenceGenerator
    @Column(colName="ID")
    private String id;

    @Column(colName="USER_ID")
    private String userId;

    @Column(colName="PUNCH_TYPE")
    private String punchType;

    @Column(colName="TASK_ID")
    private String taskId;

    @Column(colName="PUNCH_DATE")
    private String punchDate;

    @Column(colName="REMARK")
    private String remark;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getPunchType()
    {
        return punchType;
    }

    public void setPunchType(String punchType)
    {
        this.punchType = punchType;
    }

    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public String getPunchDate()
    {
        return punchDate;
    }

    public void setPunchDate(String punchDate)
    {
        this.punchDate = punchDate;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
