package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class E extends StandardRule{
    public E(){super(null,null);}

    @Override
    public int getUnusual(AttendanceRecord record) {
        return 0;
    }

    @Override
    public void setWorkHour(float normalHour, float otHour, WkHrStat wkHrStat, LocalDate date)
    {
        wkHrStat.weekendOvertimeHours+=otHour;
    }
}
