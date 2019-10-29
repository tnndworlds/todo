package com.mailang.jdbc.persist.meta;

/**
 * 实体类属性元数据
 * @author  c00241496
 * @version  [V300R001C010B010SP000CP000, 2015年5月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ColumnMeta
{
private String columnName;
    
    private String attrName;
    
    private boolean autoIncrease;
    
    private DataType dataType;

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getAttrName()
    {
        return attrName;
    }

    public void setAttrName(String attrName)
    {
        this.attrName = attrName;
    }

    

    public boolean isAutoIncrease()
    {
        return autoIncrease;
    }

    public void setAutoIncrease(boolean autoIncrease)
    {
        this.autoIncrease = autoIncrease;
    }

    public DataType getDataType()
    {
        return dataType;
    }

    public void setDataType(DataType dataType)
    {
        this.dataType = dataType;
    }
    
    /**
     * {@inheritDoc}
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("[columnName : " + this.columnName);
        sb.append(", attrName : " + this.attrName);
        sb.append(", autoIncrease : " + this.autoIncrease);
        sb.append(", dataType : " + this.dataType + "]");
        return sb.toString();
    }
}
