package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.TimeInterval;

import java.time.LocalTime;

/**
  *@ClassName J
  *@Description 江门打卡班次，18:30到19:00加班无需申请，直接计入加班工时
*/
public abstract class J extends StandardRule{
    protected J(LocalTime t1, LocalTime t2, LocalTime t3, LocalTime t4)
    {
        super(new TimeInterval(t1,t2), new TimeInterval(t3,t4));
    }

    /**
     * @description 计算加班工时，18:30到19:00加班无需申请，其他时间需要
     * @param record //员工当日考勤记录
     * @return
     **/
    @Override
    public float getOTHour(AttendanceRecord record)
    {
        TimeInterval F630to7=new TimeInterval(LocalTime.of(18,30),LocalTime.of(19,0));
        return F630to7.interception(record.slot2)+super.getOTHour(record);
    }
}
