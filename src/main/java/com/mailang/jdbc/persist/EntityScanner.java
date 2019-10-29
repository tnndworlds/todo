package com.mailang.jdbc.persist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class EntityScanner
{
    private static final Logger LOG = LoggerFactory.getLogger(EntityScanner.class);
    
    private static final int CLASS_SUFFIX_LENGTH = 6;
    
    public static Set<Class<?>> getClassesInPackage(String packageName, boolean recursive)
    {
        Set<Class< ? >> classes = new LinkedHashSet<Class< ? >>();
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
                else if ("jar".equals(protocol))
                {
                    findClassesInJar(packageName, recursive, classes, packageDirName, url);
                }
                else if ("bundleresource".equals(protocol))
                {
                    findClassesInBundlesource(null,packageName,packageDirName,recursive,classes);
                }
                else
                {
                    LOG.error("find classes error");
                }
            }
        }
        catch (IOException e)
        {
            LOG.error("", e);
        }
        return classes;
    }
    
    /**
     * 搜索jar包中所有在指定package下的Class
     * 
     * @param packageName
     *            搜索的package名称
     * @param recursive
     *            是否循环迭代扫描，即递归扫描该目录下所有的package
     * @param classes
     *            扫描到所有符合条件的Class存储集合
     * @param packageDirName
     *            package目录
     * @param url
     *            地址
     */
    private static void findClassesInJar(String packageName, boolean recursive, Set<Class<?>> classes,
        String packageDirName, URL url)
    {
        try
        {
            JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements())
            {
                JarEntry entry = entries.nextElement();
                String newname = entry.getName();
                if (newname.charAt(0) == '/')
                {
                    newname = newname.substring(1);
                }
                if (newname.startsWith(packageDirName))
                {
                    int id = newname.lastIndexOf('/');
                    if (id != -1)
                    {
                        packageName = newname.substring(0, id).replace('/', '.');
                    }
                    if ((id != -1) || recursive)
                    {
                        if (newname.endsWith(".class") && !entry.isDirectory())
                        {
                            String className = newname.substring(packageName.length() + 1, newname.length()
                                - CLASS_SUFFIX_LENGTH);
                            try
                            {
                                classes.add(Class.forName(packageName + '.' + className));
                            }
                            catch (ClassNotFoundException e)
                            {
                                LOG.error("", e);
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            LOG.error("", e);
        }
    }
    /**
     * 以文件的形式来获取包下的所有Class
     * @param packageName 包名
     * @param packagePath 包路径
     * @param recursive 是否循环迭代
     * @param classes Set<Class<?>> 符合条件的class集合
     */
    private static void findClassesInPackage(String packageName, String packagePath, final boolean recursive,
        Set<Class< ? >> classes)
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
                        classes.add(Class.forName(packageName + '.' + className));
                    }
                    catch (ClassNotFoundException e)
                    {
                        LOG.error("", e);
                    }
                }
            }
        } catch (IOException  e) {
            LOG.error("Invalid path found");
        } catch (SecurityException e) {
            LOG.error("Invalid path found");
        }

    }
    
    /**
     * 搜索bundle中所有符合条件的class
     * @param filepath bundle路径
     * @param packageName 包名
     * @param packageDirName 包目录
     * @param recursive 是否循环迭代
     * @param classes 符合条件的class存储集合
     */
    private static void findClassesInBundlesource(String filepath,String packageName,
        String packageDirName,boolean recursive,Set<Class< ? >> classes)
    {
        LOG.debug(filepath);
        String classPath = EntityScanner.class.getClassLoader().getResource("").getPath();
        String basePath = classPath.substring(0,classPath.indexOf("/etc/"));
        String path = basePath + "/lib/com.huawei.esight.sdk.oc/com.huawei.oc.cbb.sdk-2.1-SNAPSHOT.jar";

        JarFile jar = null;
        try
        {
            jar = new JarFile(path);
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements())
            {
                // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                // 如果是以/开头的,获取后面的字符串
                if (name.charAt(0) == '/')
                {
                    name = name.substring(1);
                }
                // 如果前半部分和定义的包名相同
                if (name.startsWith(packageDirName))
                {
                    int idx = name.lastIndexOf('/');
                    // 如果以"/"结尾 是一个包,获取包名 把"/"替换成"."
                    if (idx != -1)
                    {
                        packageName = name.substring(0, idx).replace('/', '.');
                    }
                    // 如果可以迭代下去 并且是一个包
                    if ((idx != -1) || recursive)
                    {
                        // 如果是一个.class文件 而且不是目录
                        if (name.endsWith(".class") && !entry.isDirectory())
                        {
                            // 去掉后面的".class" 获取真正的类名
                            String className = name.substring(packageName.length() + 1, 
                                name.length() - CLASS_SUFFIX_LENGTH);
                            try
                            {
                                classes.add(Class.forName(packageName + '.' + className));
                            }
                            catch (ClassNotFoundException e)
                            {
                                LOG.error("",e);
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            LOG.error("",e);
        }
        finally
        {
            if (null != jar)
            {
                try
                {
                    jar.close();
                }
                catch (IOException e)
                {
                    LOG.error("",e);
                }
            }
        }
    }
    
    
}
