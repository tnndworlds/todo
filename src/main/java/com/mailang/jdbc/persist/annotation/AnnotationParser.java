package com.mailang.jdbc.persist.annotation;

import com.mailang.jdbc.persist.DBUtils;
import com.mailang.jdbc.persist.meta.ColumnMeta;
import com.mailang.jdbc.persist.meta.DataType;
import com.mailang.jdbc.persist.meta.TableMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationParser
{
    private final static String COLUMNS = "columns";
    private final static String PRIMARY = "primaryKeys";
    private final static String NOTPRIMARY = "notPrimaryKeys";
    private final static int MODIFIER = 2;
    
    
    /** 
     * 实体类解析
     * @param clazz
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static TableMeta parseClass(Class< ? > clazz)
    {
        Table table = clazz.getAnnotation(Table.class);
        if (null == table)
        {
            return null;
        }
        TableMeta tableMeta = new TableMeta();
        tableMeta.setDataSource(table.dataSource());
        tableMeta.setTableName(table.name());
        
        Map<String, List<ColumnMeta>> attrMap = getColumns(clazz);
        tableMeta.setColumns(attrMap.get(COLUMNS));
        tableMeta.setPrimaryKeys(attrMap.get(PRIMARY));
        tableMeta.setNotPrimaryKeys(attrMap.get(NOTPRIMARY));
        return tableMeta;
    }
    
    private static Map<String, List<ColumnMeta>> getColumns(Class< ? > clazz)
    {
        Map<String, List<ColumnMeta>> cols = new HashMap<String, List<ColumnMeta>>();
        List<ColumnMeta> colList = new ArrayList<ColumnMeta>();
        List<ColumnMeta> primaryList = new ArrayList<ColumnMeta>();
        List<ColumnMeta> notPrimaryList = new ArrayList<ColumnMeta>();
        List<Field> fields = DBUtils.getFields(clazz);
        
        for (Field tmpField : fields)
        {
            int mod = tmpField.getModifiers();
            
            //只拿私有数据成员以及非Transient属性
            if (MODIFIER != mod || null != tmpField.getAnnotation(Transient.class))
            {
                continue;
            }
            ColumnMeta colMeta = new ColumnMeta();
            Column column = tmpField.getAnnotation(Column.class);
            if (null == column)
            {
                colMeta.setColumnName(DBUtils.attrToColName(tmpField.getName()));
            }
            else
            {
                colMeta.setColumnName(column.colName());
            }
            colMeta.setDataType(DataType.getDataType(tmpField.getType()));
            colMeta.setAttrName(tmpField.getName());
            
            SequenceGenerator sequenceGenerator = tmpField.getAnnotation(SequenceGenerator.class);
            if (null == sequenceGenerator)
            {
                colMeta.setAutoIncrease(false);
            }
            else
            {
                colMeta.setAutoIncrease(true);
            }
            
            colList.add(colMeta);
            Id id = tmpField.getAnnotation(Id.class);
            if (null != id)
            {
                primaryList.add(colMeta);
            }
            else
            {
                notPrimaryList.add(colMeta);
            }
        }
        cols.put(COLUMNS, colList);
        cols.put(PRIMARY, primaryList);
        cols.put(NOTPRIMARY, notPrimaryList);
        return cols;
    }
}
