package com.timecalculater.model.rules;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;
import com.timecalculater.utils.DateUtil;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;

/*
 * @Description: 考勤规则类的接口，所有考勤规则类需遵循此规范，实现方法
 * */
public abstract class Rule {
    public abstract float getNormalHour(AttendanceRecord record);    //正常工时
    public abstract float getOTHour(AttendanceRecord record);       //加班工时
    public abstract float getSickHour(AttendanceRecord record);    //病假工时
    public abstract float getPaidHour(AttendanceRecord record);    //带薪假工时
    public abstract int getAbsence(AttendanceRecord record);    //考勤异常次数

    /*
    * @Description: 处理考勤记录，提取并计算工时数据，计入员工的当月工时汇总
    * @Param wkHrStat: 员工当月工时汇总
    * @Param record: 员工一日的考勤数据
    * */
    public void processAttendance(WkHrStat wkHrStat, AttendanceRecord record)
    {
        float normalHour=getNormalHour(record);
        float OTHour=getOTHour(record);
        float sickHour=getSickHour(record);
        float paidHour=getPaidHour(record);
        int absence=getAbsence(record);

        /////////处理3-5H天数，5-11H天数，11H天数
        if(normalHour+OTHour>=3 && normalHour+OTHour<5)
        {
            wkHrStat.days3to5H++;
        }
        else if(normalHour+OTHour>=5 && normalHour+OTHour<11)
        {
            wkHrStat.daysGT5H++;
        }
        else if(normalHour+OTHour>=11)
        {
            wkHrStat.daysGT11H++;
        }

        ////////处理工作日工时，工作日加班，休息日加班，节假日加班
        if(DateUtil.holidaySet.contains(record.date))
        {//节假日
            wkHrStat.holidayHours+=normalHour;
            wkHrStat.holidayOvertimeHours+=OTHour;
        }
        else if(record.date.getDayOfWeek().get(DAY_OF_WEEK)<6)
        {//工作日
            wkHrStat.weekdaysHours+=normalHour;
            wkHrStat.weekdaysOvertimeHours+=OTHour;
        }
        else
        {//休息日
            wkHrStat.weekendOvertimeHours+=(normalHour+OTHour);
        }

        //////////处理带薪假，病假，缺勤
        wkHrStat.paidLeaveHours+=paidHour;
        wkHrStat.sickLeaveHours+=sickHour;
        wkHrStat.absences+=absence;
    }
}
