package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TimeInterval;
import com.timecalculater.model.WkHrStat;

import java.time.LocalTime;

public abstract class N extends StandardRule
{
    protected N(LocalTime t1, LocalTime t2)
    {
        super(new TimeInterval(t1,t2), null);
    }

    @Override
    public float getOTHour(AttendanceRecord record)
    {
        if(record.slot1!=null)
        {
            return record.slot1.complement(reg_slot1);
        }
        else
        {
            return 0;
        }
    }

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
