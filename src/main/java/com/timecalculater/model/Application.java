package com.timecalculater.model;

public class Application {
    //单位均为小时(h)
    public float overtime;     //加班
    public float annual;    //年假
    public float sick;    //病假
    public float shift;    //调休假
    public float marriage;     //婚假
    public float maternity;    //产假
    public float a_maternity;  //陪产假
    public float funeral;      //丧假

    public float getOvertime()
    {
        return overtime;
    }

    public float getSickHour()  //病假工时
    {
        return sick;
    }


    public float getPaidLeaveHour() //带薪假工时
    {
        return -1;
    }
}
