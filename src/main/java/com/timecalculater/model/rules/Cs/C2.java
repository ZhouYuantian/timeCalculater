package com.timecalculater.model.rules.Cs;

import com.timecalculater.model.rules.C;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class C2 extends C {
    static LocalTime t1=LocalTime.of(20,0);
    static LocalTime t2=LocalTime.of(0,0);
    static LocalTime t3=LocalTime.of(0,20);
    static LocalTime t4=LocalTime.of(4,0);
    static LocalTime t5=LocalTime.of(5,0);
    static LocalTime t6=LocalTime.of(8,0);

    public C2() {
        super(t1,t2,t3,t4,t5,t6);
    }
}
