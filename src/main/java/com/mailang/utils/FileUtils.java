package com.mailang.utils;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

/**
 * @AUTHOR c00241496
 * @create 2017-11-23
 **/
public class FileUtils
{
    /**
     * 获取资源path
     * @param resource
     * @return
     */
    public static String getResourcePath(String resource)
    {
        URL url = FileUtils.class.getClassLoader().getResource(resource);
        try
        {
            return URLDecoder.decode(url.getPath(), "UTF-8");
        }
        catch (Exception e)
        {
            return "";
        }
    }

    /**
     *
     * @param cfgDir
     * @param dataList
     * @param clazz
     * @param <T>
     */
    public static <T> void xmlToList(File cfgDir, List<T> dataList, final String postfix, TypeReference<List<T>> clazz)
    {
        if (!cfgDir.isDirectory())
        {
            return;
        }
        File[] files = cfgDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname)
            {
                if (pathname.getName().endsWith(postfix))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
        for (File cfgFile : files)
        {
            if (cfgFile.isDirectory())
            {
                xmlToList(cfgFile, dataList, postfix, clazz);
            }
            else
            {
                List<T> xmlData = JSONUtils.readValue(cfgFile, clazz);
                if (null != xmlData)
                {
                    dataList.addAll(xmlData);
                }
            }
        }
    }
}
