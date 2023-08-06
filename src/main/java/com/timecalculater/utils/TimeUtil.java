package com.timecalculater.utils;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TimeUtil {
    public static LocalTime earliestOf(LocalTime... times)
    {
        List<LocalTime> timeList= Arrays.stream(times).filter(t->t!=null).toList();
        if(timeList.isEmpty()) return null;
        return Collections.min(timeList);
    }

    public static LocalTime latestOf(LocalTime... times)
    {
        List<LocalTime> timeList= Arrays.stream(times).filter(t->t!=null).toList();
        if(timeList.isEmpty()) return null;
        return Collections.max(timeList);
    }

    public static void main(String[] args) {
        System.out.println(earliestOf());
    }
}
