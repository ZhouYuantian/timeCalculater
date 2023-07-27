package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;

/*
 * @Description: 考勤规则类的接口，所有考勤规则类需遵循此规范，实现方法
 * */
public interface Rule {
    /*
    * @Description: 处理考勤记录，提取并计算工时数据，计入员工的当月工时汇总
    * @Param wkHrStat: 员工当月工时汇总
    * @Param record: 员工一日的考勤数据
    * */
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord record);
}
