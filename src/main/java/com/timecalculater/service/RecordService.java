package com.timecalculater.service;

import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.utils.ExcelUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecordService {
    public List<AttendanceRecord> getRecordList(String filePath)
    {
        return ExcelUtil.getAllRecordList(filePath);
    }
}
