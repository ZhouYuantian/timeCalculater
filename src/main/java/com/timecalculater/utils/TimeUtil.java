package com.timecalculater.utils;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

public class TimeUtil {
    public static LocalTime earliestOf(LocalTime... times)
    {
        for(LocalTime time:times)
        {
            if(time!=null) return time;
        }
        return null;
    }

    public static LocalTime latestOf(LocalTime... times)
    {
        Collections.reverse(Arrays.asList(times));
        for(LocalTime time:times)
        {
            if(time!=null) return time;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(latestOf(LocalTime.NOON,LocalTime.MAX));
    }
}
