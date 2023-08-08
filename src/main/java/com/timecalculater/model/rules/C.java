package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TimeInterval;
import com.timecalculater.model.WkHrStat;

import java.time.LocalTime;

/**
  *@ClassName C
  *@Description C类规则，三轮打卡，需计算20点后夜班补贴
*/
public abstract class C extends B{
    TimeInterval reg_slot3;     //规定的第三轮打卡时间段
    protected C(LocalTime t1,LocalTime t2,LocalTime t3,LocalTime t4,LocalTime t5,LocalTime t6)
    {
        super(t1,t2);
        reg_slot2=new TimeInterval(t3,t4);
        reg_slot3=new TimeInterval(t5,t6);
    }

    /**
     * @description  计算正常工时，因为有三轮打卡，所以计算方式有异于标准规则，需重写
     * @param record 员工当日考勤记录
     * @return  当日正常工时（H）
     **/
    @Override
    public float getNormalHour(AttendanceRecord record) {
        float normalHour= super.getNormalHour(record);
        normalHour+=reg_slot3.interception(record.slot3);
        return normalHour;
    }

    /**
     * @description 计算20点后夜班工时
     * @param record 员工当日考勤记录
     * @return 20点后工时（H）
     **/
    @Override
    public float getAfter20WkHr(AttendanceRecord record) {
        TimeInterval F12to20=new TimeInterval(LocalTime.of(12,0),LocalTime.of(20,0));
        return record.slot1.complement(F12to20)+record.slot2.complement(F12to20)+record.slot3.complement(F12to20);
    }

    /**
     * @description 求员工当日异常次数
     * @param record 员工当日考勤记录
     * @return 异常次数
     **/
    @Override
    public int getUnusual(AttendanceRecord record) {
        int unusual=super.getUnusual(record);
        if(!record.slot3.startBefore(reg_slot3,5))
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot3.t1)) unusual--;
            }
        }
        if(!record.slot3.endAfter(reg_slot3,5))
        {
            unusual++;
            for(TimeInterval bls:record.blApplications)
            {
                if(bls.contains(reg_slot3.t2)) unusual--;
            }
        }
        return unusual;
    }

    /**
     * @description 根据假时调整异常次数，并计入员工当月记录
     * @param unusual 异常次数
     * @param totalOffHour 总假时
     * @param wkHrStat 员工当月记录
     * @return
     **/
    @Override
    public void setAbsence(int unusual, float totalOffHour, WkHrStat wkHrStat) {
        if(totalOffHour>=4)
        {
            unusual-=3;
        }
        if(totalOffHour>=8)
        {
            unusual=0;
        }
        wkHrStat.absences+=unusual;
    }
}
