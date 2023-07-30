package com.timecalculater.model.rules;


import com.timecalculater.model.TimeInterval;

import java.time.LocalTime;


public abstract class A extends StandardRule{
    protected A(LocalTime t1, LocalTime t2, LocalTime t3, LocalTime t4) {
        super(new TimeInterval(t1,t2), new TimeInterval(t3,t4));
    }
}
