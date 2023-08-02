module com.timecalculater.timecalculater {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;
    requires poi.ooxml;
    requires poi;
    requires java.desktop;
    requires spring.context;
    requires spring.aop;
    requires spring.beans;
    requires spring.core;
    requires spring.expression;

    opens com.timecalculater to javafx.fxml;
    exports com.timecalculater;
    exports com.timecalculater.controller;
    opens com.timecalculater.controller to javafx.fxml;
}