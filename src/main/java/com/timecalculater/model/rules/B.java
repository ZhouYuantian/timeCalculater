package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TimeInterval;
import com.timecalculater.model.WkHrStat;

import java.time.LocalTime;

public abstract class B extends StandardRule{
    protected B(LocalTime t1, LocalTime t2)
    {
        super(new TimeInterval(t1,t2), null);
    }

    public float getAfter20WkHr(AttendanceRecord record)
    {
        TimeInterval F12to20=new TimeInterval(LocalTime.of(12,0),LocalTime.of(20,0));
        return record.slot1.complement(F12to20);
    }

    public void setAfter20Subsidy(float after20WkHr, WkHrStat wkHrStat)
    {
        wkHrStat.after20Subsidy=after20WkHr;
    }

    @Override
    public int getUnusual(AttendanceRecord record)
    {
        if (record.offSummary.getPaidLeaveDays()>0)
        {//有带薪假,不计异常
            return 0;
        }
        else
        {
            int unusual=0;
            if(record.slot1==null || !record.slot1.startBefore(reg_slot1,5))
            {
                unusual++;
            }
            if(record.slot1==null || !record.slot1.endAfter(reg_slot1,5))
            {
                unusual++;
            }
            return unusual;
        }
    }

    @Override
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord record) {
        float after20WkHr=getAfter20WkHr(record);
        setAfter20Subsidy(after20WkHr,wkHrStat);
        super.processAttendance(wkHrStat, record);
    }
}
