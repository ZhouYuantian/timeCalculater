package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TimeInterval;
import com.timecalculater.model.WkHrStat;

import java.time.LocalTime;

/**
  *@ClassName N
  *@Description 中山晚班班次，一轮打卡，下班时间按打卡时间算，无需申请加班
*/
public abstract class N extends StandardRule
{
    protected N(LocalTime t1, LocalTime t2)
    {
        super(new TimeInterval(t1,t2), null);
    }

    /**
     * @description 计算加班工时，此班次加班无需申请
     * @param record 员工当日考勤记录
     * @return 加班工时（H）
     **/
    @Override
    public float getOTHour(AttendanceRecord record)
    {
        if(!record.slot1.notComplete())
        {
            return record.slot1.complement(reg_slot1);
        }
        else
        {
            return 0;
        }
    }

    /**
     * @description 计算考勤异常次数，该班次不计迟到早退，计缺勤
     * @param record 员工当日考勤记录
     * @return 当日考勤异常次数
     **/
    @Override
    public int getUnusual(AttendanceRecord record)
    {
        int unusual=0;
        if(record.slot1.t1==null)
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot1.t1)) unusual--;
            }
        }
        if(record.slot1.t2==null)
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot1.t2)) unusual--;
            }
        }
        return unusual;
    }

    /**
     * @description 根据假时调整当日异常次数，并将当日异常次数计去员工当月记录
     * @param unusual 当日异常次数
     * @param totalOffHour 当日总假时
     * @param wkHrStat 员工当月记录
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
}
