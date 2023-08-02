package com.timecalculater.model.rules.Bs;

import com.timecalculater.model.rules.B;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class C1 extends B {
    static LocalTime t1=LocalTime.of(0,0);
    static LocalTime t2=LocalTime.of(8,0);

    public C1() {
        super(t1,t2);
    }
}
