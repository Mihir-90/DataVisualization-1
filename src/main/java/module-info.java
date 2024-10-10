module com.example.datavisualization1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.datavisualization1 to javafx.fxml;
    exports com.example.datavisualization1;
}