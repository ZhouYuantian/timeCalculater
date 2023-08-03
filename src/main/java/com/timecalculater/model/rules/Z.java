package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TimeInterval;

import java.time.LocalTime;

public abstract class Z extends StandardRule{

    protected Z(LocalTime t1, LocalTime t2, LocalTime t3, LocalTime t4)
    {
        super(new TimeInterval(t1,t2), new TimeInterval(t3,t4));
    }

    @Override
    public float getOTHour(AttendanceRecord record)
    {//17:30到18:00加班无需申请，直接计入加班工时
        TimeInterval F630to7=new TimeInterval(LocalTime.of(17,30),LocalTime.of(18,0));
        return F630to7.interception(record.slotX)+super.getOTHour(record);
    }

    @Override
    public int getUnusual(AttendanceRecord record)
    {
        int unusual=0;
        if(record.slot1==null || !record.slot1.t1.isBefore(LocalTime.of(10,0)))
        {
            unusual++;
        }
        if(record.slot1==null || !record.slot1.endAfter(reg_slot1,5))
        {
            unusual++;
        }
        if(record.slot2==null || !record.slot2.startBefore(reg_slot2,5))
        {
            unusual++;
        }
        if(record.slot2==null || !record.slot2.endAfter(reg_slot2,5))
        {
            unusual++;
        }

        if (record.offSummary.getTotalOffDays()>0)
        {//有请假但是小于等于半天，只计半天考勤异常
            unusual-=2;
        }
        if(record.offSummary.getTotalOffDays()>0.5)
        {//有请薪假多于半天，不计考勤异常
            unusual=0;
        }

        return unusual<0?0:unusual;
    }
}
