package com.example.datavisualization1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mainscreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Data Visualization Application");

        Label headerLabel1 = new Label("Data Visualization Tool");
        headerLabel1.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #E0E0E0; -fx-font-family: Georgia;");

        Label headerLabel2 = new Label("By Mihir Mohan Vathyam");
        headerLabel2.setStyle("-fx-font-size: 20px; -fx-text-fill: #E0E0E0;");

        Button pieButton = createStyledButton("Pie Chart");
        Button barButton = createStyledButton("Bar Chart");
        Button lineButton = createStyledButton("Line Chart");

        pieButton.setOnAction(event -> {
            pie pieChartExample = new pie();
            pieChartExample.start(new Stage());
        });

        barButton.setOnAction(event -> {
            bar barChartExample = new bar();
            barChartExample.start(new Stage());
        });

        lineButton.setOnAction(event -> {
            line lineChartExample = new line();
            lineChartExample.start(new Stage());
        });

        VBox buttonsVBox = new VBox(20, pieButton, barButton, lineButton);
        buttonsVBox.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        VBox headerVBox = new VBox(10, headerLabel1, headerLabel2);
        headerVBox.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        BorderPane root = new BorderPane();
        root.setTop(headerVBox);
        root.setCenter(buttonsVBox);
        root.setStyle("-fx-background-color: #212121; -fx-font-family: Cambria;");

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 18px; -fx-min-width: 300px; -fx-min-height: 60px; " +
                "-fx-background-color: #3A3D3F; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 2);");

        // Change color on hover
        button.setOnMouseEntered(event -> button.setStyle("-fx-font-size: 18px; -fx-min-width: 300px; " +
                "-fx-min-height: 60px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 2);"));

        button.setOnMouseExited(event -> button.setStyle("-fx-font-size: 18px; -fx-min-width: 300px; " +
                "-fx-min-height: 60px; -fx-background-color: #3A3D3F; -fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 2);"));

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
