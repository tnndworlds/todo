package com.mailang.taskmgt.bean;

public enum StatusEnum
{
    running(1),
    stoped(0),
    success(1),
    failed(0);
    private int status;
    private StatusEnum(int status)
    {
        this.status = status;
    }

    public int getStatus()
    {
        return this.status;
    }
}
