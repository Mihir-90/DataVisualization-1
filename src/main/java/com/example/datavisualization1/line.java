package com.example.datavisualization1;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class line extends Application {

    private final ObservableList<ItemData> itemDataList = FXCollections.observableArrayList();
    private final LineChart<Number, Number> lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Enter data for the line chart:");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        TextField nameInput = new TextField();
        nameInput.setStyle("-fx-font-size: 14px;");

        TextField dataInput = new TextField();
        dataInput.setStyle("-fx-font-size: 14px;");

        Button addButton = createStyledButton("Add Data");

        lineChart.setStyle("-fx-background-color: #2E2E2E;");

        addButton.setOnAction(event -> {
            try {
                String itemName = nameInput.getText();
                double dataValue = Double.parseDouble(dataInput.getText());

                // Find or create a series for the item name
                XYChart.Series<Number, Number> series = findOrCreateSeries(itemName);
                int xValue = series.getData().size() + 1; // X value based on current data count

                // Create a new data point and add it to the series
                XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(xValue, dataValue);
                series.getData().add(dataPoint);

                // Create and position the value label
                Text valueLabel = new Text(String.valueOf(dataValue));
                valueLabel.setStyle("-fx-fill: white; -fx-font-size: 12px;");
                valueLabel.setTranslateY(-15); // Position above the data point

                // Add listener to position the label when the data point is rendered
                dataPoint.nodeProperty().addListener((obs, oldNode, newNode) -> {
                    if (newNode != null) {
                        newNode.layoutBoundsProperty().addListener((boundsObs, oldBounds, newBounds) -> {
                            valueLabel.setTranslateX(newBounds.getMinX());
                            valueLabel.setTranslateY(newBounds.getMinY() - 20); // Adjust position above the point
                        });

                        // Add the label to the chart
                        lineChart.getParent().getChildrenUnmodifiable().add(valueLabel);

                        newNode.setOnMouseExited(e -> lineChart.getParent().getChildrenUnmodifiable().remove(valueLabel));
                    }
                });

                // Add item data to the observable list
                ItemData itemData = new ItemData(itemName, dataValue);
                itemDataList.add(itemData);

                nameInput.clear();
                dataInput.clear();
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        });

        // Labels for input fields
        Label itemNameLabel = new Label("Item Name:");
        itemNameLabel.setStyle("-fx-text-fill: white;");

        Label valueLabel = new Label("Value:");
        valueLabel.setStyle("-fx-text-fill: white;");

        VBox root = new VBox(10, label, new HBox(10, itemNameLabel, nameInput),
                new HBox(10, valueLabel, dataInput), addButton, lineChart);
        root.setStyle("-fx-padding: 20px; -fx-background-color: #2E2E2E; -fx-font-family: Cambria; -fx-text-fill: white;");

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("LINE CHART GENERATOR");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private XYChart.Series<Number, Number> findOrCreateSeries(String itemName) {
        for (XYChart.Series<Number, Number> series : lineChart.getData()) {
            if (series.getName().equals(itemName)) {
                return series; // Return existing series
            }
        }

        // Create a new series if it doesn't exist
        XYChart.Series<Number, Number> newSeries = new XYChart.Series<>();
        newSeries.setName(itemName);
        lineChart.getData().add(newSeries);
        return newSeries;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 14px; -fx-background-color: #140D4F; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 2);");

        button.setOnMouseEntered(event -> button.setStyle("-fx-font-size: 14px; -fx-background-color: #1A0D4F; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 2);"));
        button.setOnMouseExited(event -> button.setStyle("-fx-font-size: 14px; -fx-background-color: #140D4F; -fx-text-fill: white; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 2);"));

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class ItemData {
        private final SimpleStringProperty itemName;
        private final SimpleDoubleProperty itemValue;

        public ItemData(String itemName, double itemValue) {
            this.itemName = new SimpleStringProperty(itemName);
            this.itemValue = new SimpleDoubleProperty(itemValue);
        }

        public String getItemName() {
            return itemName.get();
        }

        public SimpleStringProperty itemNameProperty() {
            return itemName;
        }

        public double getItemValue() {
            return itemValue.get();
        }

        public SimpleDoubleProperty itemValueProperty() {
            return itemValue;
        }
    }
}
