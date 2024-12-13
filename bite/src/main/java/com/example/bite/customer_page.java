package com.example.bite;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class customer_page
{
    private Menu menu;
    private boolean iVIP;
    private cart_opt cart;
    private OrderT orderr_Track;

    public customer_page(boolean iVIP)
    {
        this.menu = Admin.getMenu();
        this.iVIP = iVIP;
        this.cart = new cart_opt(this.menu, iVIP);
        this.orderr_Track = new OrderT();
    }

    public void displayMainMenu() {
        Scanner opt = new Scanner(System.in);
        while(true) {
            System.out.println("\n----Main Menu----");
            System.out.println("1. Browse Menu");
            System.out.println("2. Cart Operations");
            System.out.println("3. Track Order");
            System.out.println("4. Reviews");
            System.out.println("5. View Order History");  // New option
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = opt.nextInt();
            opt.nextLine();

            switch(choice) {
                case 6:
                    return;
                case 1:
                    menu_browse();
                    break;
                case 2:
                    cart.cart_menu();
                    break;
                case 3:
                    orderr_Track.order_trackk();
                    break;
                case 4:
                    review_handler();
                    break;
                case 5:
                    view_OrderHistory();  // New case
                    break;
                default:
                    System.out.println("try again");
            }
        }
    }

    private void menu_browse()
    {
        Scanner opt = new Scanner(System.in);
        while(true) {
            System.out.println("\n----Browse Menu----");
            System.out.println("1. View All Items");
            System.out.println("2. Search Items");
            System.out.println("3. Filter by Category");
            System.out.println("4. Sort by Price");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = opt.nextInt();
            opt.nextLine();
            switch(choice)
            {
                case 5:
                    return;
                case 1:
                    menu.item_display();
                    break;
                case 2:
                    menu.item_search();
                    break;
                case 3:
                    menu.filter();
                    break;
                case 4:
                    menu.sort_price();
                    break;
                default:
                    System.out.println("try again");
            }
        }
    }

    private void review_handler()
    {
        Scanner opt = new Scanner(System.in);
        while(true) {
            System.out.println("\n----Reviews Menu----");
            System.out.println("1. Write a Review");
            System.out.println("2. View Reviews");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = opt.nextInt();
            opt.nextLine();
            switch(choice) {
                case 3:
                    return;
                case 1:
                    writeReview();
                    break;
                case 2:
                    view_Rev();
                    break;
                default:
                    System.out.println("try again");
            }
        }
    }
    private void writeReview()
    {
        Scanner opt = new Scanner(System.in);
        System.out.print("enter ur order id: ");
        int orderId = opt.nextInt();
        opt.nextLine();
        Order order = ManageOrder.getOrder(orderId);
        if (order == null)
        {
            System.out.println("order not found ,pls check your Order ID.");
            return;
        }

        System.out.println("\nItems in your order:");
        List<OrderItem> items = order.getOrderItems();
        for (int i =0; i < items.size(); i++)
        {
            System.out.println((i+ 1) + ". " + items.get(i).getItem().getName());
        }
        System.out.print("\nselect item number to review: ");
        int item_C = opt.nextInt();
        opt.nextLine();
        if (item_C<1 ||item_C > items.size())
        {
            System.out.println("wrong item selection bro");
            return;
        }

        String item_name = items.get(item_C-1).getItem().getName();
        System.out.println("enter review: ");
        String reviewww = opt.nextLine();
        Review review = new Review(item_name, reviewww, order.getroomN());
        MReview.addReview(review);
        System.out.println("thanks for the review!");
    }

    private void view_Rev()
    {
        Scanner opt = new Scanner(System.in);
        menu.item_display();
        System.out.print("\nenter item name to view reviews: ");
        String item_name = opt.nextLine();
        FoodItem item = menu.findItem(item_name);
        List<Review> reviews = MReview.getReviewI(item_name);
        System.out.println("\nReviews for " + item_name + "----");
        if (reviews.isEmpty())
        {
            System.out.println("sry no reviews yet for this item.");
        } else
        {
            for (Review review : reviews) {
                System.out.println("- " + review);
            }
        }
    }

    private void view_OrderHistory() {
        Scanner opt = new Scanner(System.in);
        System.out.print("enter room numeber ");
        String roomNo = opt.nextLine().trim();
        String fileName = "orderHistory_room_" + roomNo + ".txt";
        File file = new File(fileName);
        if (!file.exists())
        {
            System.out.println("no order history found for room " + roomNo);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println("\norder history for room: " + roomNo );
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }


}