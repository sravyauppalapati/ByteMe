package com.example.bite;
import java.util.*;
public class Menu {

private TreeSet<FoodItem> items;
private Map<String, TreeSet<FoodItem>> category;
public Menu()
{
    items = new TreeSet<>();
    category = new HashMap<>();
    category.put("Snacks", new TreeSet<>());
    category.put("Beverages", new TreeSet<>());
    category.put("Meals", new TreeSet<>());
    category.put("Desserts", new TreeSet<>());
    initializeMenu();
}

public TreeSet<FoodItem> getAllItems() {
    return items;
    }

private void initializeMenu()
{
    // Snacks
    addItem(new FoodItem("French Fries", 3.99, "Snacks", true));
    addItem(new FoodItem("Samosa", 6.99, "Snacks", true));
    addItem(new FoodItem("Cutlet", 4.99, "Snacks", true));
    addItem(new FoodItem("Mozzarella Sticks", 5.99, "Snacks", false));
    addItem(new FoodItem("Nachos", 5.49, "Snacks", true));

    // Beverages
    addItem(new FoodItem("Boba", 1.99, "Beverages", true));
    addItem(new FoodItem("Iced Tea", 2.49, "Beverages", true));
    addItem(new FoodItem("Coffee", 2.99, "Beverages", true));
    addItem(new FoodItem("Mojito", 4.99, "Beverages", false));
    addItem(new FoodItem("Water", 1.49, "Beverages", true));

    // Meals
    addItem(new FoodItem("Burger Combo", 12.99, "Meals", true));
    addItem(new FoodItem("Factory Nachos", 10.99, "Meals", true));
    addItem(new FoodItem("Pizza", 14.99, "Meals", true));
    addItem(new FoodItem("Pasta Alfredo", 11.99, "Meals", false));
    addItem(new FoodItem("Ramen", 13.99, "Meals", true));

    // Desserts
    addItem(new FoodItem("Ice Cream", 3.99, "Desserts", true));
    addItem(new FoodItem("Chocolate Cake", 5.99, "Desserts", true));
    addItem(new FoodItem("Tiramisu", 4.99, "Desserts", false));
    addItem(new FoodItem("Cheesecake", 6.99, "Desserts", true));
    addItem(new FoodItem("Brownie", 4.49, "Desserts", true));
}

private void addItem(FoodItem item)
{
    items.add(item);
    category.get(item.getCategory()).add(item);
}
public void item_display()
{
    System.out.println("\n----Complete Menu----");
    heading();
    for(FoodItem item : items) {
        System.out.println(item);
    }
}
public void item_search() {
    Scanner opt = new Scanner(System.in);
    System.out.print("enter search keyword: ");
    String keyword = opt.nextLine().toLowerCase();
    System.out.println("\n-Search Results----");
    heading();
    boolean found = false;
    for(FoodItem item : items)
    {
        if(item.getName().toLowerCase().contains(keyword)||item.getCategory().toLowerCase().contains(keyword)) {
            System.out.println(item);
            found = true;
        }
    }

    if(!found) {
        System.out.println("no items found matching your search :(");
    }

}

public void filter()
{
    Scanner opt = new Scanner(System.in);
    System.out.println("\n----Available Categories----");
    String[] categories = {"Snacks", "Beverages", "Meals", "Desserts"};
    for(int i = 0; i < categories.length; i++) {
        System.out.println((i + 1) + ". " + categories[i]);
    }

    System.out.print("Select category (1-4): ");
    int choice = opt.nextInt();
    if(choice >=1 &&choice<= 4) {
        String selectedCategory = categories[choice - 1];
        TreeSet<FoodItem> categoryI = category.get(selectedCategory);
        System.out.println("\n----" + selectedCategory + "----");
        heading();
        for(FoodItem item: categoryI)
        {
            System.out.println(item);
        }
    } else {
        System.out.println("invalid category selection!");
    }
}

public void sort_price() {
    Scanner opt = new Scanner(System.in);
    System.out.println("\n----Sort by Price----");
    System.out.println("1. Price: High to Low");
    System.out.println("2. Price: Low to High");
    System.out.print("Select sorting order: ");
    int choice = opt.nextInt();
    List<FoodItem> sortedItems = new ArrayList<>(items);
    if(choice==1) {
        Collections.sort(sortedItems, (a, b) -> Double.compare(b.getPrice(), a.getPrice()));
        System.out.println("\n----Items Sorted by Price (High to Low)----");
    }
    else if(choice==2)
    {
        Collections.sort(sortedItems, (a, b) -> Double.compare(a.getPrice(), b.getPrice()));
        System.out.println("\n----Items Sorted by Price (Low to High)----");
    }
    else {
        System.out.println("wrongchoice!");
        return;
    }

    heading();
    for(FoodItem item : sortedItems)
    {
        System.out.println(item);
    }
}

private void heading()
{
    System.out.println(String.format("%-20s %-10s %-8s %s",
            "Item", "Category", "Price", "Status"));
    System.out.println("------------------------------------------------");
}

public FoodItem findItem(String item_name) {
    for(FoodItem item : items) {
        if(item.getName().equalsIgnoreCase(item_name))
        {
            return item;
        }
    }
    return null;
}

public void add_newI(FoodItem newwI)
{
    if (newwI != null && category.containsKey(newwI.getCategory())) {
        items.add(newwI);
        category.get(newwI.getCategory()).add(newwI);
        System.out.println("Item added ");
    } else {
        System.out.println("WRONGitem or category!");
    }
}

public void update_Iprice(FoodItem item, double newPrice)
{
    if (item!= null &&newPrice>0)
    {
        items.remove(item);
        category.get(item.getCategory()).remove(item);
        item.setPrice(newPrice);
        items.add(item);
        category.get(item.getCategory()).add(item);
    }
}

public void update_Iavailability(FoodItem item, boolean isAvailable)
{
    if (item!= null)
    {
        items.remove(item);
        category.get(item.getCategory()).remove(item);
        item.setAvailable(isAvailable);
        items.add(item);
        category.get(item.getCategory()).add(item);
    }
}
public void removeItem(FoodItem item)
{
    if (item != null)
    {
        items.remove(item);
        category.get(item.getCategory()).remove(item);
    }
}



}