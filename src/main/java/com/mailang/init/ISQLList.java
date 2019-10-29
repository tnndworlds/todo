package com.mailang.init;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sqls")
public class ISQLList
{
    private List<ISQLBean> isqlList;

    @XmlElement(name="sql")
    public List<ISQLBean> getIsqlList()
    {
        return isqlList;
    }

    public void setIsqlList(List<ISQLBean> isqlList)
    {
        this.isqlList = isqlList;
    }
}
