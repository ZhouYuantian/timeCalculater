package com.timecalculater.model.rules.Js;

import com.timecalculater.model.rules.J;

import java.time.LocalTime;

public class J1 extends J {
    static LocalTime t1=LocalTime.of(8,30);
    static LocalTime t2=LocalTime.of(12,15);
    static LocalTime t3=LocalTime.of(13,30);
    static LocalTime t4=LocalTime.of(18,30);

    public J1() {
        super(t1,t2,t3,t4);
    }
}
