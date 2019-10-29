package com.mailang.jdbc.persist;

import java.util.Date;

public class AutoIncreaseSequenceGenerator
{
    private static final int MAX_CONCURRENT = 100000;
    
    private static int count = 0;
    
    public static synchronized  String getSerialNum()
    {
        if (MAX_CONCURRENT > count)
        {
            count++;
        }
        else
        {
            count = 0;
        }
        String currentTime = Long.toString(new Date().getTime());
        return currentTime + count;
    }
    
}
