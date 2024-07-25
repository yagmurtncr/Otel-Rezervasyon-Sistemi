module com.example.otelrezervasyonsistemi {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.otelrezervasyonsistemi to javafx.fxml;
    exports com.example.otelrezervasyonsistemi;
    exports com.example.otelrezervasyonsistemi.Menu;
    opens com.example.otelrezervasyonsistemi.Menu to javafx.fxml;

}