package com.mailang.jdbc.persist.meta;

import java.util.List;

public class TableMeta
{
    private String dataSource;
    private String tableName;
    private List<ColumnMeta> columns;
    private List<ColumnMeta> primaryKeys;
    private List<ColumnMeta> notPrimaryKeys;
    public String getDataSource()
    {
        return dataSource;
    }
    public void setDataSource(String dataSource)
    {
        this.dataSource = dataSource;
    }
    public String getTableName()
    {
        return tableName;
    }
    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
    public List<ColumnMeta> getColumns()
    {
        return columns;
    }
    public void setColumns(List<ColumnMeta> columns)
    {
        this.columns = columns;
    }
    public List<ColumnMeta> getPrimaryKeys()
    {
        return primaryKeys;
    }
    public void setPrimaryKeys(List<ColumnMeta> primaryKeys)
    {
        this.primaryKeys = primaryKeys;
    }
    public List<ColumnMeta> getNotPrimaryKeys()
    {
        return notPrimaryKeys;
    }
    public void setNotPrimaryKeys(List<ColumnMeta> notPrimaryKeys)
    {
        this.notPrimaryKeys = notPrimaryKeys;
    }
    @Override
    public String toString()
    {
        return "TableMeta [dataSource=" + dataSource + ", tableName=" + tableName + ", columns=" + columns
            + ", primaryKeys=" + primaryKeys + ", notPrimaryKeys=" + notPrimaryKeys + "]";
    }

}
