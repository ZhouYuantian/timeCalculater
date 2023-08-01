package com.timecalculater.model.rules.Bs;

import com.timecalculater.model.rules.B;

import java.time.LocalTime;

public class B1 extends B {
    static LocalTime t1=LocalTime.of(16,0);
    static LocalTime t2=LocalTime.of(0,0);

    public B1() {
        super(t1,t2);
    }
}
