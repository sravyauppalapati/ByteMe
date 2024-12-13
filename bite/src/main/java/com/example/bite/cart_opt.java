package com.example.bite;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
public class cart_opt
{
    private Menu menu;
    private Map<FoodItem, Integer> items_in_cart;
    private boolean iVIP;
    private String currentRoom;

    public cart_opt(Menu menu, boolean iVIP)
    {
        this.menu=menu;
        this.items_in_cart=new HashMap<>();
        this.iVIP=iVIP;
    }

    public Map<FoodItem, Integer> getItems_in_cart() {
        return items_in_cart;
    }

    public void set_Croom(String roomNo) {
        this.currentRoom = roomNo;

        this.items_in_cart = FileHandler.loadCart(roomNo, menu);
    }
    private void checkout() {
        if(items_in_cart.isEmpty()) {
            System.out.println("\nUr cart is currently empty :(");
            return;
        }
        Scanner opt = new Scanner(System.in);
        double Tamount = calculateT();
        totalV();
        System.out.println("\nspecial requests?(y/n): ");
        String hrequestt = opt.nextLine().trim().toLowerCase();
        String specialR = "";

        if(hrequestt.equals("y")) {
            System.out.println("enter ur request): ");
            specialR = opt.nextLine().trim();
        }
        System.out.print("\n is the payment done? (yes/no): ");
        String confirm_payy = opt.nextLine().trim();
        if(confirm_payy.equals("yes")) {
            System.out.print("enter hostel (girl/guy hostel): ");
            String hostel = opt.nextLine().trim();
            System.out.print("enter ur room nor: ");
            String room_no = opt.nextLine().trim();

            String Tcustomer = iVIP ? "VIP" : "Regular";
            Order nOrder = new Order(hostel, room_no, Tamount, Tcustomer, specialR);
            for(Map.Entry<FoodItem, Integer> entry : items_in_cart.entrySet()) {
                FoodItem item = entry.getKey();
                int quantity = entry.getValue();
                OrderItem new_orderI = new OrderItem(item, quantity);
                nOrder.add_orderI(new_orderI);
            }

            ManageOrder.addOrder(nOrder);
            save_orderr(nOrder); //order is saved to file yay
            System.out.println("\n---order is confimed :)---");
            System.out.println("thank you for your order!");
            items_in_cart.clear();
        } else
        {
            System.out.println("complete payment pls");
        }
    }

    private void save_orderr(Order order) {
        String roomNo = order.getroomN();
        String fileName = "orderHistory_room_" + roomNo + ".txt";

        try (FileWriter fw = new FileWriter(fileName, true);  // true for append mode
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println("=== Order #" + order.getOrderId() + " ===");
            out.println("Customer Type: " + order.getCustomerT());
            out.println("Hostel: " + order.getHostel());
            out.println("Items ordered:");

            for (OrderItem item : order.getOrderItems()) {
                out.printf("- %dx %s ($%.2f each)\n",
                        item.getQuantity(),
                        item.getItem().getName(),
                        item.getPriceAtOrder());
            }

            out.printf("Total Amount: $%.2f\n", order.getTamount());

            if (!order.getspecialR().isEmpty()) {
                out.println("Special Request: " + order.getspecialR());
            }

            out.println("----------------------------------------\n");

        } catch (IOException e) {
            System.out.println("Error saving order history: " + e.getMessage());
        }
    }

    private boolean Vquantityy(int quantity) {
        return quantity > 0; //returns true or false
    }
    public double calculateT()
    {
        double total = 0.0; //INITIAL TOTEL
        for(Map.Entry<FoodItem, Integer> entry : items_in_cart.entrySet())
        {
            FoodItem item = entry.getKey();
            int quantity = entry.getValue();
            total += item.getPrice() * quantity;
        }
        return total;
    }

    public void cart_menu()
    {
        Scanner opt = new Scanner(System.in);
        if (currentRoom == null) {
            System.out.print("enter ur room num: ");
            String roomNo = opt.nextLine().trim();
            set_Croom(roomNo);
        }
        while(true) {
            System.out.println("\n----Cart Operations----");
            System.out.println("1. Add Items");
            System.out.println("2. Modify Quantities");
            System.out.println("3. Remove Items");
            System.out.println("4. View Total");
            System.out.println("5. Checkout");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = opt.nextInt();
            opt.nextLine();

            switch(choice) {
                case 6:
                    return;
                case 1:
                    addItems();
                    break;
                case 2:
                    modify_quantity();
                    break;
                case 3:
                    removie_item();
                    break;
                case 4:
                    totalV();
                    break;
                case 5:
                    checkout();
                    break;
                default:
                    System.out.println("try again");
            }

        }
    }
    private void addItems()
    {
        Scanner opt = new Scanner(System.in);
        while(true) {
            System.out.println("\n----add Items to Cart <3----");
            menu.item_display();
            System.out.print("\nenter item name (or 0 to finish): ");
            String item_name = opt.nextLine();
            if(item_name.equals("0"))
            {
                break;
            }
            FoodItem selectedI = find_item(item_name);

            if(selectedI == null)
            {
                System.out.println("item not in menu currently ");
                continue;
            }
            System.out.print("enter quantity: ");
            int quantity = opt.nextInt();
            opt.nextLine();
            items_in_cart.merge(selectedI, quantity, Integer::sum);
            FileHandler.saveCart(currentRoom, items_in_cart); // Save after updating cart
            System.out.println(quantity + "x " + selectedI.getName() + " added to cart!");
            displayCart();
        }
    }

