package com.mailang.log;

import ch.qos.logback.core.rolling.RollingFileAppender;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class RollingFileWithHeaderAppender<E> extends RollingFileAppender<E>
{
    private String fileHeader = null;
    @Override
    public void openFile(String fileName) throws IOException
    {
        super.openFile(fileName);
        File activeFile = new File(getFile());
        if (activeFile.exists() && activeFile.isFile() && activeFile.length() == 0)
        {
            lock.lock();
            try
            {
                String header = this.fileHeader + System.lineSeparator();
                FileUtils.writeByteArrayToFile(new File(getFile()), header.getBytes());
            }
            finally
            {
                lock.unlock();
            }
        }
    }

    public void setFileHeader(String fileHeader)
    {
        this.fileHeader = fileHeader;
    }

}
