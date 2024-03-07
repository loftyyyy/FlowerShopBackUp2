module com.example.fs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.validator;
    requires org.apache.commons.lang3;


    opens com.example.fs to javafx.fxml;
    exports com.example.fs;
}