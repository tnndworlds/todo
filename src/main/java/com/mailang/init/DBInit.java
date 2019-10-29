package com.mailang.init;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mailang.bean.xml.SQLCfgBean;
import com.mailang.i18n.I18N;
import com.mailang.utils.JSONUtils;
import com.mailang.utils.PROP;
import com.mailang.utils.SpringUtils;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

public class DBInit
{
    private static Logger LOG = LoggerFactory.getLogger(DBInit.class);
    
    private static DBInit instance = null;
    
    private DBInit()
    {
        
    }
    
    public static DBInit getInstance()
    {
        if (null == instance)
        {
            instance = new DBInit();
        }
        return instance;
    }
    
    public void initDB()
    {
        try
        {
            boolean initFlag = Boolean.valueOf(PROP.getPropValue("app", "initSqlFlag"));
            if (initFlag)
            {
                return;
            }
            LOG.debug("DB Init start...");
            SqlSessionTemplate sqlSession = (SqlSessionTemplate) SpringUtils.getBean("sqlSession");
            ScriptRunner sr = new ScriptRunner(sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection());
            sr.setAutoCommit(true);
            sr.setSendFullScript(false);
            sr.setEscapeProcessing(true);
            sr.setDelimiter("\\");
            String path = DBInit.class.getClassLoader().getResource("/initDB/sqlcfg.xml").getPath();
            List<SQLCfgBean> sqlCfgBeans = JSONUtils.readValue(new File(path.substring(1)), new TypeReference<List<SQLCfgBean>>() {});
            for (SQLCfgBean sqlBean : sqlCfgBeans)
            {
                sr.runScript(new InputStreamReader(DBInit.class.getResourceAsStream(sqlBean.getSqlpath()), "UTF-8"));
            }

            LOG.debug("DB Init end...");
        }
        catch (Exception e)
        {
            LOG.error("Error. e: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
