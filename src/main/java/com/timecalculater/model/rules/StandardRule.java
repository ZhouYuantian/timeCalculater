package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TimeInterval;
import com.timecalculater.model.WkHrStat;

import java.time.LocalDate;
import java.util.List;


public abstract class StandardRule implements Rule{
    TimeInterval reg_slot1,reg_slot2;
    protected StandardRule(TimeInterval reg_slot1,TimeInterval reg_slot2)
    {
        this.reg_slot1=reg_slot1;
        this.reg_slot2=reg_slot2;
    }


    public float getNormalHour(AttendanceRecord record)     //计算正常工时
    {
        float normalHour=0;
        if(reg_slot1!=null)
        {
            normalHour+=reg_slot1.interception(record.slot1);
        }
        if(reg_slot2!=null)
        {
            normalHour+=reg_slot2.interception(record.slot2);
        }
        return normalHour;
    }


    public float getOTHour(AttendanceRecord record)     //计算加班工时
    {
        float otHour=0;
        List<TimeInterval> otList=record.otApplications;
        for(TimeInterval ot:otList)
        {
            otHour+=(ot.interception(record.slotX));
        }
        return otHour;
    }


    public float getSickHour(AttendanceRecord record)   //病假工时
    {
        return record.offSummary.getSickHour();
    }


    public float getPaidHour(AttendanceRecord record)   //计算带薪假工时
    {
        return (record.offSummary.getPaidLeaveDays())*8;   //天数*8=工时
    }


    public int getUnusual(AttendanceRecord record) {    //计算考勤异常次数
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

        if (record.offSummary.getTotalOffDays()>0)
        {//有请假但是小于等于半天，只计半天考勤异常
            unusual-=2;
        }
        if(record.offSummary.getTotalOffDays()>0.5)
        {//有请薪假多于半天，不计考勤异常
            unusual=0;
        }

        return unusual<0?0:unusual;
    }

    /////////处理3-5H天数，5-11H天数，11H天数
    public void setSubsidyDays(float normalHour,float otHour, WkHrStat wkHrStat)
    {
        float totalHour=normalHour+otHour;
        if(totalHour>=3 && totalHour<5)
        {
            wkHrStat.days3to5H++;
        }
        else if(totalHour>=5 && totalHour<11)
        {
            wkHrStat.daysGT5H++;
        }
        else if(totalHour>=11)
        {
            wkHrStat.daysGT11H++;
        }
    }

    ////////处理工作日工时，工作日加班 或 休息日加班，节假日加班
    public void setWorkHour(float normalHour, float otHour, WkHrStat wkHrStat, LocalDate date)
    {
        //工作日处理方式
        wkHrStat.weekdaysHours += normalHour;
        wkHrStat.weekdaysOvertimeHours += otHour;
    }

    public void setPLHour(float paidHour,WkHrStat wkHrStat)
    {
        wkHrStat.paidLeaveHours+=paidHour;
    }

    public void setSickHour(float sickHour,WkHrStat wkHrStat)
    {
        wkHrStat.sickLeaveHours+=sickHour;
    }

    public void setAbsence(int absence,WkHrStat wkHrStat)
    {
        wkHrStat.absences+=absence;
    }

    /*
     * @Description: 处理考勤记录，提取并计算工时数据，计入员工的当月工时汇总
     * @Param wkHrStat: 员工当月工时汇总
     * @Param record: 员工一日的考勤数据
     * */
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord record)
    {
        float normalHour=getNormalHour(record);
        float otHour=getOTHour(record);
        //如正常工时小于8，从加班的工时里补正常工时，并扣除部分加班工时
        if(normalHour<8)
        {
            if(otHour+normalHour>8)
            {
                otHour-=8-normalHour;
                normalHour=8;
            }
            else
            {
                normalHour+=otHour;
                otHour=0;
            }
        }

        float sickHour=getSickHour(record);
        float paidHour=getPaidHour(record);
        int unusual=getUnusual(record);

        setSubsidyDays(normalHour,otHour,wkHrStat);
        setWorkHour(normalHour,otHour,wkHrStat,record.date);
        setPLHour(paidHour,wkHrStat);
        setSickHour(sickHour,wkHrStat);
        setAbsence(unusual,wkHrStat);
    }
}
