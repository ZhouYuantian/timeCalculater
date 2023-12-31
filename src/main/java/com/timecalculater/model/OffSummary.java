package com.timecalculater.model;

/**
  *@ClassName OffSummary
  *@Description 该类的实例用于封装一位员工一天的假勤统计
*/
public class OffSummary {
    public float annual;    //年假（天）
    public float sick;    //病假（小时）
    public float shift;    //调休假（天）
    public float marriage;     //婚假（天）
    public float maternity;    //产假（天）
    public float a_maternity;  //陪产假（天）
    public float funeral;      //丧假（天）
    public float bLeave;       //事假（小时)


    public float getSickHour()  //病假工时
    {
        return sick;
    }

    public float getPaidLeaveDays() //带薪假天数
    {
        return annual+shift+marriage+maternity+a_maternity+funeral;
    }


}
