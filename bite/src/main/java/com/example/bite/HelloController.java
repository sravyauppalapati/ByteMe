package com.example.bite;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public class HelloController {
    @FXML
    private TabPane mainTabPane;

    // Menu Table Components
    @FXML
    private TableView<FoodItem> menuTable;
    @FXML
    private TableColumn<FoodItem, String> nameColumn;
    @FXML
    private TableColumn<FoodItem, String> categoryColumn;
    @FXML
    private TableColumn<FoodItem, Double> priceColumn;
    @FXML
    private TableColumn<FoodItem, String> availabilityColumn;

    // Orders Table Components
    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, Integer> orderIdColumn;
    @FXML
    private TableColumn<Order, String> statusColumn;
    @FXML
    private TableColumn<Order, String> customerColumn;
    @FXML
    private TableColumn<Order, String> locationColumn;
    @FXML
    private TableColumn<Order, String> itemsColumn;

    @FXML
    private Button refreshButton;

    @FXML
    public void initialize() {
        setupMenuTable();
        setupOrdersTable();

        // Setup refresh button action
        refreshButton.setOnAction(event -> refreshAllData());

        // Initial data load
        refreshAllData();
    }

    private void setupMenuTable() {
        nameColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        categoryColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getCategory()));
        priceColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrice()).asObject());
        availabilityColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().isAvailable() ? "Available" : "Not Available"));
    }

    private void setupOrdersTable() {
        orderIdColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(data.getValue().getOrderId()).asObject());
        statusColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getStatus()));
        customerColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getCustomerT()));
        locationColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getHostel() + " - Room " + data.getValue().getroomN()));
        itemsColumn.setCellValueFactory(data -> {
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
    }

    private void refreshMenuData() {
        Menu menu = Admin.getMenu();
        ObservableList<FoodItem> menuItems = FXCollections.observableArrayList(menu.getAllItems());
        menuTable.setItems(menuItems);
    }

    private void refreshOrdersData() {
        ObservableList<Order> pendingOrders = FXCollections.observableArrayList();
        for (Order order : ManageOrder.getAllOrders().values()) {
            if (isPending(order)) {
                pendingOrders.add(order);
            }
        }
        ordersTable.setItems(pendingOrders);
    }

    private boolean isPending(Order order) {
        String status = order.getStatus();
        return status.equals(Order.STATUS_RECEIVED) ||
                status.equals(Order.STATUS_PREPARING) ||
                status.equals(Order.STATUS_READY);
    }

    @FXML
    private void refreshAllData() {
        refreshMenuData();
        refreshOrdersData();
    }
}