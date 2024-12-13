package com.example.bite;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileHandler
{
    private static final String ORDER_HISTORY_DIR = "order_histories/";
    private static final String CART_FILE = "temp/cart_";
    static
    {
        new File(ORDER_HISTORY_DIR).mkdirs();
        new File("temp").mkdirs();
    }


    public static void saveOrdeh(Order order)
    {
        String roomNo = order.getroomN();
        String filename = ORDER_HISTORY_DIR + "room_" + roomNo + "_history.txt";
        try (FileWriter fw = new FileWriter(filename, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            out.println("\nOrder" +order.getOrderId() );
            out.println("Date: " + now.format(formatter));
            out.println("Customer Type: " + order.getCustomerT());
            out.println("Hostel: " + order.getHostel());
            out.println("Status: " + order.getStatus());
            out.println("\nordered Items:");
            for (OrderItem item : order.getOrderItems()) {
                out.printf("%dx %s - $%.2f each (Total: $%.2f)\n", item.getQuantity(), item.getItem().getName(), item.getPriceAtOrder(), item.getQuantity() * item.getPriceAtOrder());
            }

            out.printf("\ntotal Amount: $%.2f\n", order.getTamount());

            if (order.getspecialR() != null && !order.getspecialR().isEmpty())
            {
                out.println("special request: " + order.getspecialR());
            }



        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void saveCart(String roomNo, Map<FoodItem, Integer> cart) {
        String filename = CART_FILE + roomNo + ".txt";

        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (Map.Entry<FoodItem, Integer> entry : cart.entrySet()) {
                FoodItem item = entry.getKey();
                int quantity = entry.getValue();
                out.printf("%s,%d,%.2f\n",item.getName(), quantity, item.getPrice());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Map<FoodItem, Integer> loadCart(String roomNo, Menu menu) {
        String filename = CART_FILE + roomNo + ".txt";
        Map<FoodItem, Integer> cart = new HashMap<>();
        File cartFile = new File(filename);
        if (!cartFile.exists())
        {
            return cart;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String itemName = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    FoodItem item = menu.findItem(itemName);
                    if (item != null) {
                        cart.put(item, quantity);
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

        return cart;
    }


    public static void Dcart(String roomNo)
    {
        String filename = CART_FILE + roomNo + ".txt";
        File cartFile = new File(filename);
        if (cartFile.exists())
        {
            cartFile.delete();

        }
    }


    public static void viewORDER(String roomNo) {
        String filename = ORDER_HISTORY_DIR + "room_" + roomNo + "_history.txt";
        File historyFile = new File(filename);

        if (!historyFile.exists()) {
            System.out.println("No order history found for room " + roomNo);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println( e.getMessage());
        }
    }
}