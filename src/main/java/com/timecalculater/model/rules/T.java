package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TripRecord;
import com.timecalculater.model.WkHrStat;
import com.timecalculater.service.TripService;
import com.timecalculater.utils.AlertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
  *@ClassName T
  *@Description 出差班次，直接从出差表里读数据，无需计算。因此不继承(改写)标准规则模板，直接实现Rule接口
*/
@Component
public class T implements Rule{
    @Autowired
    TripService tripService;

    /**
     * @description 从出差信息表获取该员工当日的出差信息，并更新到该员工当月记录
     * @param wkHrStat 当月记录
     * @param aRecord 当日考勤记录
     * @return
     **/
    @Override
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord aRecord)
    {
        TripRecord tRecord=tripService.getRecordByNameDate(aRecord.name,aRecord.date);
        if(tRecord==null)
        {
            AlertUtil.error("无法找到出差记录:"+aRecord.name+" "+aRecord.date);
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
