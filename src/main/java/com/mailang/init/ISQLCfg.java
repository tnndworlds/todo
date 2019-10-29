package com.mailang.init;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ISQLCfg
{
    private boolean initflag = false;
    
    private static Logger LOG = LoggerFactory.getLogger(ISQLCfg.class);
    
    private  List<ISQLBean> isqlList = null;
    
    private static ISQLCfg instance = null;
    
    private ISQLCfg()
    {
        init();
    }
    
    private void init()
    {
        try
        {
            InputStream fileName = ISQLCfg.class.getClassLoader()
                    .getResourceAsStream("/init/sql/sql.xml");
            JAXBContext context = JAXBContext.newInstance(ISQLList.class);
            Unmarshaller ums = context.createUnmarshaller();
            ISQLList tmpList = (ISQLList) ums.unmarshal(fileName);
            if (null == tmpList)
            {
                LOG.debug("Load Score item Failed. Check config file:/init/sql/sql.xml");
                return;
            }
            isqlList=tmpList.getIsqlList();
            Collections.sort(isqlList);
            initflag = true;
        }
        catch (Exception e)
        {
            initflag = false;
        }
    }
    
    public static ISQLCfg getInstance()
    {
        if (null == instance)
        {
            instance = new ISQLCfg();
        }
        return instance;
    }

    public List<ISQLBean> getIsqlList()
    {
        return isqlList;
    }
}
