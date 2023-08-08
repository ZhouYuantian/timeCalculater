package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
  *@ClassName H
  *@Description 法定节假日班次，无固定上班时间。不计异常，不计假时。直接计8小时正常工时，额外加班需申请并计入法定节假日加班工时。
*/
@Component
public class H extends StandardRule{
    public H(){
        super(null,null);
    }

    /**
     * @description 计算正常工时，法定节假日直接返回8
     * @param record 当日考勤记录
     * @return 当日正常工时（H）
     **/
    @Override
    public float getNormalHour(AttendanceRecord record) {
        return 8;
    }

    /**
     * @description 计算病假工时，法定节假日不计假时，直接返回0
     * @param record 员工当日考勤记录
     * @return 病假工时
     **/
    @Override
    public float getSickHour(AttendanceRecord record) {
        return 0;
    }

    /**
     * @description 计算带薪假工时，法定节假日不计假时，直接返回0
     * @param record 员工当日考勤记录
     * @return 带薪假工时
     **/
    @Override
    public float getPaidHour(AttendanceRecord record) {
        return 0;
    }

    /**
     * @description 计算考勤异常次数，法定节假日不计异常，直接返回0
     * @param record 员工当日考勤记录
     * @return 考勤异常次数
     **/
    @Override
    public int getUnusual(AttendanceRecord record) {
        return 0;
    }

    /**
     * @description 将正常工时更新到员工当月的记录
     * @param normalHour 当日正常工时
     * @param otHour 当日加班工时
     * @param wkHrStat 员工当月记录
     * @param date 当日日期
     * @return
     **/
    @Override
    public void setWorkHour(float normalHour, float otHour, WkHrStat wkHrStat, LocalDate date) {
        wkHrStat.holidayHours=normalHour;
        wkHrStat.holidayOvertimeHours=otHour;
    }

    /**
     * @description 添加 3-5小时天数，5小时天数，11小时天数 到员工当月记录，法定节假日送的8小时不计
     * @param totalWkHour 某位员工当日总工时
     * @param wkHrStat 某位员工当月记录
     * @return
     **/
    @Override
    public void setSubsidyDays(float totalWkHour, WkHrStat wkHrStat) {
        float realWkHour=totalWkHour-8;
        super.setSubsidyDays(realWkHour,wkHrStat);
    }
}
