package com.timecalculater.service;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.timecalculater.model.TripRecord;
import com.timecalculater.utils.ExcelUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TripService {
    Table<String, LocalDate, TripRecord> tripTable;

    /**
     * @description 设置出差信息的数据源，并从中读取所有出差数据并放入tripTable
     * @param filePath 数据源文件路径
     * @return
     **/
    public void setDataSource(String filePath)
    {
        tripTable= HashBasedTable.create();
        List<TripRecord> tripRecordList= ExcelUtil.getAllTripList(filePath);
        for(TripRecord tR:tripRecordList) tripTable.put(tR.name,tR.date,tR);
    }

    /**
     * @description 用员工姓名和出差记录获取对应的出差信息
     * @param name 员工姓名
     * @param date 出差日期
     * @return 出差信息
     **/
    public TripRecord getRecordByNameDate(String name, LocalDate date)
    {
        return tripTable.get(name,date);
    }

}
