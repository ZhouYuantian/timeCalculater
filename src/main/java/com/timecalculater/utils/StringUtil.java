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

    public static List<TimeInterval> getOtApplications(String rawString)
    {
        if(rawString.equals("--")) return new ArrayList<>();
        DateTimeFormatter format=DateTimeFormatter.ofPattern("HH:mm");
        String[] otStrLst=rawString.split("、");
        List<TimeInterval> otList=new ArrayList<>();

        for(String otStr:otStrLst)
        {
            if(otStr.contains("加班"))
            {
                String startStr=otStr.split(" ")[1];
                String endStr=otStr.split(" ")[4];
                LocalTime start=LocalTime.parse(startStr,format);
                LocalTime end=LocalTime.parse(endStr,format);
                otList.add(new TimeInterval(start,end));
            }
        }

        return otList;
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
        DateTimeFormatter format=DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time=LocalTime.parse(str,format);
        return time;
    }

}
