package com.mailang.datasyn;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mailang.bean.URLBean;
import com.mailang.datasyn.condition.ConditionPlugin;
import com.mailang.datasyn.dataadapter.DataAdapter;
import com.mailang.datasyn.plugin.Plugin;
import com.mailang.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CFG
{
    private static Logger LOG = LoggerFactory.getLogger(CFG.class);

    private static final int CLASS_SUFFIX_LENGTH = 6;

    private static Map<String, URLBean> urlBeanMap = new HashMap<>();

    private static Map<String, DataAdapter> dataAdapterMap = new HashMap<>();

    private static Map<String, ConditionPlugin> conditionPluginMap = new HashMap<>();

    private static Map<String, Plugin> pluginMap = new HashMap<>();

    static
    {
        initURLBean();
        initDataAdapter();
        initConditionPlugin();
        initPlugin();
    }

    private static void initURLBean()
    {
        try
        {
            URL templateUrl = CFG.class.getClassLoader().getResource("request/request.xml");
            String path = URLDecoder.decode(templateUrl.getPath(), "UTF-8");
            File templateDirFile = new File(path);
            List<URLBean> urlBeans = JSONUtils.readValue(templateDirFile, new TypeReference<List<URLBean>>(){});

            //初始化urlBeanMap=>key:name  value:SoapURLBean
            for (URLBean urlBean : urlBeans)
            {
                urlBeanMap.put(urlBean.getName(), urlBean);
            }
        }
        catch (Exception e)
        {
            LOG.error("Parse URL bean cfg failed. error: {}.", e);
        }
    }

    private static void initDataAdapter()
    {
        Map<String, Class< ? >> dataAdapterClazzs = getAllClass(DataAdapter.class.getPackage().getName());
        for (Map.Entry<String, Class< ? >> entry : dataAdapterClazzs.entrySet())
        {
            try
            {
                DataAdapter dataAdapter = (DataAdapter)entry.getValue().newInstance();
                dataAdapterMap.put(entry.getKey(), dataAdapter);
            }
            catch (Exception e)
            {
                LOG.error("New Instance failed. Error:{}.", e);
            }
        }
    }

    private static void initConditionPlugin()
    {
        Map<String, Class< ? >> conditionClazzs = getAllClass(ConditionPlugin.class.getPackage().getName());
        for (Map.Entry<String, Class< ? >> entry : conditionClazzs.entrySet())
        {
            try
            {
                ConditionPlugin conditionPlugin = (ConditionPlugin)entry.getValue().newInstance();
                conditionPluginMap.put(entry.getKey(), conditionPlugin);
            }
            catch (Exception e)
            {
                LOG.error("New Instance failed. Error:{}.", e);
            }
        }
    }

    private static void initPlugin()
    {
        Map<String, Class< ? >> pluginClazzs = getAllClass(Plugin.class.getPackage().getName());
        for (Map.Entry<String, Class< ? >> entry : pluginClazzs.entrySet())
        {
            try
            {
                Plugin plugin = (Plugin)entry.getValue().newInstance();
                pluginMap.put(entry.getKey(), plugin);
            }
            catch (Exception e)
            {
                LOG.error("New Instance failed. Error:{}.", e);
            }
        }
    }

    private synchronized static Map<String, Class< ? >> getAllClass(String packageName)
    {
        Map<String, Class< ? >> retMap = new HashMap<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        try
        {
            Enumeration<URL> files = classLoader.getResources(path);
            while (files.hasMoreElements())
            {
                URL url = files.nextElement();
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                findClassesInPackage(packageName, filePath, true, retMap);
            }

        }
        catch (Exception e)
        {
            LOG.error("Failed to instance plugin. Error: {}.", e);
        }

        return retMap;
    }

    private static void findClassesInPackage(String packageName, String packagePath, final boolean recursive, Map<String, Class< ? >> clazzMap)
    {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory())
        {
            return;
        }
        File[] dirfiles = dir.listFiles(new FileFilter()
        {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file)
            {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        try
        {
            for (File file : dirfiles)
            {
                if (file.isDirectory())
                {
                    String pkgName = packageName + "." + file.getName();
                    findClassesInPackage(pkgName, file.getCanonicalPath(), recursive, clazzMap);
                }
                else
                {
                    String className = file.getName().substring(0, file.getName().length() - CLASS_SUFFIX_LENGTH);
                    try
                    {
                        Class clazz = Class.forName(packageName + '.' + className);
                        if (clazz.isInterface())
                        {
                            continue;
                        }
                        clazzMap.put(packageName + '.' + className, clazz);
                    }
                    catch (ClassNotFoundException e)
                    {
                        LOG.error("Failed to load class. className: {}, Error: {}.", file.getName(), e);
                    }
                }
            }
        }
        catch (Exception e)
        {
            LOG.error("Invalid path found");
        }
    }


    public static URLBean getURLBean(String name)
    {
        return urlBeanMap.get(name);
    }

    public static DataAdapter getDataAdapter(String name)
    {
        return dataAdapterMap.get(name);
    }

    public static ConditionPlugin getConditionPlugin(String name)
    {
        return conditionPluginMap.get(name);
    }

    public static Plugin getPlugin(String name)
    {
        return pluginMap.get(name);
    }
}
