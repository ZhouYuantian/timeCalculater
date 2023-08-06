package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TimeInterval;
import com.timecalculater.model.WkHrStat;

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
        int unusual=super.getUnusual(record);
        if(!record.slot3.startBefore(reg_slot3,5))
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot3.t1)) unusual--;
            }
        }
        if(!record.slot3.endAfter(reg_slot3,5))
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot3.t2)) unusual--;
            }
        }
        return unusual;
    }

    @Override
    public void setAbsence(int unusual, float totalOffHour, WkHrStat wkHrStat) {
        if(totalOffHour>=4)
        {
            unusual-=3;
        }
        if(totalOffHour>=8)
        {
            unusual=0;
        }
        wkHrStat.absences+=unusual;
    }
}
