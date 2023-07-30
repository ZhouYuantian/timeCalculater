package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TimeInterval;

import java.time.LocalTime;

public class N extends StandardRule
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
        if(record.slot1==null)
        {
            return 2;
        }
        else
        {
            return 0;
        }
    }
}
