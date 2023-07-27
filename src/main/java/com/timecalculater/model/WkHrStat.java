package com.timecalculater.model;

public class WkHrStat {
    public String employeeName;
    public int days3to5H; // 3-5H天数
    public int daysGT5H; // >5H天数
    public int daysGT11H; // >11H天数
    public float weekdaysHours; //工作日工时
    public float weekdaysOvertimeHours; //工作日加班工时
    public float weekendOvertimeHours; //休息日加班工时
    public float holidayHours;  //法定休息日正常工时
    public float holidayOvertimeHours; //法定休息日加班工时
    public float paidLeaveHours; //带薪假工时
    public float sickLeaveHours; //病假工时
    public float absences; //缺勤次数
    //////////////////
    public float after20Subsidy; ///20点后夜班补贴
    public float c2Subsidy;    //C2夜班补贴
}
