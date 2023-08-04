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

    public void setDataSource(String filePath)
    {
        tripTable= HashBasedTable.create();
        List<TripRecord> tripRecordList= ExcelUtil.getAllTripList(filePath);
        for(TripRecord tR:tripRecordList) tripTable.put(tR.name,tR.date,tR);
    }

    public TripRecord getRecordByNameDate(String name, LocalDate date)
    {
        return tripTable.get(name,date);
    }

}
