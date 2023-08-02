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
        return record.slot1.toHour() + record.slot2.toHour();
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
