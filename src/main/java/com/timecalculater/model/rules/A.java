package com.timecalculater.model.rules;


import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;

public abstract class A implements Rule{
    LocalTime t1,t2,t3,t4;
    A(LocalTime t1,LocalTime t2,LocalTime t3,LocalTime t4)
    {
        this.t1=t1;
        this.t2=t2;
        this.t3=t3;
        this.t4=t4;
    }
    //需要计算： 当天总时长，加班时长，是否考勤异常，
    @Override
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord record)
    {

        if(record.date.getDayOfWeek().get(DAY_OF_WEEK)<6)
        {//工作日

        }
        else
        {//休息日

        }
    }
}
