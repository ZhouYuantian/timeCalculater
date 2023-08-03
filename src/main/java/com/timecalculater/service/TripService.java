package com.timecalculater.service;

import com.timecalculater.model.TripRecord;
import com.timecalculater.utils.ExcelUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TripService {
    List<TripRecord> tripRecordList;

    public void setDataSource(String filePath)
    {
        tripRecordList= ExcelUtil.getAllTripList(filePath);
    }

    public TripRecord getRecordByNameDate(String name, LocalDate date)
    {
        return tripRecordList.stream().filter(r->r.name.equals(name)&&r.date.isEqual(date)).findFirst().orElse(null);
    }

}
