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
            otHour+=(ot.interception(record.slotE));
            otHour+=(ot.interception(record.slot1));
            otHour+=(ot.interception(record.slot2));
            otHour+=(ot.interception(record.slot3));
            otHour+=(ot.interception(record.slotL));
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
        if(record.offSummary.bLeave>0) unusual++;   //有事假，记一次异常
        if(!record.slot1.startBefore(reg_slot1,5))
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot1.t1)) unusual--;
            }
        }
        if(!record.slot1.endAfter(reg_slot1,5))
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot1.t2)) unusual--;
            }
        }
        if(!record.slot2.startBefore(reg_slot2,5))
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot2.t1)) unusual--;
            }
        }
        if(!record.slot2.endAfter(reg_slot2,5))
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot2.t2)) unusual--;
            }
        }

        return unusual;
    }

    /////////处理3-5H天数，5-11H天数，11H天数
    public void setSubsidyDays(float totalWkHour, WkHrStat wkHrStat)
    {
        if(totalWkHour>=3 && totalWkHour<5)
        {
            wkHrStat.days3to5H++;
        }
        else if(totalWkHour>=5)
        {
            wkHrStat.daysGT5H++;
        }
        if(totalWkHour>=11)
        {
            wkHrStat.daysGT11H++;
        }
    }

    ////////处理工作日工时，工作日加班 或 休息日加班，节假日加班
    public void setWorkHour(float normalHour, float otHour, WkHrStat wkHrStat, LocalDate date)
    {
        //如正常工时小于8，从加班的工时里补正常工时，并扣除部分加班工时
        if(otHour+normalHour>8)
        {
            otHour=otHour+normalHour-8;
            normalHour=8;
        }
        else
        {
            normalHour=otHour+normalHour;
            otHour=0;
        }
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

    public void setAbsence(int unusual,float totalOffHour,WkHrStat wkHrStat)
    {
        if(totalOffHour>=4)
        {
            unusual-=2;
        }
        if(totalOffHour>=8)
        {
            unusual=0;
        }
        wkHrStat.absences+=unusual;
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
        float sickHour=getSickHour(record);
        float paidHour=getPaidHour(record);
        int unusual=getUnusual(record);
        float totalWkHour=normalHour+otHour;
        float totalOffHour=sickHour+paidHour;


        setSubsidyDays(totalWkHour,wkHrStat);
        setWorkHour(normalHour,otHour,wkHrStat,record.date);
        setPLHour(paidHour,wkHrStat);
        setSickHour(sickHour,wkHrStat);
        setAbsence(unusual,totalOffHour,wkHrStat);
    }
}
