package com.example.bite;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try
        {
            VBox root = new VBox(10);
            root.setPadding(new Insets(10));
            Button refreshButton = new Button("refresh");
            TabPane tabPane = new TabPane();
            TableView<FoodItem> menuTable = new TableView<>();
            setupMenuTable(menuTable);
            TableView<Order> ordersTable = new TableView<>();
            setupOrdersTable(ordersTable);
            Tab menuTab = new Tab("Menu");
            VBox menuBox = new VBox(10);
            menuBox.getChildren().addAll(new Label("Menu Items"), menuTable);
            menuTab.setContent(menuBox);
            menuTab.setClosable(false);
            Tab ordersTab = new Tab("Pending Orders");
            VBox ordersBox = new VBox(10);
            ordersBox.getChildren().addAll(new Label("Pending Orders"), ordersTable);
            ordersTab.setContent(ordersBox);
            ordersTab.setClosable(false);
            tabPane.getTabs().addAll(menuTab, ordersTab);
            root.getChildren().addAll(refreshButton, tabPane);
            refreshButton.setOnAction(e -> {
                refreshMenuData(menuTable);
                refreshOrdersData(ordersTable);
            });

            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Byte Me");
            stage.setScene(scene);
            stage.show();
            refreshMenuData(menuTable);
            refreshOrdersData(ordersTable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupMenuTable(TableView<FoodItem> menuTable) {
        TableColumn<FoodItem, String> nameCol = new TableColumn<>("Item Name");
        nameCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));

        TableColumn<FoodItem, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getCategory()));

        TableColumn<FoodItem, String> priceCol = new TableColumn<>("Price ($)");
        priceCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        String.format("%.2f", data.getValue().getPrice())));

        TableColumn<FoodItem, String> availabilityCol = new TableColumn<>("Availability");
        availabilityCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().isAvailable() ? "Available" : "Not Available"));

        menuTable.getColumns().addAll(nameCol, categoryCol, priceCol, availabilityCol);
    }

    private void setupOrdersTable(TableView<Order> ordersTable) {
        TableColumn<Order, Integer> orderIdCol = new TableColumn<>("Order ID");
        orderIdCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getOrderId()).asObject());

        TableColumn<Order, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));

        TableColumn<Order, String> customerCol = new TableColumn<>("Customer Type");
        customerCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getCustomerT()));

        TableColumn<Order, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getHostel() + " - Room " + data.getValue().getroomN()));

        TableColumn<Order, String> itemsCol = new TableColumn<>("Items");
        itemsCol.setCellValueFactory(data -> {
            StringBuilder items = new StringBuilder();
            for (OrderItem item : data.getValue().getOrderItems()) {
                items.append(item.getQuantity())
                        .append("x ")
                        .append(item.getItem().getName())
                        .append(", ");
            }
            String itemsList = items.toString();
            return new javafx.beans.property.SimpleStringProperty(
                    itemsList.length() > 2 ? itemsList.substring(0, itemsList.length() - 2) : "");
        });

        ordersTable.getColumns().addAll(orderIdCol, statusCol, customerCol, locationCol, itemsCol);
    }

    private void refreshMenuData(TableView<FoodItem> menuTable) {
        Menu menu = Admin.getMenu();
        menuTable.getItems().clear();
        menuTable.getItems().addAll(menu.getAllItems());
    }

    private void refreshOrdersData(TableView<Order> ordersTable) {
        ordersTable.getItems().clear();
        for (Order order : ManageOrder.getAllOrders().values()) {
            if (isPending(order)) {
                ordersTable.getItems().add(order);
            }
        }
    }

    private boolean isPending(Order order) {
        String status = order.getStatus();
        return status.equals(Order.STATUS_RECEIVED) ||
                status.equals(Order.STATUS_PREPARING) ||
                status.equals(Order.STATUS_READY);
    }

    public static void main(String[] args) {
        launch();
    }
}