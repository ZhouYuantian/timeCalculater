package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import org.springframework.stereotype.Component;


@Component
public class F extends StandardRule{
    public F()
    {
        super(null,null);
    }

    @Override
    public float getNormalHour(AttendanceRecord record) {
        float normalHour=0;
        normalHour+=record.slot1.toHour();
        normalHour+=record.slot2.toHour();
        return normalHour;
    }
    @Override
    public float getOTHour(AttendanceRecord record) {
        return 0;
    }

    @Override
    public int getUnusual(AttendanceRecord record) {
        return 0;
    }

}
