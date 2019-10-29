package com.mailang.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @Author
 * @Date 2019/6/14
 */
public class DateTime
{
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String getCurrentTime()
    {
        return simpleDateFormat.format(new Date());
    }

    public static boolean isToday(String dtStr)
    {
        return dateFormat.format(new Date()).equals(dtStr.trim());
    }

    public static int todayOfWeek()
    {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }


    public static void main(String[]args)
    {
        System.out.println(todayOfWeek());
    }
}
