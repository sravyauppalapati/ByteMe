package com.example.bite;
import java.util.*;
import java.io.Serializable;
public class Order
{
    private static int nextOrderId = 1;
    private int orderId;
    private String status;
    private String hostel;
    private String room_no;
    private double Tamount;
    private String Tcustomer;
    private List<OrderItem> items;
    private String specialR;
    private boolean specialRequestHandled;
    private static final long serialVersionUID = 1L;

    public static final String STATUS_RECEIVED = "Order Received";
    public static final String STATUS_PREPARING = "Preparing";
    public static final String STATUS_READY = "Ready for Delivery";
    public static final String STATUS_DELIVERED = "Delivered";
    public static final String STATUS_CANCELLED = "Cancelled";
    public static final String STATUS_DENIED = "Denied";
    public Order(String hostel, String room_no, double Tamount, String Tcustomer, String specialR)
    {
        this.orderId = nextOrderId++;
        this.status = STATUS_RECEIVED;
        this.hostel = hostel;
        this.room_no = room_no;
        this.Tamount = Tamount;
        this.Tcustomer = Tcustomer;
        this.items = new ArrayList<>();
        this.specialR = specialR;
        this.specialRequestHandled = false;
    }
    public Order(Order original)
    {
        this.orderId = nextOrderId++;
        this.status = STATUS_RECEIVED;
        this.hostel = original.hostel;
        this.room_no = original.room_no;
        this.Tamount = original.Tamount;
        this.Tcustomer = original.Tcustomer;
        this.items = new ArrayList<>();
        this.specialR = original.specialR;
        this.specialRequestHandled = false;

    }
    public Order(String hostel, String room_no, double Tamount, String Tcustomer)
    {
        this(hostel, room_no, Tamount, Tcustomer, "");
    }


    public boolean canCancel()
    {
        return this.status.equals(STATUS_RECEIVED);
    }

    public void add_orderI(OrderItem item) {
        items.add(item);
    }
    public List<OrderItem> getOrderItems() {
        return items;
    }
    public boolean containsItem(FoodItem item) {
        return items.stream().anyMatch(orderItem->orderItem.getItem().getName().equalsIgnoreCase(item.getName()));
    }
    public int getOrderId() {
        return orderId;
    }
    public String getStatus() {
        return status; }
    public String getHostel() {
        return hostel;
    }
    public String getroomN() {
        return room_no; }
    public double getTamount() {
        return Tamount; }
    public String getCustomerT() {
        return Tcustomer;
    }
    public void set_status(String status) {
        this.status = status; }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("order #%d - Status: %s\n", orderId, status));
        sb.append(String.format("delivery to: %s Room %s\n", hostel, room_no));
        sb.append("items:\n");
        for (OrderItem item : items) {
            sb.append(String.format("- %dx %s ($%.2f each)\n",item.getQuantity(), item.getItem().getName(),item.getPriceAtOrder()));
        }
        sb.append(String.format("Total Amount: $%.2f", Tamount));
        return sb.toString();
    }

    public String getspecialR() {
        return specialR;
    }

    public boolean getspecialRH() {
        return specialRequestHandled;
    }
    public void setspeialRH(boolean handled)
    {
        this.specialRequestHandled = handled;
    }
}
