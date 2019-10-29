package com.mailang.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author chengsongsong
 * @Date 2019/9/29
 */
@Component
@ConfigurationProperties(prefix="message")
@PropertySource("classpath:file-message.properties")
public class MessageProperties
{
    private long fileSize;  //压缩大小

    private double scaleRatio; //压缩比例

    private String winUpPath; //保存路径

    private String linuxUpPath; // Linux保存路径

    private String imageType; //图片类型

    public long getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(long fileSize)
    {
        this.fileSize = fileSize;
    }

    public double getScaleRatio()
    {
        return scaleRatio;
    }

    public void setScaleRatio(double scaleRatio)
    {
        this.scaleRatio = scaleRatio;
    }

    public String getUpPath()
    {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {//windows环境
            return winUpPath;
        }else{
            return linuxUpPath;
        }
    }

    public String getWinUpPath() {
        return winUpPath;
    }

    public void setWinUpPath(String winUpPath) {
        this.winUpPath = winUpPath;
    }

    public String getLinuxUpPath() {
        return linuxUpPath;
    }

    public void setLinuxUpPath(String linuxUpPath) {
        this.linuxUpPath = linuxUpPath;
    }

    public String getImageType()
    {
        return imageType;
    }

    public void setImageType(String imageType)
    {
        this.imageType = imageType;
    }
}
