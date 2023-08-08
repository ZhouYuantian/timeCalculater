package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;


/**
  *@ClassName Rule
  *@Description 考勤规则类的接口，所有考勤规则类需遵循此规范，实现方法, 用于处理考勤记录
*/
public interface Rule {

    /**
    * @Description: 处理考勤记录，从record中提取并计算工时数据，并计入员工的当月工时汇总wkHrStat
    * @Param wkHrStat: 员工当月工时汇总
    * @Param record: 员工一日的考勤记录
    * */
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord record);
}
