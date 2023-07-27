package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;

import java.time.LocalTime;

public class A2 extends A implements Rule{
    static LocalTime t1;
    static LocalTime t2;
    static LocalTime t3;
    static LocalTime t4;

    A2() {
        super(t1, t2, t3, t4);
    }

    @Override
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord record) {
        super.processAttendance(wkHrStat,record);
    }
}
