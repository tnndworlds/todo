package com.mailang.i18n;

import com.mailang.utils.XSLocale;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class I18N
{
    private static ResourceBundle xsResourceBundle;

    private static ResourceBundle xsUIResourceBundle;

    private static ResourceBundle xsERRResourceBundle;

    static
    {
        try
        {
            String path = "i18n/";
            xsResourceBundle = ResourceBundle.getBundle(path + I18NCons.XS, XSLocale.getInstance().getLocale());
            xsUIResourceBundle = ResourceBundle.getBundle(path + I18NCons.XS_UI, XSLocale.getInstance().getLocale());
            xsERRResourceBundle = ResourceBundle.getBundle(path + I18NCons.XS_ERR, XSLocale.getInstance().getLocale());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static String getXSString(String key, Object...params)
    {
        return MessageFormat.format(xsResourceBundle.getString(key), params);
    }

    public static String getXSUIString(String key, Object...params)
    {
        return MessageFormat.format(xsUIResourceBundle.getString(key), params);
    }

    public static String getXSERRString(String key, Object...params)
    {
        return MessageFormat.format(xsERRResourceBundle.getString(key), params);
    }
}
