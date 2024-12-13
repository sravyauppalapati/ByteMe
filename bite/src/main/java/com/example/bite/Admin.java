package com.example.bite;
import java.util.*;
public class Admin {

    private static final String ADMIN_PASSWORD = "admin123";//final pass shh
    private static Menu menu = new Menu();
    private static admin_manage_order orderManagement = new admin_manage_order();
    private static Sale_Report Sale_Report = new Sale_Report();
    public static Menu getMenu() {
        return menu;
    }
    private static void manage_orderss()
    {
        orderManagement.order_manageM();
    }
    public static void adminLogin()
    {
        Scanner opt = new Scanner(System.in);
        System.out.print("Enter admin password: ");
        String paswordd = opt.nextLine();
        if (!paswordd.equals(ADMIN_PASSWORD))
        {
            System.out.println("wrong password :p");
            return;
        }
        while (true) {
            System.out.println("\n----ADMIN PAGE----");
            System.out.println("1. Manage Menu");
            System.out.println("2. Manage Order");
            System.out.println("3. Report Generation");
            System.out.println("4. Logout"); // Added option to logout
            System.out.print("Enter your choice: ");
            int choice = opt.nextInt();
            opt.nextLine();

            switch (choice)
            {
                case 4:
                    return;
                case 1:
                    manageMenu();;
                    break;
                case 2:
                    manage_orderss();
                    break;
                case 3:
                    Sale_Report.generateRp();
                    break;
                default:
                    System.out.println("try again");
            }
        }
    }
    private static void manageMenu()
    {
        Scanner opt = new Scanner(System.in);
        while(true) {
            System.out.println("\n----Manage Menu----");
            System.out.println("Current Menu Status:");
            menu.item_display();
            System.out.println("1. Add New Item");
            System.out.println("2. Update Existing Item");
            System.out.println("3. Remove Item");
            System.out.println("4. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            int choice = opt.nextInt();
            opt.nextLine();

            switch(choice) {
                case 4:
                    return;
                case 1:
                    add_newI();
                    break;
                case 2:
                    update_itemm();
                    break;
                case 3:
                    removeItem();
                    break;
                default:
                    System.out.println("try again");
            }
        }
    }

    private static void add_newI()
    {
        Scanner opt = new Scanner(System.in);
        System.out.println("\n----Add New Item----");
        System.out.print("Enter item name: ");
        String name = opt.nextLine();
        System.out.print("Enter item price: $"); //assumed in dollars as there is no rupee symbol in keyboard:( (promise i love my country)
        double price = opt.nextDouble();
        opt.nextLine();
        System.out.println("\nAvailable categories:");
        System.out.println("1. Snacks");
        System.out.println("2. Beverages");
        System.out.println("3. Meals");
        System.out.println("4. Desserts");
        System.out.print("Select category (1-4): ");
        int CChoice = opt.nextInt();
        opt.nextLine();
        String category;
        switch(CChoice)
        {
            case 1: category = "Snacks"; break;
            case 2: category = "Beverages"; break;
            case 3: category = "Meals"; break;
            case 4: category = "Desserts"; break;
            default:
                System.out.println("wrong category .");
                return;
        }
        System.out.print("Is item available? (y/n): ");
        boolean isAvailable = opt.nextLine().trim().equals("y");
        FoodItem newwI = new FoodItem(name, price, category, isAvailable);
        menu.add_newI(newwI);
        System.out.println("\nUpdated Menu:");
        menu.item_display();
    }

    private static void update_itemm() {
        Scanner opt = new Scanner(System.in);

        System.out.println("\n----Update Existing Item----");
        // Display current menu
        menu.item_display();
        System.out.print("\nEnter name of item to update: ");
        String item_name = opt.nextLine();
        FoodItem item = menu.findItem(item_name);
        if(item == null) {
            System.out.println("Item not found!");
            return;
        }
        System.out.println("\ncurrent item details:");
        System.out.println("Name: " + item.getName());
        System.out.println("Price: $" + item.getPrice());
        System.out.println("Category: " + item.getCategory());
        System.out.println("Available: " + (item.isAvailable() ? "Yes" : "No"));
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Price");
        System.out.println("2. Availability");
        System.out.print("Enter choice: ");
        int Uchoice = opt.nextInt();
        opt.nextLine();
        switch(Uchoice)
        {
            case 1:
                System.out.print("Enter new price: $");
                double newPrice = opt.nextDouble();
                if(newPrice > 0) {
                    menu.update_Iprice(item, newPrice);
                    System.out.println("Price updated successfully!");
                } else {
                    System.out.println("wrong price lol");
                }
                break;
            case 2:
                System.out.print("Is item available? (y/n): ");
                boolean newAvailability = opt.nextLine().trim().equals("y");
                menu.update_Iavailability(item, newAvailability);
                System.out.println("Availability updated");
                break;
            default:
                System.out.println("wrong choice sry");
        }
        System.out.println("\nupdated menu:");
        menu.item_display();
    }
    private static void removeItem()
    {
        Scanner opt = new Scanner(System.in);
        System.out.println("\n----Remove Item----");
        menu.item_display();
        System.out.print("\nEnter name of item to remove: ");
        String item_name = opt.nextLine();
        FoodItem item=menu.findItem(item_name);
        if(item ==null)
        {
            System.out.println("Item not found!");
            return;
        }
        System.out.println("\nItem details:");
        System.out.println("Name: " + item.getName());
        System.out.println("Price: $" + item.getPrice());
        System.out.println("Category: " + item.getCategory());
        System.out.print("\nAre you sure you want to remove this item? (y/n): ");
        String confirm = opt.nextLine().trim();
        if(confirm.equals("y")) {
            removed_itemm(item);
            menu.removeItem(item);
            System.out.println("Item removed ");
            System.out.println("\nUpdated menu:");
            menu.item_display();
        } else {
            System.out.println("remove operation cancelled.");
        }
    }
    private static void removed_itemm(FoodItem item)
    {
        Map<Integer, Order> allOrders = ManageOrder.getAllOrders();
        int ordersA= 0;
        for (Order order :allOrders.values()) {
            if (order.getStatus().equals(Order.STATUS_RECEIVED)||order.getStatus().equals(Order.STATUS_PREPARING)) {
                if (order.containsItem(item))
                {
                    order.set_status(Order.STATUS_DENIED);
                    ordersA++;
                }
            }
        }
        if (ordersA> 0) {
            System.out.println("\nUpdated " + ordersA + " ALL pending orders to 'Denied' status.");
        }
    }
}