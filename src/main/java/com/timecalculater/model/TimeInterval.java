package com.timecalculater.model;

import java.time.LocalTime;

public class TimeInterval {
    public LocalTime t1;
    public LocalTime t2;
    public TimeInterval(LocalTime t1,LocalTime t2)
    {
        this.t1=t1;
        this.t2=t2;
    }
    public float interception(TimeInterval interval)
    {
        if(interval==null)
        {
            return 0;
        }
        return -1;
    }
    public float complement(TimeInterval interval)
    {
        return -1;
    }
    public float toHour()
    {
        return -1;
    }
    public boolean startBefore(TimeInterval interval,int offset)
    {
        return false;
    }

    public boolean endAfter(TimeInterval interval,int offset)
    {
        return false;
    }

}
