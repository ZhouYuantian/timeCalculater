package com.timecalculater.model.rules.Zs;

import com.timecalculater.model.rules.Z;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class Z1 extends Z {
    static LocalTime t1=LocalTime.of(9,15);
    static LocalTime t2=LocalTime.of(12,15);
    static LocalTime t3=LocalTime.of(13,30);
    static LocalTime t4=LocalTime.of(17,30);

    public Z1() {
        super(t1,t2,t3,t4);
    }
}
