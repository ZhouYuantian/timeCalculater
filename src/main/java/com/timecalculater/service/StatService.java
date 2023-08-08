package com.timecalculater.service;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.WkHrStat;
import com.timecalculater.model.rules.Rule;
import com.timecalculater.utils.AlertUtil;
import com.timecalculater.utils.ExcelUtil;
import com.timecalculater.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class StatService {
    @Autowired
    RecordService recordService;
    @Autowired
    TripService tripService;

    /**
     * @description 处理所有考勤记录，统计出每位员工当月的工时汇总。从每条考勤记录读出当日所属规则，并调用规则类来处理这条记录。
     * @param recordList 包含当月所有员工每日的考勤记录
     * @return 每位员工的当月工时汇总
     **/
    public List<WkHrStat> doStat(List<AttendanceRecord> recordList)
    {
        HashMap<String,WkHrStat> wkHrStatMap= new HashMap<>();

        for(AttendanceRecord record:recordList)
        {
            if(!wkHrStatMap.containsKey(record.name))
            {
                wkHrStatMap.put(record.name,new WkHrStat(record.name));
            }

            Rule rule;
            try{
                rule=(Rule) SpringUtil.getBean(record.rule.toLowerCase());
            }catch (Exception e) {
                AlertUtil.error("班次错误:"+record.rule);
                throw e;
            }
            rule.processAttendance(wkHrStatMap.get(record.name),record);
        }
        return wkHrStatMap.values().stream().collect(Collectors.toList());
    }

    public void outputResults(List<WkHrStat> resultList,String directoryPath)
    {
        String filePath=directoryPath+"\\"+"工时表汇总.xlsx";
        ExcelUtil.writeStatResult(filePath,resultList);
    }
    public void generateResults(String recordTablePath,String tripTablePath,String outputDirectory)
    {
        tripService.setDataSource(tripTablePath);
        List<AttendanceRecord> recordList= recordService.getRecordList(recordTablePath);
        List<WkHrStat> resultList=doStat(recordList);
        outputResults(resultList,outputDirectory);
    }
}
