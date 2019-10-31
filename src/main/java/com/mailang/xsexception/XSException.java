package com.mailang.xsexception;

public class XSException extends RuntimeException
{
    private int code;

    private String msg;

    public XSException(int code)
    {
        this.code = code;
    }

    public XSException(String msg, int code)
    {
        this.code = code;
        this.msg = msg;
    }

    public int getCode()
    {
        return code;
    }

    @Override
    public String getMessage()
    {
        return this.msg;
    }
}
