package com.example.bite;
import java.util.*;
public class admin_manage_order {
    private Scanner opt = new Scanner(System.in);
    public void order_manageM()
    {
        while(true) {
            System.out.println("\n----Admin Order Management----");
            System.out.println("1. View Pending Orders");
            System.out.println("2. Update Order Status");
            System.out.println("3. Process Refunds");
            System.out.println("4. Handle Special Requests");
            System.out.println("5. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            int choice = opt.nextInt();
            opt.nextLine();
            switch(choice) {
                case 5:
                    return;
                case 1:
                    pending_O();
                    break;
                case 2:
                    updatee_statuss();
                    break;
                case 3:
                    processRefunds();
                    break;
                case 4:
                    handleSR();
                    break;
                default:
                    System.out.println("try again");
            }
        }
    }

    private void pending_O()
    {
        System.out.println("\n----Pending Orders----");
        Map<Integer, Order> allOrders = ManageOrder.getAllOrders();
        List<Order> pendingOrders = new ArrayList<>();
        for (Order order : allOrders.values()) {
            if (isPending(order))
            {
                pendingOrders.add(order);
            }
        }

        if (pendingOrders.isEmpty())
        {
            System.out.println("no pending orders found!");
            return;
        }
        Collections.sort(pendingOrders, new Opriority()); //here we sort vip firtt :p
        System.out.println("\n VIP PRIORITY ORDERS: ");
        System.out.println(String.format("%-8s %-15s %-20s %-10s %-10s %s", "OrderID", "Status", "Location", "Customer", "Amount", "Items"));
        boolean has_VIP = false;
        boolean has_Regular = false;
        for (Order order : pendingOrders)
        {
            if (order.getCustomerT()!=null && order.getCustomerT().equals("VIP"))
            {
                porder_Deets(order);
                has_VIP = true;
            }
        }

        if (!has_VIP)
        {
            System.out.println("no pending vIP orders");
        }
        System.out.println("\nREGULAR ORDERS: ");
        System.out.println(String.format("%-8s %-15s %-20s %-10s %-10s %s", "OrderID", "Status", "Location", "Customer", "Amount", "Items"));

        for (Order order : pendingOrders)
        {
            if (order.getCustomerT()==null ||!order.getCustomerT().equals("VIP"))
            {
                porder_Deets(order);
                has_Regular = true;
            }
        }

        if (!has_Regular)
        {
            System.out.println("noo pending regular orders");
        }
    }
    private boolean isPending(Order order) {
        return order.getStatus().equals(Order.STATUS_RECEIVED)||order.getStatus().equals(Order.STATUS_PREPARING)||order.getStatus().equals(Order.STATUS_READY);
    }

    private void porder_Deets(Order order)
    {
        System.out.println(String.format("%-8d %-15s %-20s %-10s $%-9.2f %s",order.getOrderId(),order.getStatus(),order.getHostel() + " " + order.getroomN(),order.getCustomerT(),order.getTamount(),getsummaryy(order)));
    }

    private void updatee_statuss() {
        pending_O();
        System.out.println("\n----Update Order Status----");
        System.out.print("Enter Order ID: ");
        int orderId = opt.nextInt();
        opt.nextLine();
        Order order = ManageOrder.getOrder(orderId);
        if (order != null)
        {
            System.out.println("\nur current order details:");
            System.out.println(order);
            if (!order.getCustomerT().equals("VIP"))
            {
                List<Order> pendingVIPo = getpVIpO();
                if (!pendingVIPo.isEmpty()) {
                    System.out.println("\nthere are pending VIP orders that should be processed first:");
                    for (Order vipOrder : pendingVIPo) {
                        System.out.println("Order #" + vipOrder.getOrderId() + " - " + vipOrder.getStatus());
                    }
                    System.out.print("\ndo you still want to update this regular order?(y/n): ");
                    if (!opt.nextLine().trim().toLowerCase().equals("y"))
                    {
                        return;
                    }
                }
            }

            System.out.println("\nAvailable Statuses:");
            System.out.println("1. Order Received");
            System.out.println("2. Preparing");
            System.out.println("3. Ready for Delivery");
            System.out.println("4. Delivered");
            System.out.println("5. Denied");
            System.out.println("6. Cancelled");
            System.out.print("Select new status (1-6): ");
            int statusChoice = opt.nextInt();
            opt.nextLine();
            String newStatus;
            switch(statusChoice)
            {
                case 1: newStatus = Order.STATUS_RECEIVED; break;
                case 2: newStatus = Order.STATUS_PREPARING; break;
                case 3: newStatus = Order.STATUS_READY; break;
                case 4: newStatus = Order.STATUS_DELIVERED; break;
                case 5: newStatus = Order.STATUS_DENIED; break;
                case 6: newStatus = Order.STATUS_CANCELLED; break;
                default:
                    System.out.println("Invalid choice!");
                    return;
            }

            order.set_status(newStatus);
            System.out.println("Order status updated to: " + newStatus);
        } else
        {
            System.out.println("order not found!");
        }
    }

    private List<Order> getpVIpO()
    {
        List<Order> pendingVIPo = new ArrayList<>();
        for (Order order : ManageOrder.getAllOrders().values())
        {
            if (isPending(order) && order.getCustomerT().equals("VIP"))
            {
                pendingVIPo.add(order);
            }
        }
        return pendingVIPo;
    }
    private String getsummaryy(Order order)
    {
        StringBuilder summary = new StringBuilder();
        List<OrderItem> items = order.getOrderItems();
        for (OrderItem item : items) {
            summary.append(item.getQuantity()).append("x ").append(item.getItem().getName()).append(", ");
        }
        if (summary.length() >2)
        {
            summary.setLength(summary.length()-2);
        }
        return summary.toString();
    }

    private void processRefunds()
    {
        System.out.println("\n----Process Refunds----");
        Map<Integer, Order> allOrders = ManageOrder.getAllOrders();
        List<Order> cancelledO = new ArrayList<>();
        for (Order order : allOrders.values())
        {
            if (order.getStatus().equals(Order.STATUS_CANCELLED))
            {
                cancelledO.add(order);
            }
        }

        if (cancelledO.isEmpty())
        {
            System.out.println("no cancelled orders were found :( ");
            return;
        }
        System.out.println("\nCANCELLED ORDERS:");
        System.out.println(String.format("%-8s %-20s %-10s %-10s %s","OrderID", "Location", "Customer", "Amount", "Items"));
        for (Order order : cancelledO)
        {
            System.out.println(String.format("%-8d %-20s %-10s $%-9.2f %s", order.getOrderId(),order.getHostel() + " " + order.getroomN(),order.getCustomerT(),order.getTamount(),getsummaryy(order)));
        }
        System.out.print("\nenter Order ID to process refund: ");
        int orderId = opt.nextInt();
        opt.nextLine();

        Order selectedOrder = ManageOrder.getOrder(orderId);
        if (selectedOrder != null && selectedOrder.getStatus().equals(Order.STATUS_CANCELLED))
        {
            System.out.println("\nprocessing refund for Order #" + orderId);
            System.out.println("Amount to be refunded: $" + String.format("%.2f", selectedOrder.getTamount()));
            System.out.print("\nconfirm refund processing? (y/n): ");
            String confirm = opt.nextLine().trim();
            if (confirm.equals("y")) {
                System.out.println("\nRefund processed successfully!");
                System.out.println("amount of $" + String.format("%.2f", selectedOrder.getTamount()) + " has been refunded to the customer.");
            } else {
                System.out.println("\nRefund processing cancelled.");
            }
        } else {
            System.out.println("order not cancel. try again");
        }
    }

    private void handleSR()
    {
        System.out.println("\n----Handle Special Requests----");
        Map<Integer, Order> allOrders = ManageOrder.getAllOrders();
        List<Order> requests_order = new ArrayList<>();
        for (Order order : allOrders.values()) {
            if (order.getspecialR()!= null&&!order.getspecialR().isEmpty()&&!order.getspecialRH()) {
                requests_order.add(order);
            }
        }

        if (requests_order.isEmpty())
        {

            System.out.println("no pending special requests to handle!");
            return;
        }

        System.out.println("\nORDERS WITH SPECIAL REQUESTS:");
        System.out.println(String.format("%-8s %-15s %-20s %-10s %s", "OrderID", "Status", "Location", "Customer", "Special Request"));
        for (Order order : requests_order)
        {
            System.out.println(String.format("%-8d %-15s %-20s %-10s %s", order.getOrderId(),order.getStatus(),order.getHostel() + " " + order.getroomN(), order.getCustomerT(),order.getspecialR()));
        }
        System.out.print("\nenter Order ID to handle special request or 0 to exit): ");
        int orderId = opt.nextInt();
        opt.nextLine();
        if (orderId ==0) {
            return;
        }
        Order selectedOrder = ManageOrder.getOrder(orderId);
        if (selectedOrder!=null&& !selectedOrder.getspecialR().isEmpty())
        {
            System.out.println("\nOrder #" + orderId + " Special Request:");
            System.out.println(selectedOrder.getspecialR());
            System.out.print("\nmark this request as handled? (y/n): ");
            String confirm = opt.nextLine().trim().toLowerCase();
            if (confirm.equals("y")) {
                selectedOrder.setspeialRH(true);
                System.out.println("special request is handled");
            } else {
                System.out.println("request is still pending.");
            }
        } else {
            System.out.println("no request was found for this ID SRY :) ");
        }
    }
}