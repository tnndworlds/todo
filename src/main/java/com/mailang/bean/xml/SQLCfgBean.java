package com.mailang.bean.xml;
public class SQLCfgBean
{
    private String name;
    private Integer order;
    private String sqlpath;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getOrder()
    {
        return order;
    }

    public void setOrder(Integer order)
    {
        this.order = order;
    }

    public String getSqlpath()
    {
        return sqlpath;
    }

    public void setSqlpath(String sqlpath)
    {
        this.sqlpath = sqlpath;
    }
}
