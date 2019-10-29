package com.mailang.jdbc.persist;

import com.mailang.jdbc.mybatis.bean.QCondition;
import com.mailang.jdbc.mybatis.bean.RelateEnum;
import com.mailang.jdbc.persist.meta.ColumnMeta;
import com.mailang.jdbc.persist.meta.DataType;
import com.mailang.jdbc.persist.meta.TableMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;
public class DBUtils
{
    private static Logger LOG = LoggerFactory.getLogger(DBUtils.class);
    
    /**
     * 获取类的所有属性域
     * 
     * @param clazz
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<Field> getFields(Class<?> clazz)
    {
        if (null == clazz)
        {
            return null;
        }
        
        List<Field> rslt = new ArrayList<Field>();
        Set<String> fieldNames = new HashSet<String>();
        while (clazz != Object.class)
        {
            Field[] fields = clazz.getDeclaredFields();
            for (Field tmpField : fields)
            {
                String name = tmpField.getName();
                if (fieldNames.contains(name))
                {
                    continue;
                }
                rslt.add(tmpField);
                fieldNames.add(name);
            }
            clazz = clazz.getSuperclass();
        }
        return rslt;
    }
    /**
     * 数据库字段转驼峰命名
     * @param colName 数据库属性名称
     * @return 驼峰命名属性名
     * @see [类、类#方法、类#成员]
     */
    public static String colToAttrName(String colName)
    {
        String[] attrs = colName.split("_");
        StringBuffer sb = new StringBuffer();
        sb.append(attrs[0].toLowerCase());
        for (int i = 1; i < attrs.length; i++)
        {
            String subAttr = attrs[i].substring(0, 1).toUpperCase() + attrs[i].substring(1).toLowerCase();
            sb.append(subAttr);
        }
        return sb.toString();
    }

    /**
     * 驼峰命名转数据库字段
     * 
     * @param attrName
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String attrToColName(String attrName)
    {
        String[] attrs = attrName.split("(?<=[a-z])(?=[A-Z])");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < attrs.length - 1; i++)
        {
            sb.append(attrs[i].toUpperCase());
            sb.append('_');
        }
        sb.append(attrs[attrs.length - 1].toUpperCase());
        return sb.toString();
    }

    /**
     * 数据适配器：数据库下划线转驼峰命名
     * eg: DB_NAME -> dbName or dbName->DB_NAME
     * @param dataMap 待转换数据
     * @param toDB true:dbName->DB_NAME  false:DB_NAME->dbName
     * @return result
     */
    public static Map<String, Object> dbDataAdapter(Map<String, Object> dataMap, boolean toDB)
    {
        Map<String, Object> retMap = new HashMap<String, Object>();
        for (String key : dataMap.keySet())
        {
            if (toDB)
            {
                retMap.put(attrToColName(key), dataMap.get(key));
            }
            else
            {
                retMap.put(attrToColName(key), dataMap.get(key));
            }
        }
        return retMap;
    }

    /**
     * 数据适配器：数据库下划线转驼峰命名
     * eg: DB_NAME -> dbName or dbName->DB_NAME
     * @param dataList 待转换数据
     * @param toDB true:dbName->DB_NAME  false:DB_NAME->dbName
     * @return result
     */
    public static List<Map<String, Object>> dbDataAdapter(List<Map<String, Object>> dataList, boolean toDB)
    {
        List<Map<String, Object>> retList = new ArrayList<>();
        for (Map<String, Object> dataMap : dataList)
        {
            retList.add(dbDataAdapter(dataMap, toDB));
        }
        return retList;
    }

