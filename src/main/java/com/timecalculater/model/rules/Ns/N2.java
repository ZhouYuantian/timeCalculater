package com.timecalculater.model.rules.Ns;

import com.timecalculater.model.rules.N;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

//晚班中山
@Component
public class N2 extends N {
    static LocalTime t1=LocalTime.of(14,0);
    static LocalTime t2=LocalTime.of(21,30);

    public N2() {
        super(t1,t2);
    }
}
