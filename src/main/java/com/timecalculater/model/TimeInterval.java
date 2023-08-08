package com.timecalculater.model;


import java.time.Duration;
import java.time.LocalTime;

/**
  *@ClassName TimeInterval
  *@Description 该类封装了两个时间对象，用于表示一段时间区间，并提供了几个常用的区间运算方法
*/
public class TimeInterval {
    public LocalTime t1;
    public LocalTime t2;
    public TimeInterval(LocalTime t1,LocalTime t2)
    {
        this.t1=t1;
        this.t2=t2;
    }

    /**
     * @description 判断当前类是否完整，两个时间属性均非NULL
     * @param
     * @return true 完整 false 不完整
     **/
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

    /**
     * @description 求当前时间段和另一个时间段的交集时间
     * @param interval 另一个时间段
     * @return 交集的小时数 (H)
     **/
    public float interception(TimeInterval interval)
    {
        if(this.notComplete()||interval.notComplete()) return 0;

        int t1s=t1.toSecondOfDay();
        int t2s=t2.toSecondOfDay();
        if(t2s<t1s) t2s+=86400;  //跨日补时

        int s1s=interval.t1.toSecondOfDay();
        int s2s=interval.t2.toSecondOfDay();
        if(s2s<s1s) s2s+=86400;

        float diff=Math.min(t2s,s2s)-Math.max(t1s,s1s);
        return diff>0?(diff/3600):0;
    }

    /**
     * @description 求当前时间段对令一个时间段取反后的时间。eg. (9:00~11:00).complement(10:00~11:00)=1（H）
     * @param interval 另一个时间段
     * @return 取反后的小时数（H）
     **/
    public float complement(TimeInterval interval)
    {
        return this.toHour()-this.interception(interval);
    }

    /**
     * @description 求当前时间段之间的小时数 eg.（t2-t1)
     * @param
     * @return 小时数（H）
     **/
    public float toHour()
    {
        if(this.notComplete()) return 0;
        float duration=(float) Duration.between(t1,t2).getSeconds()/3600;
        return duration>0?duration:duration+24;
    }

    /**
     * @description 判断当前时间段是否在目标时间段开始之前开始（可用于判断是否迟到）
     * @param interval 目标时间段
     * @param offset 容错值（分钟），用于容错，eg. 当容错值为5时，9：05 startBefore 9:00 返回true(迟到5分钟不算迟到）
     * @return true 目标为空或当前时间段在目标时间段（包括）开始之前开始, false 当前t1为空或当前时间段在目标时间段开始之后开始
     **/
    public boolean startBefore(TimeInterval interval,int offset)
    {
        if(interval==null) return true;
        if(t1==null) return false;
        LocalTime legalT1=interval.t1.minusHours(12);
        LocalTime legalT2=interval.t1.plusMinutes(offset);
        TimeInterval legalInterval=new TimeInterval(legalT1,legalT2);
        return legalInterval.contains(t1);
    }

    /**
     * @description 判断当前时间段是否在目标时间段结束之后结束（可用于判断是否早退）
     * @param interval 目标时间段
     * @param offset 容错值（分钟），用于容错，eg. 当容错值为5时，8：55 endAfter 9:00 返回true(早退5分钟不算早退）
     * @return true 目标为空或当前时间段在目标时间段（包括）结束之后结束, false 当前t2为空或当前时间段在目标时间段结束之前结束
     **/
    public boolean endAfter(TimeInterval interval,int offset)
    {
        if(interval==null) return true;
        if(t2==null) return false;
        LocalTime legalT1=interval.t2.minusMinutes(offset);
        LocalTime legalT2=interval.t2.plusHours(12);
        TimeInterval legalInterval=new TimeInterval(legalT1,legalT2);
        return legalInterval.contains(t2);
    }

    /**
     * @description 判断目标时间点是否在当前时间段之内
     * @param t 目标时间点
     * @return true t在t1,t2之间, false t不在t1,t2之间
     **/
    public boolean contains(LocalTime t)
    {
        LocalTime t1=this.t1.minusMinutes(1);
        LocalTime t2=this.t2.plusMinutes(1);
        if(t1.isBefore(t2))
        {
            return t1.isBefore(t)&&t2.isAfter(t);
        }
        else
        {
            return t1.isBefore(t)||t2.isAfter(t);
        }
    }
}