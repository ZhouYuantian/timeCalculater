package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;

public class T extends Rule{

    @Override
    public float getNormalHour(AttendanceRecord record) {
        return 0;
    }

    @Override
    public float getOTHour(AttendanceRecord record) {
        return 0;
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
    public int getAbsence(AttendanceRecord record) {
        return 0;
    }
}
