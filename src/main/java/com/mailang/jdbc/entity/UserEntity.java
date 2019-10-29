package com.mailang.jdbc.entity;


import com.mailang.jdbc.persist.annotation.*;

@Table(name="XS_DD_USER", dataSource = "xsservice")
public class UserEntity
{
	@Id
    @SequenceGenerator
	@Column(colName="ID")
	private String id;
	
	@Column(colName="NAME")
	private String name;

    @Column(colName="PASSWORD")
    private String password;

    @Column(colName="EMAIL")
    private String email;

    @Column(colName="TEL_NO")
    private String telNo;

    @Column(colName="TYPE")
    private String type;

    @Column(colName="CREATE_TIME")
    private String createTime;

    @Column(colName="UPDATE_TIME")
    private String updateTime;

    @Column(colName="DESCRIPTION")
    private String description;

    public String getId()
    {
        return id;
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

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getTelNo()
    {
        return telNo;
    }

    public void setTelNo(String telNo)
    {
        this.telNo = telNo;
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
