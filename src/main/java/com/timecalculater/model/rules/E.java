package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
  *@ClassName E
  *@Description 休息日上班规则，无固定上下班时间，不计考勤异常，所有工时计入休息日加班工时
*/
@Component
public class E extends StandardRule{
    public E(){super(null,null);}

    /**
     * @description 计算考勤异常次数，该班次不计考勤异常，因此返回0
     * @param record 员工当日考勤记录
     * @return 员工当日考勤异常次数
     **/
    @Override
    public int getUnusual(AttendanceRecord record) {
        return 0;
    }

    /**
     * @description  将员工当日工时更新到当月记录，所有工时都计为 休息日加班工时
     * @param normalHour 正常工时
     * @param otHour 加班工时
     * @param wkHrStat 当月记录
     * @param date 当日日期
     * @return
     **/
    @Override
    public void setWorkHour(float normalHour, float otHour, WkHrStat wkHrStat, LocalDate date)
    {
        wkHrStat.weekendOvertimeHours+=otHour;
    }
}
