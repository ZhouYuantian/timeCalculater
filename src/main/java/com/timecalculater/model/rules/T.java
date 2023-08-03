package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TripRecord;
import com.timecalculater.model.WkHrStat;
import com.timecalculater.service.TripService;
import com.timecalculater.utils.AlertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class T implements Rule{
    @Autowired
    TripService tripService;

    @Override
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord aRecord)
    {
        TripRecord tRecord=tripService.getRecordByNameDate(aRecord.name,aRecord.date);
        if(tRecord==null)
        {
            AlertUtil.warning("无法找到出差记录:"+aRecord.name+" "+aRecord.date);
            throw new RuntimeException();
        }

        if(tRecord.date.getDayOfWeek().getValue()>5)
        {//休息日
            wkHrStat.weekendOvertimeHours+= tRecord.workHour>8? tRecord.workHour : 8;
        }
        else
        {//工作日
            wkHrStat.weekdaysHours+=8;
            wkHrStat.weekdaysOvertimeHours+=tRecord.workHour>8? tRecord.workHour-8 : 0;
        }
        //计津贴日数
        if(tRecord.workHour>=11)
        {
            wkHrStat.daysGT11H++;
        }
        else
        {
            wkHrStat.daysGT5H++;
        }
    }
}
