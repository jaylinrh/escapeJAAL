module com.escape {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires freetts;
    requires junit;


    opens com.escape to javafx.fxml;
    opens com.escape.Model to junit;
    exports com.escape;
    exports com.escape.Model;
}