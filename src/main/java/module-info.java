open module com.timecalculater.timecalculater {
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
    requires commons.lang;
    requires guava;


    exports com.timecalculater;
    exports com.timecalculater.controller;

}