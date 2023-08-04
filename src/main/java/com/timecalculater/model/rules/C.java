package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TimeInterval;

import java.time.LocalTime;

public abstract class C extends B{
    TimeInterval reg_slot3;
    protected C(LocalTime t1,LocalTime t2,LocalTime t3,LocalTime t4,LocalTime t5,LocalTime t6)
    {
        super(t1,t2);
        reg_slot2=new TimeInterval(t3,t4);
        reg_slot3=new TimeInterval(t5,t6);
    }

    @Override
    public float getNormalHour(AttendanceRecord record) {
        float normalHour= super.getNormalHour(record);
        normalHour+=reg_slot3.interception(record.slot3);
        return normalHour;
    }

    @Override
    public float getAfter20WkHr(AttendanceRecord record) {
        TimeInterval F12to20=new TimeInterval(LocalTime.of(12,0),LocalTime.of(20,0));
        return record.slot1.complement(F12to20)+record.slot2.complement(F12to20)+record.slot3.complement(F12to20);
    }

    @Override
    public int getUnusual(AttendanceRecord record) {
        int unusual=0;
        if(!record.slot1.startBefore(reg_slot1,5))
        {
            unusual++;
        }
        if(!record.slot1.endAfter(reg_slot1,5))
        {
            unusual++;
        }
        if(!record.slot2.startBefore(reg_slot2,5))
        {
            unusual++;
        }
        if(!record.slot2.endAfter(reg_slot2,5))
        {
            unusual++;
        }
        if(!record.slot3.startBefore(reg_slot3,5))
        {
            unusual++;
        }
        if(!record.slot3.endAfter(reg_slot3,5))
        {
            unusual++;
        }

        if (record.offSummary.getTotalOffDays()>0)
        {
            unusual-=2;
        }
        if (record.offSummary.getTotalOffDays()>0.3)
        {
            unusual-=2;
        }
        if(record.offSummary.getTotalOffDays()>0.6)
        {
            unusual=0;
        }

        return unusual<0?0:unusual;
    }


}
