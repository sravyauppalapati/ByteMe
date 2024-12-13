package com.example.bite;
import java.util.*;
class OrderT
{
    private Scanner opt = new Scanner(System.in);
    private String Tcustomer;

    public void order_trackk()
    {
        while(true) {
            System.out.println("\n----Order Tracking Menu----");
            System.out.println("1. View Order Status");
            System.out.println("2. Cancel Order");
            System.out.println("3. Order History");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = opt.nextInt();
            opt.nextLine();
            switch(choice) {
                case 4:
                    return;
                case 1:
                    view_staus();
                    break;
                case 2:
                    order_cancel();
                    break;
                case 3:
                    view_Historyy();
                    break;
                default:
                    System.out.println("try again");
            }
        }
    }

    private void view_staus() {
        System.out.println("\n----Order Status----");
        System.out.print("enter Order ID: ");
        int orderId = opt.nextInt();
        opt.nextLine();
        Order order = ManageOrder.getOrder(orderId);
        if (order != null)
        {
            System.out.println(order);
        } else {
            System.out.println("sry order not found!");
        }
    }
    private void order_cancel()
    {
        System.out.println("\n----Cancel Order----");
        System.out.print("Enter Order ID: ");
        int orderId = opt.nextInt();
        opt.nextLine();
        Order order = ManageOrder.getOrder(orderId);
        if (order == null)
        {
            System.out.println("sry order not found!");
            return;
        }
        if (order.canCancel())
        {
            order.set_status(Order.STATUS_CANCELLED);
            System.out.println("order #" + orderId + " has been cancelled :) ");
        } else {
            System.out.println("sorry, this order cannot be cancelled!");
            System.out.println("orders can only be cancelled when they are in '" + Order.STATUS_RECEIVED + "' status.");
            System.out.println("the current order status: " + order.getStatus());
        }
    }
    private void view_Historyy() {
        System.out.println("\n----Order History----");
        System.out.print("please enter your room number: ");
        String room_no = opt.nextLine().trim();

        List<Order> roomOrders = ManageOrder.getOrdersByRoom(room_no);
        if (roomOrders.isEmpty()) {
            System.out.println("no orders found from this room");
            return;
        }

        System.out.println("\nOrder history for Room " + room_no + ":");
        for (Order order : roomOrders) {
            order_deets(order);
            System.out.println("------------------------------------------------");
        }

        System.out.print("\nwould you like to reorder any of these orders? (Enter Order ID or 'N' to skip): ");
        String input = opt.nextLine().trim();
        if (!input.equalsIgnoreCase("N")) {
            try {
                int reorderId = Integer.parseInt(input);
                reorderr(reorderId);
            } catch (NumberFormatException e) {
                System.out.println("wrong input. Returning to menu.");
            }
        }
    }

    private void order_deets(Order order)
    {
        System.out.println("\nOrder #" + order.getOrderId());
        System.out.println("Status: " + order.getStatus());
        System.out.println("Customer Type: " + order.getCustomerT());
        System.out.println("Hostel: " + order.getHostel());
        System.out.println("Room Number: " + order.getroomN());
        System.out.println("Total Amount: $" + String.format("%.2f", order.getTamount()));
    }

    private void reorderr(int orderId)
    {
        Order originalOrder = ManageOrder.getOrder(orderId);
        if (originalOrder == null) {
            System.out.println("sry order not found");
            return;
        }
        Order nOrder = new Order(originalOrder);
        ManageOrder.addOrder(nOrder);
        System.out.println("Order #" + orderId + " has been reordered yay!");
        System.out.println("ur new Order ID!!: " + nOrder.getOrderId());
        order_deets(nOrder);
    }
}
