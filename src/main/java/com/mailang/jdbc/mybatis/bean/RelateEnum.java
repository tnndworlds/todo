package com.mailang.jdbc.mybatis.bean;

/**
 * 数据库常见where子句关系
 * 【作者】： 成松松
 * 【版本】：[V01]
 * 【日期】：2017年6月13日
 */
public enum RelateEnum
{
    EQUAL("$ATTR_NAME$ = '$ATTR_VALUE$'"),
    LIKE("$ATTR_NAME$ like '%$ATTR_VALUE$%'"),
    NOTLIKE("$ATTR_NAME$ not like '%$ATTR_VALUE$%'"),
    NOTEQUAL("$ATTR_NAME$ <> '$ATTR_VALUE$'"),
    IN("$ATTR_NAME$ IN ($ATTR_VALUE$)"),
    GREATE("$ATTR_NAME$ > '$ATTR_VALUE$'"),
    LESS("$ATTR_NAME$ < '$ATTR_VALUE$'"),
    BETWEEN("$ATTR_NAME$ >= '$ATTR_MIN$' AND $ATTR_NAME$ <= '$ATTR_MAX$'");
    
    
    private String replaceStr;
    private RelateEnum(String tmpStr)
    {
        this.replaceStr = tmpStr;
    }
    
    public String getReplaceStr()
    {
        return this.replaceStr;
    }
    
    public static RelateEnum getRelateByStr(String relateStr)
    {
        try
        {
            return RelateEnum.valueOf(relateStr);
        }
        catch (Exception e)
        {
            return RelateEnum.EQUAL;
        }
    }
    
    
/*    
    public String getSql(String attrName, String attrValue, String minValue, String maxValue)
    {
        String tmpSql = this.replaceStr;
        String sql = tmpSql.replace("$ATTR_NAME$", attrName)
                           .replace("$ATTR_VALUE$", attrValue)
                           .replace("$ATTR_MIN$", minValue)
                           .replace("$ATTR_MAX$", maxValue);
        return sql;
    }*/
}
