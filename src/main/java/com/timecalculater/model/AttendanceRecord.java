package com.timecalculater.model;

import com.timecalculater.model.rules.A;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceRecord {
    public String name; //姓名
    public String rule; //规则
    public LocalDate date;  //日期
    public LocalTime t1,t2,t3,t4;   //打卡时间
    public Application application; //假勤申请

}
