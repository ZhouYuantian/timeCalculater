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
                rule=(Rule) SpringUtil.getBean(record.rule);
            }catch (Exception e) {
                AlertUtil.warning("班次错误:"+record.rule);
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
