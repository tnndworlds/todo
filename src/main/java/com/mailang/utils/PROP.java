package com.mailang.utils;

import com.mailang.cons.XSCons;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PROP
{
    private static Logger LOG = LoggerFactory.getLogger(PROP.class);

    private static Map<String, Properties> propMap = new HashMap<String, Properties>();

    static
    {
        init();
    }

    public static void init()
    {
        URL templateUrl = PROP.class.getClassLoader().getResource("/prop");
        try
        {
            String path = URLDecoder.decode(templateUrl.getPath(), "UTF-8");
            File propFile = new File(path);

            File[] propFiles = propFile.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name)
                {
                    if (name.endsWith("properties"))
                    {
                        return true;
                    }
                    return false;
                }
            });
            for (File tmpFile : propFiles)
            {
                Properties prop = new Properties();
                prop.load(new FileInputStream(tmpFile));
                propMap.put(tmpFile.getName().substring(0, tmpFile.getName().lastIndexOf(".properties")), prop);
            }
        }
        catch (Exception e)
        {
            LOG.error("Init properties container failed. errorMsg: {}.", e.getMessage());
        }
    }

    public static Properties getProp(String propName)
    {
        return propMap.get(propName);
    }


    public static String getPropValue(String propName, String key)
    {
        if (null != getProp(propName))
        {
            return getProp(propName).getProperty(key);
        }
        return "";
    }

    public static String getSYSPropValue(String key)
    {
        return getProp(XSCons.PROP_APP).getProperty(key);
    }
}
