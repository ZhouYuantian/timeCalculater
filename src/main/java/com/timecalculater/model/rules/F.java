package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;

import java.time.LocalDate;

public class F extends StandardRule{
    public F()
    {
        super(null,null);
    }

    @Override
    public float getNormalHour(AttendanceRecord record) {
        return record.slot1.toHour();
    }

    @Override
    public float getOTHour(AttendanceRecord record) {
        return 0;
    }

    @Override
    public int getUnusual(AttendanceRecord record) {
        return 0;
    }

    @Override
    public void setWorkHour(float normalHour, float otHour, WkHrStat wkHrStat, LocalDate date)
    {
        wkHrStat.weekdaysHours+=normalHour;
    }
}
