module com.example.bite {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bite to javafx.fxml;
    exports com.example.bite;
}