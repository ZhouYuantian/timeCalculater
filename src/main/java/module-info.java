module com.timecalculater.timecalculater {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.timecalculater to javafx.fxml;
    exports com.timecalculater;
}