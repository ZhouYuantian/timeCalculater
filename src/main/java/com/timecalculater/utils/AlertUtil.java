package com.timecalculater.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtil {
    private static Alert alert=new Alert(Alert.AlertType.NONE);
    public static void info(String text)
    {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        alert.headerTextProperty().set(text);
        alert.showAndWait();
    }
    public static void warning(String text)
    {
        alert.setAlertType(Alert.AlertType.WARNING);
        alert.titleProperty().set("警告");
        alert.headerTextProperty().set(text);
        alert.showAndWait();
    }
    public static void erro(String text)
    {
        alert.setAlertType(Alert.AlertType.WARNING);
        alert.titleProperty().set("错误");
        alert.headerTextProperty().set(text);
        alert.showAndWait();
    }
    public static boolean confirm(String text)
    {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.titleProperty().set("确认");
        alert.headerTextProperty().set(text);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