    private FoodItem find_item(String item_name)
    {
        return menu.findItem(item_name);
    }
    private void displayCart()
    {
        if(items_in_cart.isEmpty()) //if cart is empty
        {
            System.out.println("\ncart is empty :()");
            return;
        }
        System.out.println("\n----Current Cart----");
        System.out.println(String.format("%-21s %-11s %-11s %s", "Item", "Price", "Quantity", "Subtotal"));
        System.out.println("------------------------------------------------");

        for(Map.Entry<FoodItem, Integer> entry : items_in_cart.entrySet())
        {
            FoodItem item = entry.getKey();
            int quantity = entry.getValue();
            double Stotal = item.getPrice() * quantity;

            System.out.println(String.format("%-20s $%-9.2f %-10d $%.2f",
                    item.getName(), item.getPrice(), quantity, Stotal));
        }
    }

    public void modify_quantity()
    {
        Scanner opt = new Scanner(System.in);
        System.out.println("\n current cart---");
        displayCart();
        if(items_in_cart.isEmpty())
        {
            return;
        }
        System.out.print("\nwould u like to modify any quantity (y/n): ");
        String response = opt.nextLine().trim();
        if(response.equals("y"))
        {
            System.out.print("enter item name: ");
            String item_name = opt.nextLine();
            FoodItem modifyyI = null;
            for(FoodItem item : items_in_cart.keySet())
            {
                if(item.getName().equalsIgnoreCase(item_name)) {
                    modifyyI = item;
                    break;
                }
            }

            if(modifyyI !=null) {
                System.out.print("enter new quantity (0 to remove): ");
                int newQuantity = opt.nextInt();
                opt.nextLine();
                if(newQuantity>0)
                {
                    items_in_cart.put(modifyyI, newQuantity);
                    FileHandler.saveCart(currentRoom, items_in_cart);
                    System.out.println("quantity updated yay");
                } else if(newQuantity==0)
                {
                    items_in_cart.remove(modifyyI);
                    System.out.println("item has been removed from cart!");
                } else
                {
                    System.out.println("Invalid quantity! No changes made.");
                }
                System.out.println("\n----Updated Cart Contents----");
                displayCart();
            } else
            {
                System.out.println("item not found!");
            }
        }
    }

    private void removie_item() {
        Scanner opt = new Scanner(System.in);
        System.out.println("\n----Current Cart Contents----");
        displayCart();
        if(items_in_cart.isEmpty()) //if carts empty!
        {
            return;
        }

        System.out.print("\ndo u want to remove items?(y/n): ");
        String response = opt.nextLine().trim();
        if(response.equals("y")) {
            System.out.print("enter item name to remove: ");
            String item_name = opt.nextLine();
            FoodItem removeeI = null;
            for(FoodItem item : items_in_cart.keySet())
            {
                if(item.getName().equalsIgnoreCase(item_name)) {
                    removeeI = item;
                    break;
                }
            }

            if(removeeI != null) {
                items_in_cart.remove(removeeI);
                FileHandler.saveCart(currentRoom, items_in_cart);
                System.out.println("Item removed  ");
                System.out.println("\n----Updated Cart Contents----");
                displayCart();
            } else
            {
                System.out.println("item not found!");
            }
        }
    }
    private void totalV()
    {
        System.out.println("\ncart Total----");

        if(items_in_cart.isEmpty())
        {
            System.out.println("cart is empty!");
            return;
        }
        System.out.println(String.format("%-20s %-10s %-10s %s", "Item", "Price", "Quantity", "Subtotal"));
        System.out.println("------------------------------------------------");

        double total = 0.0;
        for(Map.Entry<FoodItem, Integer> entry : items_in_cart.entrySet())
        {
            FoodItem item = entry.getKey();
            int quantity = entry.getValue();
            double subtotall_It = item.getPrice() * quantity;
            total += subtotall_It;
            System.out.println(String.format("%-20s $%-9.2f %-10d $%.2f", item.getName(), item.getPrice(), quantity, subtotall_It));
        }
        System.out.println("------------------------------------------------");
        System.out.printf("Total Amount: $%.2f\n", total);
    }

    public void addToCart(FoodItem item, int quantity) {
        if (Vquantityy(quantity)) {
            items_in_cart.put(item, quantity);
        }
    }

    public void updatee_quantity(FoodItem item, int quantity) {
        if (Vquantityy(quantity))
        {
            items_in_cart.put(item, quantity);

        } else {
            items_in_cart.remove(item);
        }
    }
}

