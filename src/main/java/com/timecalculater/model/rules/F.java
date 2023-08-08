package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import org.springframework.stereotype.Component;

/**
  *@ClassName F
  *@Description 自由打卡规则，无固定上下班时间，不计异常，不计加班
*/
@Component
public class F extends StandardRule{
    public F()
    {
        super(null,null);
    }

    /**
     * @description 计算正常工时，无固定上下班时间，正常工时即为打卡时间段
     * @param record 当日打卡记录
     * @return 正常工时（H）
     **/
    @Override
    public float getNormalHour(AttendanceRecord record) {
        float normalHour=0;
        normalHour+=record.slot1.toHour();
        normalHour+=record.slot2.toHour();
        return normalHour;
    }

    /**
     * @description 计算加班工时，该班次不计加班，因此返回0
     * @param record 当日考勤记录
     * @return 加班工时（H）
     **/
    @Override
    public float getOTHour(AttendanceRecord record) {
        return 0;
    }

    /**
     * @description 计算考勤异常次数，该班次不计异常，因此返回0
     * @param record 当日考勤记录
     * @return 考勤异常次数
     **/
    @Override
    public int getUnusual(AttendanceRecord record) {
        return 0;
    }

}
