package com.timecalculater.model;

import java.time.LocalDate;
import java.util.List;

public class AttendanceRecord {
    public String name; //姓名
    public String rule; //规则
    public LocalDate date;  //日期
    public TimeInterval slot1;   //第一次上下班时间段
    public TimeInterval slot2;   //第二次上下班时间段
    public TimeInterval slotX;   //最早到最晚打卡的时间段
    public List<TimeInterval> otApplications;     //加班申请
    public OffSummary offSummary; //假勤申请

}
