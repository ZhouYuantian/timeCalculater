package com.timecalculater.model.rules;


import com.timecalculater.model.TimeInterval;

import java.time.LocalTime;

/**
  *@ClassName A
  *@Description A大类的规则，所有A附属班次适用此规则
*/
public abstract class A extends StandardRule{
    protected A(LocalTime t1, LocalTime t2, LocalTime t3, LocalTime t4) {
        super(new TimeInterval(t1,t2), new TimeInterval(t3,t4));
    }
}
