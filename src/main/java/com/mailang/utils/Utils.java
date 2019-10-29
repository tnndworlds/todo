package com.mailang.utils;

import com.mailang.cons.XSCons;
import com.mailang.tquery.TemplateDataProvider;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

public class Utils
{
    private static final int CLASS_SUFFIX_LENGTH = 6;
    private static Logger LOG = LoggerFactory.getLogger(Utils.class);
    public static String getXMLElementValue(Element xmlEle)
    {
        return null == xmlEle ? "" : xmlEle.getStringValue();
    }

    public static String getStackTrace(Exception e)
    {
        StringBuffer sb = new StringBuffer();
        if (null != e.toString())
        {
            sb.append("ErrMsg:").append(e.toString()).append(XSCons.WRAP);
        }
        sb.append("StackTrace(More 10):");
        int lines = 0;
        for (StackTraceElement stackTraceElement : e.getStackTrace())
        {
            if (lines > 10)
            {
                break;
            }
            sb.append(stackTraceElement.getClassName())
                    .append(XSCons.SUB_SYMBOL)
                    .append(stackTraceElement.getMethodName())
                    .append(XSCons.COLON)
                    .append(stackTraceElement.getLineNumber())
                    .append(XSCons.WRAP);
            lines++;
        }
        return sb.toString();
    }

    public static String getAppRootPath(){

        try
        {
            URL appRootPath = Utils.class.getClassLoader().getResource("/");
            String path = URLDecoder.decode(appRootPath.getPath(), "UTF-8");
            return path;
        }
        catch (UnsupportedEncodingException e)
        {
            return "";
        }
    }
    public static <T> Set<Class<? extends T>> getAllClass(Class baseClass)
    {
        return getClassesInPackage(baseClass.getPackage().getName(), true);
    }

    public static <T> Set<Class<? extends T>> getClassesInPackage(String packageName, boolean recursive)
    {
        Set<Class<? extends T>> classes = new LinkedHashSet<>();
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try
        {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements())
            {
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                if ("file".equals(protocol))
                {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findClassesInPackage(packageName, filePath, recursive, classes);
                }
                else
                {
                    System.out.println();
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        return classes;
    }

    public static <T> void findClassesInPackage(String packageName, String packagePath, final boolean recursive, Set<Class<? extends T>> classes)
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
                    findClassesInPackage(pkgName, file.getCanonicalPath(), recursive, classes);
                }
                else
                {
                    // 如果是java类文件 去掉后面的.class 只留下类名
                    String className = file.getName().substring(0, file.getName().length() - CLASS_SUFFIX_LENGTH);
                    try
                    {
                        Class< ? extends T> clazz = (Class< ? extends T>)Class.forName(packageName + '.' + className);
                        if (Modifier.isAbstract(clazz.getModifiers()) || clazz.isInterface())
                        {
                            continue;
                        }
                        classes.add(clazz);
                    }
                    catch (ClassNotFoundException e)
                    {
                        LOG.error("", e);
                    }
                }
            }
        }
        catch (IOException e)
        {
            LOG.error("Invalid path found");
        }
        catch (SecurityException e)
        {
            LOG.error("Invalid path found");
        }
    }
}
