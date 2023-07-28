package com.timecalculater.model.rules;


import com.timecalculater.model.AttendanceRecord;
import java.time.Duration;
import java.time.LocalTime;


public abstract class A extends Rule{
    LocalTime t1,t2,t3,t4;
    A(LocalTime t1,LocalTime t2,LocalTime t3,LocalTime t4)
    {
        this.t1=t1;
        this.t2=t2;
        this.t3=t3;
        this.t4=t4;
    }

    @Override
    public float getNormalHour(AttendanceRecord record)
    {
        int normalHour=0;
        //计算前两次打卡之间的工时
        if(record.t1!=null)
        {
            if(Duration.between(t1,record.t1).toMinutes()<5)
            {//无迟到
                if(Duration.between(t2,record.t2).toMinutes()>-5)
                {//无早退
                    normalHour+=Duration.between(t1,t2).toHours();
                }
                else
                {//早退
                    normalHour+=Duration.between(t1,record.t2).toHours();
                }
            }
            else
            {//迟到
                if(Duration.between(t2,record.t2).toMinutes()>-5)
                {//无早退
                    normalHour+=Duration.between(record.t1,t2).toHours();
                }
                else
                {//早退
                    normalHour+=Duration.between(record.t1,record.t2).toHours();
                }
            }
        }
        //计算后两次打卡之间的工时
        if(record.t3!=null)
        {
            if(Duration.between(t3,record.t3).toMinutes()<5)
            {//无迟到
                if(Duration.between(t4,record.t4).toMinutes()>-5)
                {//无早退
                    normalHour+=Duration.between(t3,t4).toHours();
                }
                else
                {//早退
                    normalHour+=Duration.between(t3,record.t4).toHours();
                }
            }
            else
            {//迟到
                if(Duration.between(t4,record.t4).toMinutes()>-5)
                {//无早退
                    normalHour+=Duration.between(record.t3,t4).toHours();
                }
                else
                {//早退
                    normalHour+=Duration.between(record.t3,record.t4).toHours();
                }
            }
        }

        if(normalHour<8)
        {//如正常工时小于8，如果有加班，从加班的工时里补正常工时，并扣除部分加班工时
            float totalHour=Duration.between(record.t1,record.t2).toHours()+Duration.between(record.t3,record.t4).toHours();
            float trueOtHour=Math.min(totalHour-normalHour,record.application.overtime);
            {
                if(trueOtHour>8-normalHour)
                {
                    normalHour=8;
                }
                else
                {
                    normalHour+=trueOtHour;
                }
            }
        }
        return normalHour;
    }

    @Override
    public float getOTHour(AttendanceRecord record)
    {
        float totalHour=Duration.between(record.t1,record.t2).toHours()+Duration.between(record.t3,record.t4).toHours();
        return totalHour<8? 0:Math.min(record.application.overtime,totalHour-8);
    }

    @Override
    public float getSickHour(AttendanceRecord record)
    {

    }

    @Override
    public float getPaidHour(AttendanceRecord record) {
        return 0;
    }

    @Override
    public int getAbsence(AttendanceRecord record) {
        int absence=0;
        if(Duration.between(t1,record.t1).toMinutes()>5)
        {
            absence++;
        }
        if(Duration.between(t2,record.t2).toMinutes()<-5)
        {
            absence++;
        }
        if(Duration.between(t3,record.t3).toMinutes()>5)
        {
            absence++;
        }
        if(Duration.between(t4,record.t4).toMinutes()<-5)
        {
            absence++;
        }
        return absence;
    }
}
