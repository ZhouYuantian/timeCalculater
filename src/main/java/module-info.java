module com.timecalculater.timecalculater {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;
    requires poi.ooxml;
    requires poi;
    requires java.desktop;

    opens com.timecalculater to javafx.fxml;
    exports com.timecalculater;
}