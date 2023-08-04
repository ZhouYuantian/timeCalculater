package com.timecalculater.model;

import java.time.Duration;
import java.time.LocalTime;

public class TimeInterval {
    public LocalTime t1;
    public LocalTime t2;
    public TimeInterval(LocalTime t1,LocalTime t2)
    {
        this.t1=t1;
        this.t2=t2;
    }
    public boolean notComplete()
    {
        if(t1==null||t2==null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public float interception(TimeInterval interval)
    {
        if(this.notComplete()||interval.notComplete()) return 0;

        int t1s=t1.toSecondOfDay();
        int t2s=t1.isBefore(t2)?t2.toSecondOfDay():t2.toSecondOfDay()+86400; //跨日补时

        int s1s=interval.t1.toSecondOfDay();
        int s2s=interval.t1.isBefore(interval.t2)?interval.t2.toSecondOfDay():interval.t2.toSecondOfDay()+86400;

        int diff=Math.min(t2s,s2s)-Math.max(t1s,s1s);
        return diff>0?(float) diff/3600:0;
    }
    public float complement(TimeInterval interval)
    {
        return this.toHour()-this.interception(interval);
    }
    public float toHour()
    {
        if(this.notComplete()) return 0;
        float duration=(float) Duration.between(t1,t2).getSeconds()/3600;
        return duration>0?duration:duration+24;
    }
    public boolean startBefore(TimeInterval interval,int offset)
    {
        if(t1==null) return false;
        return t1.isBefore(interval.t1.plusMinutes(offset+1));
    }

    public boolean endAfter(TimeInterval interval,int offset)
    {
        if(t2==null) return false;
        return t2.isAfter(interval.t2.minusMinutes(offset+1));
    }

}
