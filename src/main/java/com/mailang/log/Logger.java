package com.mailang.log;

import com.mailang.utils.Utils;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @Author c00241496
 * @Version [OSS Dashboard v1.0]
 * @Date 2019/1/28
 */
public class Logger
{
    private final static String INFO = "Info";

    private final static String WARN = "Warn";

    private final static String DEBUG = "Debug";

    private final static String ERROR = "Error";

    //private static SystemLogDao systemLogDao = SpringUtils.getBeanByClass(SystemLogDao.class);

    private org.slf4j.Logger LOG;

    private Logger(Class clazz)
    {
        LOG = LoggerFactory.getLogger(clazz);
    }

    public static Logger getLogger(Class clazz)
    {
        return new Logger(clazz);
    }

    public void debug(String format, Object ...args)
    {
        LOG.debug(MessageFormat.format(format, args));
        if (args != null && args[args.length - 1] instanceof Exception)
        {
            Exception e = (Exception)args[args.length - 1];
            LOG.debug(Utils.getStackTrace(e));
        }
    }

    public void info(String format, Object... args)
    {
        LOG.info(MessageFormat.format(format, args));
    }

    public void error(String format, Object... args)
    {
        LOG.error(MessageFormat.format(format, args));
        if (args != null && args[args.length - 1] instanceof Exception)
        {
            Exception e = (Exception)args[args.length - 1];
            LOG.debug(Utils.getStackTrace(e));
        }
    }

    public void warn(String format, Object... args)
    {
        LOG.warn(MessageFormat.format(format, args));
    }

    public void xdebug(String format, Object... args)
    {
       // systemLogDao.save(DEBUG, MessageFormat.format(format, args));
        this.debug(format, args);
    }

    public void xinfo(String format, Object... args)
    {
        this.info(format, args);
    }

    public void xerror(String format, Object... args)
    {
        this.error(format, args);
    }

    public void xwarn(String format, Object... args)
    {
        this.warn(format, args);
    }
}
