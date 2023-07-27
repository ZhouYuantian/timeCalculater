package com.timecalculater.model;

import com.timecalculater.model.rules.A;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceRecord {
    public String name;
    public String rule;
    public LocalDate date;
    public LocalTime t1,t2,t3,t4;
    public Application application;

}
