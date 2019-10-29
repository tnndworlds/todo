package com.mailang.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import com.mailang.cons.XSCons;
import com.mailang.utils.PROP;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * log.properties
 *
 * @AUTHOR c00241496
 * @create 2017-11-28
 **/
public class XSRecord
{
    private Map<String, Logger> loggerMap = new HashMap<String, Logger>();
    private Properties logProp = PROP.getProp(XSCons.PROP_LOG);
    private static XSRecord xsRecord = null;

    private XSRecord()
    {
        String[] recordTypes = logProp.getProperty("Record.type").split(XSCons.COMMA);
        for (String recordType : recordTypes)
        {
            createRecordLogger(recordType);
        }
    }

    private void createRecordLogger(String recordType)
    {
        if (null != this.loggerMap.get(recordType))
        {
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(logProp.getProperty("Record.dirctory")).append(recordType).append(File.separator).append(recordType);

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        RollingFileWithHeaderAppender<ILoggingEvent> rollingFileAppender = new RollingFileWithHeaderAppender<>();
        rollingFileAppender.setFileHeader(logProp.getProperty(recordType + ".header"));
        rollingFileAppender.setContext(loggerContext);
        rollingFileAppender.setName(recordType);
        rollingFileAppender.setFile(sb.toString() + ".csv");
        rollingFileAppender.setAppend(true);

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern(logProp.getProperty("Record.pattern"));
        encoder.start();

        TimeBasedRollingPolicy timeBasedRollingPolicy = new TimeBasedRollingPolicy();
        timeBasedRollingPolicy.setParent(rollingFileAppender);
        timeBasedRollingPolicy.setContext(loggerContext);
        timeBasedRollingPolicy.setMaxHistory(Integer.parseInt(logProp.getProperty("Record.maxHistory")));
        timeBasedRollingPolicy.setTotalSizeCap(new FileSize(Long.parseLong(logProp.getProperty("Record.totalSizeCap"))));
        timeBasedRollingPolicy.setFileNamePattern(sb.toString() + logProp.getProperty("Record.history.suffix"));
        timeBasedRollingPolicy.start();

        rollingFileAppender.setRollingPolicy(timeBasedRollingPolicy);
        rollingFileAppender.setEncoder(encoder);
        rollingFileAppender.start();

        Logger logbackLogger = loggerContext.getLogger(recordType);
        logbackLogger.addAppender(rollingFileAppender);
        logbackLogger.setLevel(Level.INFO);
        logbackLogger.setAdditive(false);

        loggerMap.put(recordType, logbackLogger);
    }

    public static XSRecord getInstance()
    {
        if (null == xsRecord)
        {
            xsRecord = new XSRecord();
        }
        return xsRecord;
    }

    public Logger getLogger(String recordType)
    {
        return loggerMap.get(recordType);
    }


}
