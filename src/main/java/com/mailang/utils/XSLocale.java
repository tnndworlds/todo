package com.mailang.utils;

import com.mailang.cons.XSCons;
import org.apache.commons.lang.StringUtils;

import java.util.Locale;

public class XSLocale
{
    private Locale locale;

    private static XSLocale instance = null;

    private XSLocale()
    {
        String language = PROP.getSYSPropValue(XSCons.PROP_KEY_LANGUAGE);
        if (StringUtils.isNotBlank(language))
        {
            locale = new Locale(language.split("_")[0], language.split("_")[1]);
        }
        else
        {
            locale = Locale.getDefault();
        }
    }

    public static XSLocale getInstance()
    {
        if (null == instance)
        {
            instance = new XSLocale();
        }
        return instance;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public String toString()
    {
        return locale.toString();
    }
}
