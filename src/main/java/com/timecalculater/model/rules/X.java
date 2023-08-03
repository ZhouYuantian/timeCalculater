package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;
import org.springframework.stereotype.Component;

//X代表休息
@Component
public class X implements Rule{

    @Override
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord record) {

    }

}
