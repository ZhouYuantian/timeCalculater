package com.timecalculater.model;

import com.timecalculater.model.rules.A;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceRecord {
    public String name; //姓名
    public String rule; //规则
    public LocalDate date;  //日期
    public TimeInterval slot1;   //第一次上下班时间段
    public TimeInterval slot2;   //第二次上下班时间段
    public Application application; //假勤申请

}
