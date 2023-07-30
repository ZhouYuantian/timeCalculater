package com.timecalculater.model.rules.Ns;

import com.timecalculater.model.rules.N;

import java.time.LocalTime;


public class N1 extends N {
    static LocalTime t1=LocalTime.of(13,30);
    static LocalTime t2=LocalTime.of(22,0);

    public N1() {
        super(t1,t2);
    }
}
