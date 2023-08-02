package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;
import org.springframework.stereotype.Component;

@Component
public class T implements Rule{


    @Override
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord record) {

    }
}
