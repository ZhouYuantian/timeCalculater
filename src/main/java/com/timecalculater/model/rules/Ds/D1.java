package com.timecalculater.model.rules.Ds;

import com.timecalculater.model.rules.D;

import java.time.LocalTime;

public class D1 extends D {
    static LocalTime t1=LocalTime.of(8,15);
    static LocalTime t2=LocalTime.of(12,15);
    static LocalTime t3=LocalTime.of(13,30);
    static LocalTime t4=LocalTime.of(17,30);

    public D1() {
        super(t1,t2,t3,t4);
    }
}
