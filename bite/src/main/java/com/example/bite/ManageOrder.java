package com.example.bite;

import java.io.*;
import java.util.*;

class ManageOrder {
    private static Map<Integer, Order> orders = new HashMap<>();
    private static int nextOrderId = 1;
    private static final String ORDERS_DIR = "orders/";

    static {
        new File(ORDERS_DIR).mkdirs();
        loadOrders();
    }

    public static void addOrder(Order order) {
        orders.put(order.getOrderId(), order);
        saveOrder(order);
    }

    private static void saveOrder(Order order) {
        String fileName = ORDERS_DIR + "order_" + order.getOrderId() + ".txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            // Write basic order info
            out.println("OrderID:" + order.getOrderId());
            out.println("Status:" + order.getStatus());
            out.println("CustomerType:" + order.getCustomerT());
            out.println("Hostel:" + order.getHostel());
            out.println("RoomNo:" + order.getroomN());
            out.println("TotalAmount:" + order.getTamount());

            // Write order items
            out.println("Items:");
            for (OrderItem item : order.getOrderItems()) {
                out.printf("%s,%d,%.2f\n",
                        item.getItem().getName(),
                        item.getQuantity(),
                        item.getPriceAtOrder());
            }

            // Write special request if any
            if (order.getspecialR() != null && !order.getspecialR().isEmpty()) {
                out.println("SpecialRequest:" + order.getspecialR());
            }
        } catch (IOException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }

        // Also save order ID to room history file
        String roomFileName = ORDERS_DIR + "room_" + order.getroomN() + "_orders.txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(roomFileName, true))) {
            out.println(order.getOrderId());
        } catch (IOException e) {
            System.out.println("Error updating room history: " + e.getMessage());
        }
    }

    private static void loadOrders() {
        File ordersDir = new File(ORDERS_DIR);
        if (!ordersDir.exists()) return;

        for (File file : ordersDir.listFiles()) {
            if (file.getName().startsWith("order_") && file.getName().endsWith(".txt")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    Order order = parseOrderFile(br);
                    if (order != null) {
                        orders.put(order.getOrderId(), order);
                        nextOrderId = Math.max(nextOrderId, order.getOrderId() + 1);
                    }
                } catch (IOException e) {
                    System.out.println("Error loading order: " + e.getMessage());
                }
            }
        }
    }

    private static Order parseOrderFile(BufferedReader br) throws IOException {
        String line;
        Map<String, String> orderInfo = new HashMap<>();
        List<OrderItem> items = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            if (line.startsWith("Items:")) {
                // Read items until end of file or new section
                while ((line = br.readLine()) != null && !line.contains(":")) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String itemName = parts[0];
                        int quantity = Integer.parseInt(parts[1]);
                        double price = Double.parseDouble(parts[2]);
                        FoodItem foodItem = Admin.getMenu().findItem(itemName);
                        if (foodItem != null) {
                            items.add(new OrderItem(foodItem, quantity));
                        }
                    }
                }
            }
            if (line != null && line.contains(":")) {
                String[] parts = line.split(":", 2);
                orderInfo.put(parts[0], parts[1]);
            }
        }

        if (orderInfo.containsKey("OrderID")) {
            Order order = new Order(
                    orderInfo.get("Hostel"),
                    orderInfo.get("RoomNo"),
                    Double.parseDouble(orderInfo.get("TotalAmount")),
                    orderInfo.get("CustomerType"),
                    orderInfo.getOrDefault("SpecialRequest", "")
            );
            for (OrderItem item : items) {
                order.add_orderI(item);
            }
            order.set_status(orderInfo.get("Status"));
            return order;
        }
        return null;
    }

    public static Order getOrder(int orderId) {
        return orders.get(orderId);
    }

    public static Map<Integer, Order> getAllOrders() {
        return orders;
    }

    public static List<Order> getOrdersByRoom(String roomNo) {
        List<Order> roomOrders = new ArrayList<>();
        String roomFileName = ORDERS_DIR + "room_" + roomNo + "_orders.txt";
        File roomFile = new File(roomFileName);

        if (roomFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(roomFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    int orderId = Integer.parseInt(line.trim());
                    Order order = orders.get(orderId);
                    if (order != null) {
                        roomOrders.add(order);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading room orders: " + e.getMessage());
            }
        }
        return roomOrders;
    }

    public static void updatee_statuss(int orderId, String newStatus) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.set_status(newStatus);
            saveOrder(order);
        }
    }
}