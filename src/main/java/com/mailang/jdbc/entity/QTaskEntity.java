package com.mailang.jdbc.entity;

import com.mailang.jdbc.persist.annotation.Column;
import com.mailang.jdbc.persist.annotation.Id;
import com.mailang.jdbc.persist.annotation.SequenceGenerator;
import com.mailang.jdbc.persist.annotation.Table;

@Table(name="XS_DD_QTASK", dataSource = "xsservice")
public class QTaskEntity
{
    @Id
    @SequenceGenerator
    @Column(colName="ID")
    private String id;

    @Column(colName="USER_ID")
    private String userId;

    @Column(colName="TITLE")
    private String title;

    @Column(colName="DESCRIPTION")
    private String description;

    @Column(colName="DOING_DATE")
    private String doingDate;

    @Column(colName="VALID")
    private Boolean valid;

    @Column(colName="CREATE_TIME")
    private String createTime;

    @Column(colName="UPDATE_TIME")
    private String updateTime;

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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDoingDate()
    {
        return doingDate;
    }

    public void setDoingDate(String doingDate)
    {
        this.doingDate = doingDate;
    }

    public Boolean getValid()
    {
        return valid;
    }

    public void setValid(Boolean valid)
    {
        this.valid = valid;
    }

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }

    public String getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
}
