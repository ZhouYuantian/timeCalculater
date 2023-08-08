package com.timecalculater.utils;


import com.timecalculater.model.TimeInterval;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    public static List<TimeInterval> getApplications(String rawString,String keyWord)
    {
        if(rawString.equals("--")) return new ArrayList<>();
        DateTimeFormatter format=DateTimeFormatter.ofPattern("HH:mm");
        String[] timeStrLst=rawString.split("、");
        List<TimeInterval> timeLst=new ArrayList<>();

        for(String timeStr:timeStrLst)
        {
            if(timeStr.contains(keyWord))
            {
                String startStr=timeStr.split(" ")[1];
                String endStr=timeStr.split(" ")[4];
                LocalTime start=LocalTime.parse(startStr,format);
                LocalTime end=LocalTime.parse(endStr,format);
                timeLst.add(new TimeInterval(start,end));
            }
        }

        return timeLst;
    }

    public static List<TimeInterval> getOtApplications(String rawString)
    {
        return getApplications(rawString,"加班");
    }
    public static List<TimeInterval> getBlApplications(String rawString)
    {
        return getApplications(rawString,"事假");
    }
    public static LocalDate getDate(String str)
    {
        String dateStr= StringUtils.left(str,10);
        LocalDate date;
        if(StringUtils.isNumeric(str))
        {
            ZoneId zid=ZoneId.systemDefault();
            date=HSSFDateUtil.getJavaDate(Double.valueOf(str)).toInstant().atZone(zid).toLocalDate();
        }
        else
        {
            DateTimeFormatter format= DateTimeFormatter.ofPattern("yyyy/M/d");
            date=LocalDate.parse(dateStr,format);
        }
        return date;
    }

    public static LocalTime getTime(String str)
    {
        if(str.equals("--")||str.equals("未打卡")) return null;
        str=str.replaceAll("次日","");
        DateTimeFormatter format;
        if(str.length()>5)
        {
            format=DateTimeFormatter.ofPattern("H:mm:ss");
        }
        else
        {
            format=DateTimeFormatter.ofPattern("HH:mm");
        }
        LocalTime time=LocalTime.parse(str,format);
        return time;
    }

}
