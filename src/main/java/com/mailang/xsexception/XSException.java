package com.mailang.xsexception;

import com.mailang.i18n.I18N;

public class XSException extends RuntimeException
{
    private String errCode;

    private Object[]msgParam;

    public XSException(String errCode)
    {
        this.errCode = errCode;
    }

    public XSException(String errCode, Object...msgParam)
    {
        this.msgParam = msgParam;
    }

    @Override
    public String getMessage()
    {
        return I18N.getXSERRString(errCode, this.msgParam);
    }

    public String getErrCode()
    {
        return this.errCode;
    }
}
