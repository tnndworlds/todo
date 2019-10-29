package com.mailang.init;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sql")
public class ISQLBean implements Comparable<ISQLBean>
{
    private String name;
    private String order = null;
    private String sqlpath = null;
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getOrder()
    {
        return order;
    }
    public void setOrder(String order)
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
    
	public int compareTo(ISQLBean o)
	{
		int curOrder = Integer.parseInt(this.order);
        int parOrder = Integer.parseInt(o.order);
        return curOrder > parOrder ? 1 : -1;
	}
}
