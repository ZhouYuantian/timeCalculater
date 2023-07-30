package com.timecalculater.model.rules;


import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TimeInterval;
import com.timecalculater.model.WkHrStat;
import com.timecalculater.utils.DateUtil;

import java.time.LocalDate;
import java.time.LocalTime;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;

public abstract class D extends StandardRule{

    protected D(LocalTime t1, LocalTime t2, LocalTime t3, LocalTime t4) {
        super(new TimeInterval(t1,t2), new TimeInterval(t3,t4));
    }

    @Override
    public void setWorkHour(float normalHour, float otHour, WkHrStat wkHrStat, LocalDate date)
    {
        ////////处理工作日工时，工作日加班，休息日加班，节假日加班
        if(DateUtil.holidaySet.contains(date))
        {//节假日
            wkHrStat.holidayHours+=normalHour;
            wkHrStat.holidayOvertimeHours+=otHour;
        }
        else
        {//工作日和周末
            wkHrStat.weekdaysHours+=normalHour;
            wkHrStat.weekdaysOvertimeHours+=otHour;
        }
    }
}
