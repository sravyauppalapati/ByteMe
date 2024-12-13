package com.example.bite;
import java.util.*;
public class Sale_Report
{
    private Map<String, Integer> itemsSold = new HashMap<>();
    private int Torderss = 0;
    private int vipOr = 0;
    private int regularOr = 0;
    private double totalR = 0.0;

    public void generateRp()
    {
        Map<Integer, Order> allOrders = ManageOrder.getAllOrders();
        itemsSold.clear(); //resetted all thr counters
        Torderss = 0;
        vipOr = 0;
        regularOr = 0;
        totalR = 0.0;
        for (Order order : allOrders.values())
        {
            processO(order);
        }
        reportD();
    }

    private void processO(Order order)
    {
        Torderss++;
        totalR += order.getTamount();
        if (order.getCustomerT()!=null&& order.getCustomerT().equals("VIP")) {
            vipOr++;
        }
        else {
            regularOr++;
        }
        for (OrderItem orderItem :order.getOrderItems())
        {
            String item_name = orderItem.getItem().getName();
            int quantity = orderItem.getQuantity();
            itemsSold.merge(item_name, quantity, Integer::sum);
        }
    }
    private void reportD() {
        System.out.println("DAILY SALES REPORT:");
        System.out.println("\ntotal Orders: " + Torderss);
        System.out.println("total Revenue: $" + String.format("%.2f", totalR));
        System.out.println("vip Orders: " + vipOr);
        System.out.println("Regular Orders: " + regularOr);
        System.out.println("Average Order Value: $" +String.format("%.2f", Torderss > 0 ? totalR/Torderss : 0));
        String popularI = itemsSold.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("No items sold");
        int quantitySold = itemsSold.getOrDefault(popularI, 0);
        System.out.println("\nMost Popular Item: "+ popularI+" (Sold: " + quantitySold + " times)");
    }
}
