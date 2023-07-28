package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;

import java.time.LocalTime;

public class A1 extends A{
    static LocalTime t1=LocalTime.of(8,0);
    static LocalTime t2=LocalTime.of(12,30);
    static LocalTime t3=LocalTime.of(13,30);
    static LocalTime t4=LocalTime.of(17,0);

    A1() {
        super(t1, t2, t3, t4);
    }

    @Override
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord record) {
        super.processAttendance(wkHrStat,record);

    }
}
