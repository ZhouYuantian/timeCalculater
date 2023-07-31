package com.timecalculater.utils;


import com.timecalculater.model.AttendanceRecord;
import com.timecalculater.model.OffSummary;
import com.timecalculater.model.TimeInterval;
import com.timecalculater.model.WkHrStat;
import com.timecalculater.utils.coordinatesMapper.RecordTbl;
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
    static void initialize(String filePath)
    {
        try{
            File file = new File(filePath);
            FileInputStream fs = FileUtils.openInputStream(file);
            workbook = new XSSFWorkbook(fs);
            sheet = workbook.getSheetAt(0);
            firstRow = sheet.getFirstRowNum() + 1; //数据从 列1 开始
            lastRow = sheet.getLastRowNum();
            currRow=0;
        }catch (Exception e)
        {
            AlertUtil.warning("初始化文件异常,请重试");
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
        {   //获取String,然后转float保留两位小数
            cell.setCellType(CellType.STRING);
            String value=cell.getStringCellValue();
            try {
                return value.equals("--")?0:Float.parseFloat(value);
            }catch (NumberFormatException e) {
                AlertUtil.warning((currRow+1)+"行的信息异常，请检查并重试");
                throw new RuntimeException();
            }
        }
        return 0;
    }
    public static List<AttendanceRecord> getAllRecordList(String filePath)
    {
        initialize(filePath);

        List<AttendanceRecord> recordList=new ArrayList<>();
        for(currRow=firstRow;currRow<=lastRow;currRow++)
        {
            AttendanceRecord record=new AttendanceRecord();
            row=sheet.getRow(currRow);

            record.name=getStringFromCell(RecordTbl.name);
            record.date=StringUtil.getDate(getStringFromCell(RecordTbl.date));
            record.rule=getStringFromCell(RecordTbl.rule);
            LocalTime t1=StringUtil.getTime(getStringFromCell(RecordTbl.t1));
            LocalTime t2=StringUtil.getTime(getStringFromCell(RecordTbl.t2));
            LocalTime t3=StringUtil.getTime(getStringFromCell(RecordTbl.t3));
            LocalTime t4=StringUtil.getTime(getStringFromCell(RecordTbl.t4));
            record.slot1=new TimeInterval(t1,t2);
            record.slot2=new TimeInterval(t3,t4);
            record.otApplications=StringUtil.getOtApplications(getStringFromCell(RecordTbl.application));

            OffSummary offSummary=new OffSummary();
            offSummary.annual=getFloatFromCell(RecordTbl.annual);
            offSummary.business=getFloatFromCell(RecordTbl.business);
            offSummary.sick=getFloatFromCell(RecordTbl.sick);
            offSummary.shift=getFloatFromCell(RecordTbl.shift);
            offSummary.marriage=getFloatFromCell(RecordTbl.marriage);
            offSummary.maternity=getFloatFromCell(RecordTbl.maternity);
            offSummary.a_maternity=getFloatFromCell(RecordTbl.a_maternity);
            offSummary.funeral=getFloatFromCell(RecordTbl.funeral);

            record.offSummary=offSummary;

            recordList.add(record);
        }

        return recordList;
    }

    public static void writeStatResult(String filePath, List<WkHrStat> statResults)
    {

    }
}
