package com.timecalculater.utils;


import com.timecalculater.model.*;
import com.timecalculater.model.rules.E;
import com.timecalculater.utils.coordinatesMapper.RecordTbl;
import com.timecalculater.utils.coordinatesMapper.StatTbl;
import com.timecalculater.utils.coordinatesMapper.TripTbl;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class ExcelUtil {
    static Row row;
    static Cell cell;
    static Sheet sheet;
    static Workbook workbook;
    static int firstRow,lastRow;
    static int currRow;  //记录读取中数据的行数，当数据有异常时输出行数便于检查
    static String templateFile="src/main/resources/com/timecalculater/excel/template/result_template.xlsx";
    static void initialize(String filePath)
    {
        try{
            File file = new File(filePath);
            FileInputStream fs = FileUtils.openInputStream(file);
            workbook = new XSSFWorkbook(fs);
            sheet = workbook.getSheetAt(0);
            firstRow = sheet.getFirstRowNum();
            lastRow = sheet.getLastRowNum();
            currRow=0;
        }catch (Exception e)
        {
            AlertUtil.warning("初始化文件异常,请重试");
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
    static void saveToFile(String filePath)
    {
        try {
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
        }catch (Exception e)
        {
            AlertUtil.warning("保存文件异常,请重试");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    static String getStringFromCell(int cellIdx)
    {
        Cell cell=row.getCell(cellIdx);
        if(cell!=null)
        {
            cell.setCellType(CellType.STRING);
            return cell.getStringCellValue();
        }
        return "";
    }
    static float getFloatFromCell(int cellIdx)
    {
        Cell cell=row.getCell(cellIdx);
        if(cell!=null)
        {   //获取String,然后转float
            cell.setCellType(CellType.STRING);
            String value=cell.getStringCellValue();
            return value.equals("--")?0:Float.parseFloat(value);
        }
        return 0;
    }
    static void writeToCellAt(int colNum, String value)
    {
        cell=row.getCell(colNum);
        cell.setCellValue(value);
    }
    static void writeToCellAt(int colNum, float value)
    {
        cell=row.getCell(colNum);
        cell.setCellValue(String.format("%.2f",value));
    }
    public static List<AttendanceRecord> getAllRecordList(String filePath)
    {
        initialize(filePath);

        List<AttendanceRecord> recordList=new ArrayList<>();
        for(currRow=firstRow=4;currRow<=lastRow;currRow++) {
            AttendanceRecord record = new AttendanceRecord();
            row = sheet.getRow(currRow);

            try {
                record.name = getStringFromCell(RecordTbl.name);
                record.date = StringUtil.getDate(getStringFromCell(RecordTbl.date));
                record.rule = getStringFromCell(RecordTbl.rule);
                LocalTime t1 = StringUtil.getTime(getStringFromCell(RecordTbl.t1));
                LocalTime t2 = StringUtil.getTime(getStringFromCell(RecordTbl.t2));
                LocalTime t3 = StringUtil.getTime(getStringFromCell(RecordTbl.t3));
                LocalTime t4 = StringUtil.getTime(getStringFromCell(RecordTbl.t4));
                LocalTime t5 = StringUtil.getTime(getStringFromCell(RecordTbl.t5));
                LocalTime t6 = StringUtil.getTime(getStringFromCell(RecordTbl.t6));
                LocalTime tE = StringUtil.getTime(getStringFromCell(RecordTbl.tE));
                LocalTime tL = StringUtil.getTime(getStringFromCell(RecordTbl.tL));
                record.slot1 = t1 != null ? new TimeInterval(t1, t2) : null;
                record.slot2 = t3 != null ? new TimeInterval(t3, t4) : null;
                record.slot3 = t5 != null ? new TimeInterval(t5, t6) : null;
                record.slotX = tE != null ? new TimeInterval(tE, tL) : null;
                record.otApplications = StringUtil.getOtApplications(getStringFromCell(RecordTbl.application));

                OffSummary offSummary = new OffSummary();
                offSummary.annual = getFloatFromCell(RecordTbl.annual);
                offSummary.business = getFloatFromCell(RecordTbl.business);
                offSummary.sick = getFloatFromCell(RecordTbl.sick);
                offSummary.shift = getFloatFromCell(RecordTbl.shift);
                offSummary.marriage = getFloatFromCell(RecordTbl.marriage);
                offSummary.maternity = getFloatFromCell(RecordTbl.maternity);
                offSummary.a_maternity = getFloatFromCell(RecordTbl.a_maternity);
                offSummary.funeral = getFloatFromCell(RecordTbl.funeral);
                record.offSummary=offSummary;
            } catch (Exception e) {
                AlertUtil.warning("考勤记录表"+(currRow+1)+"行的信息异常，请检查并重试");
                throw e;
            }

            recordList.add(record);
        }

        return recordList;
    }

    public static List<TripRecord> getAllTripList(String filePath)
    {
        initialize(filePath);

        List<TripRecord> recordList=new ArrayList<>();
        for(currRow=firstRow=1;currRow<=lastRow;currRow++)
        {
            TripRecord tripRecord=new TripRecord();
            row=sheet.getRow(currRow);

            try {
                tripRecord.name=getStringFromCell(TripTbl.name);
                tripRecord.date=StringUtil.getDate(getStringFromCell(TripTbl.date));
                tripRecord.workHour=getFloatFromCell(TripTbl.workHour);
            }catch (Exception e) {
                AlertUtil.warning("出差记录表"+(currRow+1)+"行的信息异常，请检查并重试");
                throw e;
            }

            recordList.add(tripRecord);
        }
        return recordList;
    }
    public static void writeStatResult(String outputFile, List<WkHrStat> statResults)
    {
        initialize(templateFile);

        currRow=firstRow=2;
        for(WkHrStat result:statResults)
        {
            row=sheet.getRow(currRow);
            writeToCellAt(StatTbl.name,result.name);
            writeToCellAt(StatTbl.days3to5H,result.days3to5H);
            writeToCellAt(StatTbl.daysGT5H,result.daysGT5H);
            writeToCellAt(StatTbl.daysGT11H,result.daysGT11H);
            writeToCellAt(StatTbl.weekdaysHours,result.weekdaysHours);
            writeToCellAt(StatTbl.weekdaysOvertimeHours,result.weekdaysOvertimeHours);
            writeToCellAt(StatTbl.weekendOvertimeHours,result.weekendOvertimeHours);
            writeToCellAt(StatTbl.holidayHours,result.holidayHours);
            writeToCellAt(StatTbl.holidayOvertimeHours,result.holidayOvertimeHours);
            writeToCellAt(StatTbl.paidLeaveHours,result.paidLeaveHours);
            writeToCellAt(StatTbl.sickLeaveHours,result.sickLeaveHours);
            writeToCellAt(StatTbl.after20Subsidy,result.after20Subsidy);
            writeToCellAt(StatTbl.absences,result.absences);
            writeToCellAt(StatTbl.c2Subsidy,result.c2Subsidy);
            currRow++;
        }

        saveToFile(outputFile);
    }

    public static void main(String[] args) {
//        List<AttendanceRecord> recordList=getAllRecordList("src/main/resources/com/timecalculater/excel/template/record_template.xlsx");
//        for(AttendanceRecord record:recordList.subList(recordList.size()-10,recordList.size()))
//        {
//            System.out.println(record);
//        }

        WkHrStat result=new WkHrStat("张三");
        result.name="张三";
        result.days3to5H=1;
        result.daysGT5H=2;
        result.daysGT11H=11;
        result.weekdaysHours=(float) 143.98;
        result.weekdaysOvertimeHours=(float) 25.18;
        result.weekendOvertimeHours=(float) 19.65;
        result.holidayHours=(float) 8.00;
        result.holidayOvertimeHours=0;
        result.paidLeaveHours=8;
        result.sickLeaveHours=1;
        result.after20Subsidy=10;
        result.absences=6;
        result.c2Subsidy=200;

        List<WkHrStat> resultList=new ArrayList<>();
        resultList.add(result);

        writeStatResult("src/main/resources/com/timecalculater/excel/temporary/result_temp.xlsx",resultList);
    }
}
