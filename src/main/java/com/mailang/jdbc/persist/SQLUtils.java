package com.mailang.jdbc.persist;

import com.mailang.jdbc.mybatis.bean.QCondition;
import com.mailang.jdbc.mybatis.bean.RelateEnum;

import java.util.ArrayList;
import java.util.List;

public class SQLUtils
{
    public static String getWhereSql(List<QCondition> qCons)
    {
        if (null == qCons)
        {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        boolean isFirst = true;
        for (QCondition tmpCon : qCons)
        {
            if (!isFirst)
            {
                sb.append(") and (")
                  .append(tmpCon.getSql());
                continue;
            }
            sb.append(tmpCon.getSql());
            isFirst = false;
        }
        return sb.toString();
    }
    
    /*public static QCondition conditionAdapter(ConditionMeta attrCon)
    {
        if (null == attrCon)
        {
            return null;
        }
        QCondition qCon = new QCondition();
        qCon.setAttrName(DBUtils.getColumnName(attrCon.getKey()));
        qCon.setRelate(RelateEnum.getRelateByStr(attrCon.getRelate()));
        qCon.setValue(attrCon.getValue());
        qCon.setMinValue(attrCon.getMinValue());
        qCon.setMaxValue(attrCon.getMaxValue());
        return qCon;
    }
    
    public static List<QCondition> conditionAdapter(List<ConditionMeta> attrConList)
    {
        List<QCondition> qconList = new  ArrayList<QCondition>();
        if (null == attrConList || attrConList.isEmpty())
        {
            return qconList;
        }
        
        for (ConditionMeta attrCon : attrConList)
        {
            QCondition qCon = conditionAdapter(attrCon);
            if (null == qCon)
            {
                continue;
            }
            qconList.add(qCon);
        }
        return qconList;
    }*/
}
