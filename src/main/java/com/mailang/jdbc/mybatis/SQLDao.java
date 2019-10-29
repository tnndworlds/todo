package com.mailang.jdbc.mybatis;

import com.mailang.jdbc.mybatis.bean.DBean;
import com.mailang.jdbc.mybatis.mapper.SQLMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 原生SQL执行类入口
 * 【作者】： 成松松
 * 【版本】：[V01]
 * 【日期】：2017年6月13日
 */
public class SQLDao
{
	private Logger LOG = LoggerFactory.getLogger(SQLDao.class);
	
    @Autowired
    protected SQLMapper sqlMapper;
    
    public SQLMapper getSqlMapper()
    {
        return sqlMapper;
    }

    public void setSqlMapper(SQLMapper sqlMapper)
    {
        this.sqlMapper = sqlMapper;
    }
    
    /**
     * 查询SQL执行，通常返回结果为数据列表
     * @param sql 可查询SQL语句
     * @return 数据列表
     */
    public List<Map<String, Object>> queryBySql(String sql)
    {
        DBean reqBean = new DBean();
        reqBean.setSql(sql);
        return sqlMapper.queryBySql(reqBean);
    }
    
    /**
     * 无数据返回SQL执行
     * @param sql SQL语句
     * @return 正常返回0， 执行失败返回-1
     */
    public int executeSql(String sql)
    {
        DBean reqBean = new DBean();
        reqBean.setSql(sql);
        try
        {
        	sqlMapper.executeSql(reqBean);
        	return 0;
        }
        catch (Exception e)
        {
        	LOG.error("SQL Error(Excute). sql:{}. Msg:{}", sql, e.getMessage());
        	return -1;
        }
    }

    public int saveSql(String tblName, Map<String, Object> dataMap)
    {
        DBean reqBean = new DBean();
        String sql = getInsertSql(tblName, dataMap);

        return executeSql(sql);
    }

    private String getInsertSql(String tblName, Map<String, Object> dataMap)
    {
        StringBuffer sbSql = new StringBuffer();
        StringBuffer colStr = new StringBuffer();
        StringBuffer valueStr = new StringBuffer();
        Set<Map.Entry<String, Object>> set = dataMap.entrySet();
        for (Map.Entry<String, Object> entry : set)
        {
            colStr.append(entry.getKey()).append(',');
            valueStr.append('\'').append(entry.getValue()).append('\'').append(',');
        }

        sbSql.append("INSERT INTO ")
          .append(tblName)
          .append('(')
          .append(colStr.substring(0, colStr.length() - 1))
          .append(") VALUES (")
          .append(valueStr.substring(0, valueStr.length() - 1))
          .append(')');

        return sbSql.toString();
    }

}
