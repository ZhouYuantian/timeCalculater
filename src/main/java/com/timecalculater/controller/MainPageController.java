package com.timecalculater.controller;

import com.timecalculater.service.StatService;
import com.timecalculater.utils.AlertUtil;
import com.timecalculater.utils.FileUtil;
import com.timecalculater.utils.SpringUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainPageController {
    @FXML
    private TextField tfRecordsFilePath;
    @FXML
    private TextField tfTripFilePath;
    @FXML
    private TextField tfOutputDirectoryPath;

    @FXML
    public void onSelectRecordTable(ActionEvent actionEvent)
    {
        FileChooser chooser=new FileChooser();
        chooser.setTitle("打开文件");
        chooser.setInitialDirectory(new File("c:\\"));
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".xlsx文件","*.xlsx"));
        Stage stage=(Stage)tfRecordsFilePath.getScene().getWindow();
        File file=chooser.showOpenDialog(stage);
        if(file==null)
        {
            AlertUtil.warning("未选择任何文件");
        }
        else
        {
            tfRecordsFilePath.setText(file.getAbsolutePath());
        }
    }
    @FXML
    public void onViewRecordTemplate(ActionEvent actionEvent)
    {
        FileUtil.openRecordTemplate();
    }
    @FXML
    public void onSelectTripTable(ActionEvent actionEvent)
    {
        FileChooser chooser=new FileChooser();
        chooser.setTitle("打开文件");
        chooser.setInitialDirectory(new File("c:\\"));
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".xlsx文件","*.xlsx"));
        Stage stage=(Stage)tfTripFilePath.getScene().getWindow();
        File file=chooser.showOpenDialog(stage);
        if(file==null)
        {
            AlertUtil.warning("未选择任何文件");
        }
        else
        {
            tfTripFilePath.setText(file.getAbsolutePath());
        }
    }
    @FXML
    public void onViewTripTemplate(ActionEvent actionEvent) {
        FileUtil.openTripTemplate();
    }


    @FXML
    public void onSelectOutputDirectory(ActionEvent actionEvent)
    {
        DirectoryChooser chooser=new DirectoryChooser();
        chooser.setTitle("请选择文件夹");
        chooser.setInitialDirectory(new File("c:\\"));
        Stage stage=(Stage) tfOutputDirectoryPath.getScene().getWindow();
        File file=chooser.showDialog(stage);
        if(file==null){
            AlertUtil.warning("未选择任何文件夹");
        }else{
            tfOutputDirectoryPath.setText(file.getAbsolutePath());
        }
    }
    @FXML
    public void onExport(ActionEvent actionEvent)
    {
        String recordTablePath=tfRecordsFilePath.getText();
        String tripTablePath=tfTripFilePath.getText();
        String outputDirectory=tfOutputDirectoryPath.getText();
        if(recordTablePath==null||"".equals(recordTablePath))
        {
            AlertUtil.warning("请先选择考勤记录表文件");
            return;
        }
        if(tripTablePath==null||"".equals(tripTablePath))
        {
            AlertUtil.warning("请选择出差记录表文件");
            return;
        }
        if(outputDirectory==null||"".equals(outputDirectory))
        {
            AlertUtil.warning("请选择输出目录");
            return;
        }
        StatService statService= SpringUtil.getBean(StatService.class);
        statService.generateResults(recordTablePath,tripTablePath,outputDirectory);
        AlertUtil.info("导出成功");
    }

}
