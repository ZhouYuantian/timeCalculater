package com.timecalculater.model;

import java.time.LocalDate;

/**
  *@ClassName TripRecord
  *@Description 该类的实例用于封装一位员工一天的出差记录，对应出差记录表里的一行
*/
public class TripRecord {
    public String name; //姓名
    public LocalDate date;  //日期
    public float workHour;  //工时
}
