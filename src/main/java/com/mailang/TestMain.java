package com.mailang;

import com.mailang.cons.XSCons;
import org.apache.commons.lang.CharSet;
import org.apache.commons.lang.math.NumberUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {

    private static SimpleDateFormat format = new SimpleDateFormat();
    private static String timeReg = "(?<=\\D)(\\d{14})(?=\\D)";

    private static String zoneReg = "ab";
    private static String testReg = "[0-5]:";

    public double findMedianSortedArrays(int[] nums1, int[]nums2)
    {
        //
       /* int m = nums1.length, n = nums2.length;
        if (m > n)
        {
            int[]temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }

        int iMin = 0, iMax = m, i = 0, j;
        while (iMin < iMax)
        {
            i = (iMin + iMax)/2;
            j = (m + n + 1)/2 + i;
            if (
                (i == 0 || )
                )
            {
                break;
            }
        }
*/


        return 0.0d;
    }


    public static void main(String[]args)
    {
        Set<String> set = new HashSet<>();

        zoneReg.length();
        int i=0, j = 1;
    }

    private static String getDTFromTM(String timeM) {
        try {
            Long formatTime = NumberUtils.toLong(timeM) * 1000;
            format.applyPattern("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(formatTime);
            return format.format(date);
        }
        catch (Exception e) {
            return timeM;
        }
    }
    public static List<String> getRexContent(String regexStr, String content){
        List<String> retList = new ArrayList<String>();

        try
        {
            Pattern pattern = Pattern.compile(regexStr);
            Matcher matcher = pattern.matcher(content);
            while (matcher.find())
            {
                retList.add(matcher.group());
            }
        }
        catch (Exception e)
        {
        }
        return retList;
    }
    public static boolean regexFind(String regexStr, String content){

        try
        {
            Pattern pattern = Pattern.compile(regexStr);
            Matcher matcher = pattern.matcher(content);
            return matcher.find();
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
