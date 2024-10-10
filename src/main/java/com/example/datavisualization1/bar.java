package com.example.datavisualization1;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class bar extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Enter data for the bar chart:");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #E0E0E0;");
        TextField nameInput = new TextField();
        nameInput.setStyle("-fx-font-size: 14px; -fx-background-color: #333333; -fx-text-fill: white;");

        TextField dataInput = new TextField();
        dataInput.setStyle("-fx-font-size: 14px; -fx-background-color: #333333; -fx-text-fill: white;");

        Button addButton = createStyledButton("Add Data");
        BarChart<String, Number> barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        barChart.setStyle("-fx-background-color: #212121;");

        TableView<ItemData> tableView = new TableView<>();
        tableView.setStyle("-fx-background-color: #333333; -fx-text-fill: #E0E0E0;");

        TableColumn<ItemData, String> itemNameCol = new TableColumn<>("Item Name");
        itemNameCol.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());

        TableColumn<ItemData, Number> itemValueCol = new TableColumn<>("Value");
        itemValueCol.setCellValueFactory(cellData -> cellData.getValue().itemValueProperty());

        tableView.getColumns().addAll(itemNameCol, itemValueCol);

        ObservableList<ItemData> itemDataList = FXCollections.observableArrayList();

        addButton.setOnAction(event -> {
            try {
                String itemName = nameInput.getText();
                double dataValue = Double.parseDouble(dataInput.getText());

                boolean itemExists = itemDataList.stream().anyMatch(item -> item.getItemName().equals(itemName));
                if (!itemExists) {
                    ItemData itemData = new ItemData(itemName, dataValue);
                    itemDataList.add(itemData);
                    tableView.setItems(itemDataList);

                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    series.getData().add(new XYChart.Data<>(itemName, dataValue));
                    barChart.getData().add(series);
                } else {
                    System.out.println("Item already exists. Please enter a unique item name.");
                }

                nameInput.clear();
                dataInput.clear();
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        });

        VBox root = new VBox(10, label,
                new HBox(10, new Label("Item Name:") {{
                    setTextFill(javafx.scene.paint.Color.WHITE);
                }}, nameInput),
                new HBox(10, new Label("Value:") {{
                    setTextFill(javafx.scene.paint.Color.WHITE);
                }}, dataInput), addButton, tableView, barChart);
        root.setStyle("-fx-padding: 20px; -fx-background-color: #212121; -fx-font-family: Cambria;");

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("BAR CHART GENERATOR");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 14px; -fx-background-color: #3A3D3F; -fx-text-fill: white; -fx-font-weight: bold;");
        button.setOnMouseEntered(event -> button.setStyle("-fx-font-size: 14px; -fx-background-color: #4A4D4F; -fx-text-fill: white; -fx-font-weight: bold;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-font-size: 14px; -fx-background-color: #3A3D3F; -fx-text-fill: white; -fx-font-weight: bold;"));
        return button;
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
