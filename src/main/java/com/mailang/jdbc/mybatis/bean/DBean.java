package com.mailang.jdbc.mybatis.bean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mybatis 注解参数传递单一对象封装
 * 使用Mybatis注解提供SQL时，方法参数只能为一个，多参数封装bean
 * 【作者】： 成松松
 * 【版本】：[V01]
 * 【日期】：2017年6月13日
 */
public class DBean
{
    private Class<?> clazz;
    
    private Map<String, Object> dataMap;
    
    private Map<String, Object> conMap;
    
    private List<QCondition> conList;
    
    private String sql;
    
    private Object id;

    private Object primaryValue;

    public Class<?> getClazz()
    {
        return clazz;
    }

    public void setClazz(Class<?> clazz)
    {
        this.clazz = clazz;
    }

    public Map<String, Object> getConMap()
    {
        return conMap;
    }

    public void setConMap(Map<String, Object> conMap)
    {
        this.conMap = conMap;
    }

    public Object getId()
    {
        return id;
    }

    public void setId(Object id)
    {
        this.id = id;
    }

    public List<QCondition> getConList()
    {
        return conList;
    }

    public void setConList(List<QCondition> conList)
    {
        this.conList = conList;
    }

    public Map<String, Object> getDataMap()
    {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap)
    {
        this.dataMap = dataMap;
    }

    public String getSql()
    {
        return sql;
    }

    public void setSql(String sql)
    {
        this.sql = sql;
    }

    public Object getPrimaryValue()
    {
        return primaryValue;
    }

    public void setPrimaryValue(Object primaryValue)
    {
        this.primaryValue = primaryValue;
    }
}
