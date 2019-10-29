package com.mailang.utils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

public class FreemarkerAdapter
{

    private static FreemarkerAdapter freemarkerAdapter = null;

    private Configuration cfg;

    private FreemarkerAdapter()
    {
        cfg = new Configuration(Configuration.VERSION_2_3_23);
    }

    public static FreemarkerAdapter getInstance()
    {
        if (freemarkerAdapter == null)
        {
            freemarkerAdapter = new FreemarkerAdapter();
        }
        return freemarkerAdapter;
    }

    /**
     * freemarker 字符串处理方法
     * @param content 模板
     * @param paramMap 数据模型
     * @return 替换结果
     */
    public synchronized String getResult(String content, Map<String, Object> paramMap)
    {
        if (StringUtils.isBlank(content) || null == paramMap || paramMap.isEmpty())
        {
            return content;
        }
        try
        {
            Iterator<String> iterator = paramMap.keySet().iterator();
            String key = null;
            while (iterator.hasNext())
            {
                key = String.valueOf(iterator.next());
                String tmpValue = String.valueOf(paramMap.get(key));
                if (null == tmpValue || "null".equals(tmpValue) || content.length() == 0)
                {
                    continue;
                }
                tmpValue.replaceAll("\n", "\\n").replaceAll("\r", "\\r");
                paramMap.put(key, tmpValue);
            }

            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate("myTmp", content);
            cfg.setTemplateLoader(stringLoader);
            Template template = cfg.getTemplate("myTmp", "UTF-8");
            StringWriter writer = new StringWriter();
            template.process(paramMap, writer);
            return writer.toString();
        }
        catch (Exception e)
        {
            return "";
        }
    }
}
