package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TimeInterval;
import com.timecalculater.model.WkHrStat;

import java.time.LocalTime;

/**
  *@ClassName B
  *@Description B类规则，只有一轮上下班打卡，需计算20点后夜班补贴
*/
public abstract class B extends StandardRule{
    protected B(LocalTime t1, LocalTime t2)
    {
        super(new TimeInterval(t1,t2), null);
    }

    /**
     * @description 计算20点后夜班工时
     * @param record 员工当日考勤记录
     * @return 20点后工时（H）
     **/
    public float getAfter20WkHr(AttendanceRecord record)
    {
        TimeInterval F12to20=new TimeInterval(LocalTime.of(12,0),LocalTime.of(20,0));
        return record.slot1.complement(F12to20);
    }

    /**
     * @description 将当日20点后工时加去员工当月的记录
     * @param after20WkHr 20点后工时
     * @param wkHrStat  员工当月记录
     * @return
     **/
    public void setAfter20Subsidy(float after20WkHr, WkHrStat wkHrStat)
    {
        wkHrStat.after20Subsidy+=after20WkHr;
    }

    /**
     * @description 将当日异常次数加去员工当月记录，因为B类只有一轮打卡，所以异常计算和标准规则不同需要重写
     * @param unusual 当日异常次数
     * @param totalOffHour 当日总假时（不包括事假）
     * @param wkHrStat 某员工当月记录
     * @return
     **/
    @Override
    public void setAbsence(int unusual, float totalOffHour, WkHrStat wkHrStat) {
        if(totalOffHour>=4)
        {
            unusual-=1;
        }
        if(totalOffHour>=8)
        {
            unusual=0;
        }
        wkHrStat.absences+=unusual;
    }

    /**
     * @description 处理考勤记录，在标准规则的基础上，计算20点后夜班补贴
     * @param wkHrStat 员工当月记录
     * @param record 员工当日考勤记录
     * @return
     **/
    @Override
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord record) {
        float after20WkHr=getAfter20WkHr(record);
        setAfter20Subsidy(after20WkHr,wkHrStat);
        super.processAttendance(wkHrStat, record);
    }
}
