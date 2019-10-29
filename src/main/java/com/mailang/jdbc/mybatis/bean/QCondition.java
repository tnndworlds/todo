package com.mailang.jdbc.mybatis.bean;

import java.util.List;

/**
 * 查询条件封装
 * 【作者】： 成松松
 * 【版本】：[V01]
 * 【日期】：2017年6月13日
 */
public class QCondition
{
	/**数据库属性名称**/
    private String attrName;
    
    /**关系类型**/
    private RelateEnum relate;
    
    private Object value;
    
    private Object minValue;
    
    private Object maxValue;
    
    private List<String> inDataList;
    
    /**
     * 特定关系Where SQL生成
     * @return sub sql
     */
    public String getSql()
    {
        StringBuffer sb = new StringBuffer();
        switch (this.relate)
        {
            case EQUAL:
                return this.value.toString().trim().isEmpty() ?
                sb.append(this.attrName)
                  .append(" = '")
                  .append(this.value)
                  .append("'")
                  .append(" or ")
                  .append(this.attrName)
                  .append(" is null").toString()
                :
                sb.append(this.attrName)
                  .append(" = '")
                  .append(this.value)
                  .append("'").toString();
            case LIKE:
                return  this.value.toString().trim().isEmpty() ?
                sb.append(this.attrName)
                  .append(" like '%")
                  .append(this.value)
                  .append("%'")
                  .append(" or ")
                  .append(this.attrName)
                  .append(" is null").toString()
                :
                sb.append(this.attrName)
                  .append(" like '%")
                  .append(this.value)
                  .append("%'").toString();
            case NOTLIKE:
                return  this.value.toString().trim().isEmpty() ?
                sb.append(this.attrName)
                  .append(" not like '%")
                  .append(this.value)
                  .append("%'")
                  .append(" or ")
                  .append(this.attrName)
                  .append(" is null").toString()
                :
                sb.append(this.attrName)
                  .append(" not like '%")
                  .append(this.value)
                  .append("%'").toString();
            case NOTEQUAL:
                return this.value.toString().trim().isEmpty() ?
                sb.append(this.attrName)
                  .append(" != '")
                  .append(this.value)
                  .append("'")
                  .append(" or ")
                  .append(this.attrName)
                  .append(" is not null").toString()
                :
                sb.append(this.attrName)
                  .append(" != '")
                  .append(this.value)
                  .append("'")
                  .append(" or ")
                  .append(this.attrName)
                  .append(" is null").toString();
            case IN:
                String listValueStr = getListStr();
                return sb.append(this.attrName)
                  .append(" IN ")
                  .append(listValueStr).toString();
            case GREATE:
                return sb.append(this.attrName)
                  .append(" >= '")
                  .append(this.value)
                  .append("'").toString();
            case LESS:
                return sb.append(this.attrName)
                  .append(" <= '")
                  .append(this.value)
                  .append("'").toString();
            case BETWEEN:
                return sb.append(this.attrName)
                  .append(" >= '")
                  .append(this.minValue)
                  .append("'")
                  .append(" AND ")
                  .append(this.attrName)
                  .append(" <= '")
                  .append(this.maxValue)
                  .append("'").toString();
        }
        
        return "false";
    }
    
    
    public String getAttrName()
    {
        return attrName;
    }

    public void setAttrName(String attrName)
    {
        this.attrName = attrName;
    }

    public RelateEnum getRelate()
    {
        return relate;
    }

    public void setRelate(RelateEnum relate)
    {
        this.relate = relate;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public Object getMinValue()
    {
        return minValue;
    }

    public void setMinValue(Object minValue)
    {
        this.minValue = minValue;
    }

    public Object getMaxValue()
    {
        return maxValue;
    }

    public void setMaxValue(Object maxValue)
    {
        this.maxValue = maxValue;
    }

    public List<String> getInDataList()
    {
        return inDataList;
    }

    public void setInDataList(List<String> inDataList)
    {
        this.inDataList = inDataList;
    }
    
    private String getListStr()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        boolean isFirst = true;
        if(null != this.inDataList&&! this.inDataList.isEmpty()){
        	for (String tmpStr : this.inDataList)
        	{
        		if (!isFirst)
        		{
        			sb.append(", ");
        		}
        		sb.append('\'')
        		.append(tmpStr)
        		.append('\'');
        		isFirst = false;
        	}
        }else if(null != this.value&&!"".equals(this.value)){
        	sb.append(this.value);
        }else{
        	 return "false";
        }
        sb.append(")");
        return sb.toString();
    }
}