    /**
     * 从数据中抽取对应属性的值
     * 
     * @param dataMap
     * @param dataType
     * @param colName
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Object getValue(Map<String, Object> dataMap, DataType dataType, String colName)
    {
        if (null == dataMap || null == dataType)
        {
            LOG.error("Parameter is null/empty.");
            return null;
        }
        Object value;
        Object tmpValue = dataMap.get(colName);
        switch (dataType)
            {
                case STRING:
                    value = String.valueOf(tmpValue);
                    break;
                case INTEGER:
                    if (null == tmpValue)
                    {
                        return 0;
                    }
                    value = Integer.parseInt(String.valueOf(tmpValue));
                    break;
                case BOOLEAN:
                    value = Boolean.parseBoolean(String.valueOf(tmpValue));
                    break;
                case LONG:
                    value = Long.parseLong(String.valueOf(tmpValue));
                    break;
                case FLOAT:
                    value = Float.parseFloat(String.valueOf(tmpValue));
                    break;
                default:
                    value = null;
            }
        return value;
    }
    
   /* public static String getWhereOfSubIn(List<String> valueList)
    {
        if (null == valueList || valueList.isEmpty())
        {
            return "()";
        }
        
        StringBuffer sb = new StringBuffer();
        sb.append('(');
        boolean isFirst = true;
        for (String value : valueList)
        {
            if (!isFirst)
            {
                sb.append(',');
            }
            sb.append('\'')
              .append(value)
              .append('\'');
            isFirst = false;
        }
        sb.append(')');
        return sb.toString();
    }*/
    
    
    /**
     * 条件Map转标准查询List(条件封装)，仅限等于
     * 
     * @param conMap
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<QCondition> getQListFromMap(Map<String, Object> conMap)
    {
        List<QCondition> conList = new ArrayList<QCondition>();
        if (null == conMap)
        {
            return conList;
        }
        Set<Entry<String, Object>> set = conMap.entrySet();
        for (Entry<String, Object> entry : set)
        {
            QCondition tmpCon = new QCondition();
            tmpCon.setAttrName(entry.getKey());
            tmpCon.setValue(entry.getValue());
            tmpCon.setRelate(RelateEnum.EQUAL);
            conList.add(tmpCon);
        }
        return conList;
    }
    
    public static <T> List<T> convertToBean(Class<T> clazz, List<Map<String, Object>> dataList)
    {
        if (null == dataList)
        {
            return null;
        }
        List<T> resultList = new ArrayList<T>();
        for (Map<String, Object> tmpMap : dataList)
        {
            T t = getEntity(clazz, tmpMap);
            resultList.add(t);
        }
        return resultList;
    }
    
    public static <T> Map<String, Object> beanToMap(Class<T> clazz, T t)
    {
        if (null == clazz || null == t)
        {
            return null;
        }
        Map<String, Object> dataMap = new HashMap<String, Object>();
        TableMeta tableMeta = TableFactory.getInstance().getMetaByClazz(clazz.getName());
        if (null == tableMeta)
        {
            LOG.error("DBOP: Request Param was invalid.");
            return null;
        }
        List<ColumnMeta> cols = tableMeta.getColumns();
        for (ColumnMeta tmpCol : cols)
        {
            String attrName = tmpCol.getAttrName();
            String key = tmpCol.getColumnName();
            StringBuffer methodName = new StringBuffer();
            methodName.append("get");
            methodName.append(attrName.replaceFirst(attrName.substring(0, 1), attrName.substring(0, 1).toUpperCase()));
            try
            {
                Method method = clazz.getMethod(methodName.toString());
                tmpCol.getDataType();
                Object value = method.invoke(t);
                if (null == value)
                {
                    continue;
                }
                dataMap.put(key, value);
            }
            catch (Exception e)
            {
                LOG.error("DBOP: Invoke Error.");
                return null;
            }
        }
        return dataMap;
    }

    /**
     * 数据库实例数据转bean类实例
     * 
     * @param clazz
     * @param data
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static <T> T getEntity(Class<T> clazz, Map<String, Object> data)
    {
        if (null == data || null == clazz)
        {
            return null;
        }
        TableMeta tableMeta = TableFactory.getInstance().getMetaByClazz(clazz.getName());
        if (null == tableMeta)
        {
            LOG.error("DBOP: className Param was invalid. className" + clazz.getName());
            return null;
        }
        
        List<ColumnMeta> cols = tableMeta.getColumns();
        T t = null;
        try
        {
            t = clazz.newInstance();
        }
        catch (Exception e)
        {
            LOG.error("DBOP: Instance Error." + clazz.getName());
            return null;
        }
        for (ColumnMeta tmpCol : cols)
        {
            String attrName = tmpCol.getAttrName();
            StringBuffer methodName = new StringBuffer();
            methodName.append("set");
            methodName.append(attrName.replaceFirst(attrName.substring(0, 1), attrName.substring(0, 1).toUpperCase()));
            try
            {
                Method method = clazz.getMethod(methodName.toString(), tmpCol.getDataType().getType());
                Object value = getValue(data, tmpCol.getDataType(), tmpCol.getColumnName());
                method.invoke(t, value);
            }
            catch (Exception e)
            {
                return null;
            }
        }
        return t;
    }
    
    public static <T> List<QCondition> getQConditionListByBean(T t, Class<T> clazz)
    {
        List<QCondition> qCons = new ArrayList<QCondition>();
        TableMeta tableMeta = TableFactory.getInstance().getMetaByClazz(clazz.getName());
        if (null == tableMeta)
        {
            LOG.error("DBOP: Request Param was invalid.");
            return null;
        }
        List<ColumnMeta> cols = tableMeta.getColumns();
        for (ColumnMeta tmpCol : cols)
        {
            String attrName = tmpCol.getAttrName();
            String key = tmpCol.getColumnName();
            StringBuffer methodName = new StringBuffer();
            methodName.append("get");
            methodName.append(attrName.replaceFirst(attrName.substring(0, 1), attrName.substring(0, 1).toUpperCase()));
            try
            {
                Method method = clazz.getMethod(methodName.toString());
                tmpCol.getDataType();
                Object value = method.invoke(t);
                if (null == value)
                {
                    continue;
                }
                QCondition con = new QCondition();
                con.setAttrName(key);
                con.setValue(value);
                con.setRelate(RelateEnum.EQUAL);
                qCons.add(con);
            }
            catch (Exception e)
            {
                LOG.error("DBOP: Invoke Error.");
                return null;
            }
        }
        return qCons;
    }
}
