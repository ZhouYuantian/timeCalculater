package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;
import org.springframework.stereotype.Component;

/**
  *@ClassName X
  *@Description 休息班次，无上班，无需对记录进行任何处理
*/
@Component
public class X implements Rule{

    @Override
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord record) {

    }

}
