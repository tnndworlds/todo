package com.mailang.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * XS日志记录
 * @AUTHOR c00241496
 * @create 2017-11-28
 **/
public class XSLogger
{
    private Logger LOG;

    private XSLogger(Class clazz)
    {
        LOG = LoggerFactory.getLogger(clazz);
    }

    public static XSLogger getLogger(Class clazz)
    {
        return new XSLogger(clazz);
    }

    /**
     * 记录类型日志
     * @param recordType
     * @param datas
     */
    public void info(String recordType, List<String> datas)
    {
        //此处可判断datas大小//依据log.properties中header的大小//大小不符时，可能会影响前端日志分析
        String dataStr = datas.toString();
        XSRecord.getInstance().getLogger(recordType).info(dataStr.substring(1, dataStr.length() - 1));
    }

    public void debug(String format, String...args)
    {
        LOG.debug(format, args);
    }

    public void info(String format, String...args)
    {
        LOG.info(format, args);
    }

    public void error(String format, String...args)
    {
        LOG.error(format, args);
    }

    public void warn(String format, String...args)
    {
        LOG.warn(format, args);
    }

    public void xdebug(String format, String...args)
    {
        LOG.debug(format, args);
    }

    public void xinfo(String format, String...args)
    {
        LOG.info(format, args);
    }

    public void xerror(String format, String...args)
    {
        LOG.error(format, args);
    }

    public void xwarn(String format, String...args)
    {
        LOG.warn(format, args);
    }
}
