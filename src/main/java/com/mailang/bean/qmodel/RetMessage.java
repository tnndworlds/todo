package com.mailang.bean.qmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mailang.utils.JSONUtils;

public class RetMessage
{
    private int code;

    /**
     * 若为null，在jackson的toString时，不带该标签
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return JSONUtils.objToString(this);
    }
}
