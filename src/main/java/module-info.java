module com.example.tap2023_v {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tap2023 to javafx.fxml;
    exports com.example.tap2023;
}