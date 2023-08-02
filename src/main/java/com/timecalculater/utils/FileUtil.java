package com.timecalculater.utils;

import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class FileUtil {
    static String recordSourcePath="src/main/resources/com/timecalculater/excel/template/record_template.xlsx";
    static String recordTempPath="src/main/resources/com/timecalculater/excel/temporary/record_template.xlsx";
    static void openFile(String filePath)
    {
        try {
            File file = new File(filePath); // 创建文件对象，路径为filePath
            Desktop.getDesktop().open(file); // 启动已在本机桌面上注册的关联应用程序，打开文件对象file。
        } catch (IOException | NullPointerException e) { // 异常处理
            AlertUtil.warning("无法打开文件，请重试");
        }
    }

    public static void openRecordTemplate()
    {
        //复制源文件到临时文件再打开，可保证源文件不被修改
        try {
            FileUtils.copyFile(new File(recordSourcePath),new File(recordTempPath));
        } catch (IOException e) {
            AlertUtil.warning("无法打开文件，请重试");
        }
        openFile(recordTempPath);
    }

}