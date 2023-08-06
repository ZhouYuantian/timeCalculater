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
        return F630to7.interception(record.slot2)+super.getOTHour(record);
    }

    @Override
    public int getUnusual(AttendanceRecord record)
    {
        int unusual=0;
        if(record.offSummary.bLeave>0) unusual++;   //有事假，记一次异常
        if(record.slot1.t1==null||!record.slot1.t1.isBefore(LocalTime.of(10,0)))
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot1.t1)) unusual--;
            }
        }
        if(!record.slot1.endAfter(reg_slot1,5))
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot1.t2)) unusual--;
            }
        }
        if(!record.slot2.startBefore(reg_slot2,5))
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot2.t1)) unusual--;
            }
        }
        if(!record.slot2.endAfter(reg_slot2,5))
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot2.t2)) unusual--;
            }
        }

        return unusual;
    }
}
