package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TimeInterval;
import com.timecalculater.model.WkHrStat;

import java.time.LocalDate;
import java.util.List;



/**
  *@ClassName StandardRule
  *@Description 标准班次的模板，实现了最常规的班次处理方式。(A大类可直接使用）
*/
public abstract class StandardRule implements Rule{
    TimeInterval reg_slot1,reg_slot2;   //规则定好的两段上班时间
    protected StandardRule(TimeInterval reg_slot1,TimeInterval reg_slot2)
    {
        this.reg_slot1=reg_slot1;
        this.reg_slot2=reg_slot2;
    }


    /**
     * @description 计算当前员工的当日正常工时
     * @param record 某位员工当日考勤记录
     * @return 正常工时（H）
     **/
    public float getNormalHour(AttendanceRecord record)
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

    /**
     * @description 计算当前员工的当日加班工时
     * @param record 某位员工的当日考勤记录
     * @return 加班工时(H)
     **/
    public float getOTHour(AttendanceRecord record)
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

    /**
     * @description 计算当前员工的当日病假工时
     * @param record 某位员工的当日考勤记录
     * @return 病假工时(H)
     **/
    public float getSickHour(AttendanceRecord record)
    {
        return record.offSummary.getSickHour();
    }

    /**
     * @description 计算当前员工当日的带薪假工时
     * @param record 某位员工的当日考勤记录
     * @return 带薪假工时（H）
     **/
    public float getPaidHour(AttendanceRecord record)
    {
        return (record.offSummary.getPaidLeaveDays())*8;   //天数*8=工时
    }

    /**
     * @description 计算当前员工当日考勤异常次数，考虑埋事假，不考虑其他假
     * @param record 某位员工的当日考勤记录
     * @return 当天异常次数
     **/
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

    /**
     * @description 添加 3-5小时天数，5小时天数，11小时天数 到员工当月记录
     * @param totalWkHour 某位员工当日总工时
     * @param wkHrStat 某位员工当月记录
     * @return
     **/
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

    /**
     * @description 添加某位员工当日的工时到当月记录
     * @param normalHour 当日正常工时(H)
     * @param otHour    当日加班工时（H）
     * @param wkHrStat  员工的当月记录
     * @param date      当日日期
     * @return
     **/
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

    /**
     * @description 将员工当日带薪假工时添加到其当月记录
     * @param paidHour 当日带薪假工时
     * @param wkHrStat 某员工当月记录
     * @return
     **/
    public void setPLHour(float paidHour,WkHrStat wkHrStat)
    {
        wkHrStat.paidLeaveHours+=paidHour;
    }

    /**
     * @description 将员工当日病假工时添加到其当月记录
     * @param sickHour 当日病假工时
     * @param wkHrStat 某员工当月记录
     * @return
     **/
    public void setSickHour(float sickHour,WkHrStat wkHrStat)
    {
        wkHrStat.sickLeaveHours+=sickHour;
    }

    /**
     * @description 综合病假和带薪假考虑，调整员工当日考勤异常次数
     * @param unusual 员工当日考勤异常次数
     * @param totalOffHour 总请假工时 （不包含事假)
     * @param wkHrStat 员工当月记录
     * @return
     **/
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

    /**
     * @description 处理考勤记录，提取并计算工时数据，计入员工的当月工时记录
     * @param wkHrStat 员工当月工时记录
     * @param record 员工某一日的考勤数据
     * @return
     **/
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
