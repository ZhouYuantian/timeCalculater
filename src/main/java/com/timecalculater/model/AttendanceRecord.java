package com.timecalculater.model;

import java.time.LocalDate;
import java.util.List;

public class AttendanceRecord {
    public String name; //姓名
    public String rule; //规则
    public LocalDate date;  //日期
    public TimeInterval slot1;   //第一次上下班时间段
    public TimeInterval slot2;   //第二次上下班时间段
    public TimeInterval slot3;   //第三次上下班时间段
    public TimeInterval slotE;   //最早到第一次打卡时间段
    public TimeInterval slotL;   //从最后一次打卡到最晚打卡时间段
    public List<TimeInterval> otApplications;     //加班申请时间段
    public List<TimeInterval> blApplications;      //事假申请时间段
    public OffSummary offSummary; //假勤申请

}
