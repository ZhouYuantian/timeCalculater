package com.timecalculater.model.rules.As;

import com.timecalculater.model.rules.A;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
@Component
public class A8 extends A {
    static LocalTime t1=LocalTime.of(8,20);
    static LocalTime t2=LocalTime.of(12,15);
    static LocalTime t3=LocalTime.of(13,30);
    static LocalTime t4=LocalTime.of(17,20);

    public A8() {
        super(t1,t2,t3,t4);
    }
}
