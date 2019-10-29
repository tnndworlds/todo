package com.mailang.jdbc.persist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mailang.jdbc.persist.annotation.AnnotationParser;
import com.mailang.jdbc.persist.meta.ColumnMeta;
import com.mailang.jdbc.persist.meta.TableMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 【作者】： chengsongsong
 * 【类名】：org.sony.jdbc.persistTableFactory
 * 【版本】：[V01R01C01]
 * 【日期】：2017年6月11日
 */
public class TableFactory
{
    private static Logger LOG = LoggerFactory.getLogger(TableFactory.class);
    
    private static TableFactory instance = null;
    
    //数据库表名索引：类元数据
    private static Map<String, TableMeta> tableName2Meta = new HashMap<String, TableMeta>();
    
    //类名索引：类元数据
    private static Map<String, TableMeta> clazzName2Meta = new HashMap<String, TableMeta>();
    
    //类名：表名
    private static Map<String, String> class2TableName = new HashMap<String, String>();
    
    private static final String PACKAGE_NAME = "com.mailang.jdbc.entity";
    
    private static boolean initFlag = false;
    
    private TableFactory()
    {
        init();
    }
    public static TableFactory getInstance()
    {
        if (null == instance)
        {
            instance = new TableFactory();
        }
        return instance;
    }
    
    /** 
     * 初始化方法
     * @see [类、类#方法、类#成员]
     */
    public void init()
    {
        //TODO:需要由平台拉起
        tableName2Meta.clear();
        clazzName2Meta.clear();
        class2TableName.clear();
        
        Set<Class< ? >> classes = EntityScanner.getClassesInPackage(PACKAGE_NAME, true);
        if (null == classes || classes.isEmpty())
        {
            return;
        }
        try
        {  
            for (Class< ? > clazz : classes)
            {
                TableMeta tableMeta = AnnotationParser.parseClass(clazz);
                tableName2Meta.put(tableMeta.getTableName(), tableMeta);
                clazzName2Meta.put(clazz.getName(), tableMeta);
                class2TableName.put(clazz.getName(), tableMeta.getTableName());
            }
            initFlag = true;
        }
        catch (Exception e)
        {
            LOG.error("Parse class meta data failed."  + e.getMessage());
        }
    }
    
    /** 
     * 通过数据库表名获取类元数据信息
     * @param tableName
     * @return
     * @see [类、类#方法、类#成员]
     */
    public TableMeta getMetaByTableName(String tableName)
    {
        return tableName2Meta.get(tableName);
    }
    
    /** 
     * 通过类名获取类元数据信息
     * @param clazzName
     * @return
     * @see [类、类#方法、类#成员]
     */
    public TableMeta getMetaByClazz(String clazzName)
    {
        return clazzName2Meta.get(clazzName);
    }
    
    /** 
     * 根据属性名，返回数据库对应的属性ID
     * @param clazzName
     * @param attrName
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String getDBAttrId(String clazzName, String attrName)
    {
        TableMeta tableMeta = clazzName2Meta.get(clazzName);
        List<ColumnMeta> cols = tableMeta.getColumns();
        for (ColumnMeta tmpCol : cols)
        {
            if (tmpCol.getAttrName().equals(attrName))
            {
                return tmpCol.getColumnName();
            }
        }
        return null;
    }
    
    /** 
     * 实体类名转对应的数据库表名
     * @param className
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String getTableNameByClassName(String className)
    {
        return class2TableName.get(className);
    }
    
    /** 
     * 是否初始化成功
     * @return
     * @see [类、类#方法、类#成员]
     */
    public boolean isInit()
    {
        return initFlag;
    }
}

