package com.timecalculater.utils;

import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class FileUtil {
    static String recordSourcePath="src/main/resources/com/timecalculater/excel/template/record_template.xlsx";
    static String recordTempPath="src/main/resources/com/timecalculater/excel/temporary/record_template.xlsx";
    static String tripSourcePath="src/main/resources/com/timecalculater/excel/template/trip_template.xlsx";
    static String tripTempPath="src/main/resources/com/timecalculater/excel/temporary/trip_template.xlsx";
    static void openFile(String filePath) throws IOException {
        File file = new File(filePath); // 创建文件对象，路径为filePath
        Desktop.getDesktop().open(file); // 启动已在本机桌面上注册的关联应用程序，打开文件对象file。
    }

    public static void openRecordTemplate()
    {
        //复制源文件到临时文件再打开，可保证源文件不被修改
        try {
            FileUtils.copyFile(new File(recordSourcePath),new File(recordTempPath));
            openFile(recordTempPath);
        } catch (IOException e) {
            AlertUtil.error("无法打开文件，请重试");
        }
    }

    public static void openTripTemplate()
    {
        //复制源文件到临时文件再打开，可保证源文件不被修改
        try {
            FileUtils.copyFile(new File(tripSourcePath),new File(tripTempPath));
            openFile(tripTempPath);
        } catch (IOException e) {
            AlertUtil.error("无法打开文件，请重试");
        }
    }

}
