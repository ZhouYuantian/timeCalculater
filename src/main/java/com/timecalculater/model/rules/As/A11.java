package com.timecalculater.model.rules.As;

import com.timecalculater.model.TimeInterval;
import com.timecalculater.model.rules.A;

import java.time.LocalTime;

public class A11 extends A {
    static LocalTime t1=LocalTime.of(8,30);
    static LocalTime t2=LocalTime.of(12,0);
    static LocalTime t3=LocalTime.of(13,0);
    static LocalTime t4=LocalTime.of(17,30);

    public A11() {
        super(t1,t2,t3,t4);
    }
}
