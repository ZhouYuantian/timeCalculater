package com.timecalculater.model.rules.As;

import com.timecalculater.model.rules.A;

import java.time.LocalTime;

public class A7 extends A {
    static LocalTime t1=LocalTime.of(8,15);
    static LocalTime t2=LocalTime.of(12,15);
    static LocalTime t3=LocalTime.of(13,30);
    static LocalTime t4=LocalTime.of(17,30);

    public A7() {
        super(t1,t2,t3,t4);
    }
}