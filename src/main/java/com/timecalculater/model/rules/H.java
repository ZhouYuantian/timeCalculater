package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class H extends StandardRule{
    public H(){
        super(null,null);
    }

    @Override
    public float getNormalHour(AttendanceRecord record) {
        return 8;
    }

    @Override
    public float getSickHour(AttendanceRecord record) {
        return 0;
    }

    @Override
    public float getPaidHour(AttendanceRecord record) {
        return 0;
    }

    @Override
    public int getUnusual(AttendanceRecord record) {
        return 0;
    }

    @Override
    public void setWorkHour(float normalHour, float otHour, WkHrStat wkHrStat, LocalDate date) {
        wkHrStat.holidayHours=normalHour;
        wkHrStat.holidayOvertimeHours=otHour;
    }

    @Override
    public void setSubsidyDays(float totalWkHour, WkHrStat wkHrStat) {
        float realWkHour=totalWkHour-8;
        super.setSubsidyDays(realWkHour,wkHrStat);
    }
}
